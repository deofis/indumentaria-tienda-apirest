package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.autenticacion.dto.UsuarioDTO;
import com.deofis.tiendaapirest.autenticacion.services.AutenticacionService;
import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.repositories.ClienteRepository;
import com.deofis.tiendaapirest.emails.dto.NotificationEmail;
import com.deofis.tiendaapirest.emails.services.MailService;
import com.deofis.tiendaapirest.operaciones.domain.DetalleOperacion;
import com.deofis.tiendaapirest.operaciones.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.EventoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.operaciones.repositories.OperacionRepository;
import com.deofis.tiendaapirest.perfiles.domain.Perfil;
import com.deofis.tiendaapirest.perfiles.repositories.PerfilRepository;
import com.deofis.tiendaapirest.perfiles.services.PerfilService;
import com.deofis.tiendaapirest.perfiles.services.UsuarioService;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OperacionServiceImpl implements OperacionService {
    public static final String NRO_OPERACION_HEADER = "nroOperacion";

    private final StateMachineService stateMachineService;

    private final AutenticacionService autenticacionService;
    private final UsuarioService usuarioService;
    private final MailService mailService;

    private final OperacionRepository operacionRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final PerfilRepository perfilRepository;
    private final PerfilService perfilService;

    @Transactional
    @Override
    public Operacion registrarNuevaOperacion(Operacion operacion) {
        Cliente cliente;

        if (this.autenticacionService.estaLogueado()) {
            cliente = this.perfilService.obtenerPerfil().getCliente();
        } else {
            cliente = this.clienteRepository.findById(operacion.getCliente().getId())
                    .orElseThrow(() -> new OperacionException("El cliente seleccionado debe estar cargado " +
                            "en la base de datos."));
        }

        Operacion nuevaOperacion = Operacion.builder()
                .cliente(cliente)
                .fechaOperacion(new Date(new Date().getTime()))
                .fechaEnviada(null)
                .fechaRecibida(null)
                .medioPago(operacion.getMedioPago())
                .estado(EstadoOperacion.PENDING)
                .total(0.0)
                .items(operacion.getItems())
                .build();

        for (DetalleOperacion item: operacion.getItems()) {
            Producto producto = this.productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new ProductoException("Producto no encontrado con id: " +
                            item.getProducto().getId()));

            if (producto.getDisponibilidadGeneral() - item.getCantidad() < 0) {
                throw new OperacionException("Error al completar la compra: " +
                        "La cantidad de productos vendidos no puede ser menor al stock actual");
            }

            item.setPrecioVenta(producto.getPrecio());
            producto.setDisponibilidadGeneral(producto.getDisponibilidadGeneral() - item.getCantidad());
            item.setSubTotal(item.getPrecioVenta() * item.getCantidad().doubleValue());

            nuevaOperacion.setTotal(this.calcularTotal(nuevaOperacion.getTotal(), item.getSubTotal()));
            this.productoRepository.save(producto);
        }

        this.operacionRepository.save(nuevaOperacion);


        if (this.autenticacionService.estaLogueado()) {
            Perfil perfil = this.perfilService.obtenerPerfil();

            perfil.getCompras().add(nuevaOperacion);
            this.perfilService.vaciarCarrito();
            this.perfilRepository.save(perfil);
        }

        this.enviarEmailUsuario(nuevaOperacion, cliente.getEmail());
        this.enviarEmailsAdmins(nuevaOperacion);

        return nuevaOperacion;
    }

    private void enviarEmailsAdmins(Operacion operacion) {
        /*Operacion operacionGuardada = this.operacionRepository.findById(operacion.getNroOperacion())
                .orElseThrow(() -> new OperacionException("Operación no encontrada con numero: " +
                        operacion.getNroOperacion()));
         */

        List<UsuarioDTO> admins = this.usuarioService.obtenerAdministradores();

        for (UsuarioDTO admin: admins) {
            NotificationEmail notificationEmail = new NotificationEmail();
            String body = "Se ha registrado una nueva venta en el sistema. Revisar la venta con " +
                    "número de operacion: " + operacion.getNroOperacion() +
                    " para realizar la facturación correspondiente al cliente: " +
                    operacion.getCliente().getNombre() + ", " + operacion.getCliente().getApellido();

            notificationEmail.setBody(body);
            notificationEmail.setSubject("Nueva venta registrada :: Deofis");

            notificationEmail.setRecipient(admin.getEmail());
            this.mailService.sendEmail(notificationEmail);

            log.info(admin.getEmail());
        }
    }

    private void enviarEmailUsuario(Operacion operacion, String emailUsuario) {
        NotificationEmail notificationEmail = new NotificationEmail();
        String body = "Gracias por realizar la compra! En la brevedad recibirá a su correo la facturación " +
                "realizada por la tienda.\n" +
                "Número de compra: " + operacion.getNroOperacion() + "\n" +
                "\nPorfavor, revise los datos de la compra en su perfil de usuario!";
        notificationEmail.setBody(body);
        notificationEmail.setRecipient(emailUsuario);
        notificationEmail.setSubject("Nueva compra registrada");

        log.info(emailUsuario);

        this.mailService.sendEmail(notificationEmail);
    }

    @Transactional
    @Override
    public Operacion registrarEnvio(Long nroOperacion) {
        Operacion operacion = this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operacion con numero: " + nroOperacion));
        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(nroOperacion);

        sm.getExtendedState().getVariables().put("operacion", operacion);

        this.stateMachineService.enviarEvento(nroOperacion, sm, EventoOperacion.SEND);
        return operacion;
    }

    @Transactional
    @Override
    public Operacion registrarRecibo(Long nroOperacion) {
        Operacion operacion = this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operacion con numero: " + nroOperacion));
        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(nroOperacion);

        sm.getExtendedState().getVariables().put("operacion", operacion);

        this.stateMachineService.enviarEvento(nroOperacion, sm, EventoOperacion.RECEIVE);
        return operacion;
    }

    @Transactional
    @Override
    public Operacion cancelar(Long nroOperacion) {
        Operacion operacion = this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operacion con numero: " + nroOperacion));
        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(nroOperacion);

        this.stateMachineService.enviarEvento(nroOperacion, sm, EventoOperacion.CANCEL);
        return operacion;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> listarVentas() {
        return this.operacionRepository.findAllByOrderByFechaOperacionDesc();
    }

    @Override
    public Operacion obtenerVenta(Long nroOperacion) {
        return this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operación con id: " + nroOperacion));
    }

    private Double calcularTotal(Double total, Double subTotal) {
        return total + subTotal;
    }
}
