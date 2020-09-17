package com.deofis.tiendaapirest.operacion.services;

import com.deofis.tiendaapirest.autenticacion.services.AutenticacionService;
import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.repositories.ClienteRepository;
import com.deofis.tiendaapirest.operacion.domain.DetalleOperacion;
import com.deofis.tiendaapirest.operacion.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operacion.domain.EventoOperacion;
import com.deofis.tiendaapirest.operacion.domain.Operacion;
import com.deofis.tiendaapirest.operacion.exceptions.OperacionException;
import com.deofis.tiendaapirest.operacion.repositories.OperacionRepository;
import com.deofis.tiendaapirest.perfiles.domain.Perfil;
import com.deofis.tiendaapirest.perfiles.repositories.PerfilRepository;
import com.deofis.tiendaapirest.perfiles.services.PerfilService;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OperacionServiceImpl implements OperacionService {
    public static final String NRO_OPERACION_HEADER = "nroOperacion";

    private final StateMachineFactory<EstadoOperacion, EventoOperacion> stateMachineFactory;
    private final OperacionStateChangeInterceptor operacionStateChangeInterceptor;

    private final AutenticacionService autenticacionService;
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
            cliente = this.perfilService.getPerfil().getCliente();
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
                .formaPago(operacion.getFormaPago())
                .estado(EstadoOperacion.PENDING)
                .total(0.0)
                .items(operacion.getItems())
                .build();

        for (DetalleOperacion item: operacion.getItems()) {
            Producto producto = this.productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new ProductoException("Producto no encontrado con id: " +
                            item.getProducto().getId()));

            if (producto.getStock() - item.getCantidad() < 0) {
                throw new OperacionException("Error al completar la compra: " +
                        "La cantidad de productos vendidos no puede ser menor al stock actual");
            }

            item.setPrecioVenta(producto.getPrecio());
            producto.setStock(producto.getStock() - item.getCantidad());
            item.setSubTotal(item.getPrecioVenta() * item.getCantidad().doubleValue());

            nuevaOperacion.setTotal(this.calcularTotal(nuevaOperacion.getTotal(), item.getSubTotal()));
            this.productoRepository.save(producto);
        }

        this.operacionRepository.save(nuevaOperacion);


        if (this.autenticacionService.estaLogueado()) {
            Perfil perfil = this.perfilService.getPerfil();

            perfil.getCompras().add(nuevaOperacion);
            this.perfilService.vaciarCarrito();
            this.perfilRepository.save(perfil);
        }

        return nuevaOperacion;
    }

    @Transactional
    @Override
    public Operacion registrarEnvio(Long nroOperacion) {
        Operacion operacion = this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operacion con numero: " + nroOperacion));
        StateMachine<EstadoOperacion, EventoOperacion> sm = build(nroOperacion);

        sm.getExtendedState().getVariables().put("operacion", operacion);

        this.enviarEvento(nroOperacion, sm, EventoOperacion.SEND);
        return operacion;
    }

    @Transactional
    @Override
    public Operacion registrarRecibo(Long nroOperacion) {
        Operacion operacion = this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operacion con numero: " + nroOperacion));
        StateMachine<EstadoOperacion, EventoOperacion> sm = build(nroOperacion);

        sm.getExtendedState().getVariables().put("operacion", operacion);

        this.enviarEvento(nroOperacion, sm, EventoOperacion.RECEIVE);
        return operacion;
    }

    @Transactional
    @Override
    public Operacion cancelar(Long nroOperacion) {
        Operacion operacion = this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operacion con numero: " + nroOperacion));
        StateMachine<EstadoOperacion, EventoOperacion> sm = build(nroOperacion);

        this.enviarEvento(nroOperacion, sm, EventoOperacion.CANCEL);
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

    private void enviarEvento(Long nroOperacion, StateMachine<EstadoOperacion, EventoOperacion> sm, EventoOperacion evento) {
        Message<EventoOperacion> msg = MessageBuilder.withPayload(evento)
                .setHeader(NRO_OPERACION_HEADER, nroOperacion)
                .build();

        sm.sendEvent(msg);
    }

    private StateMachine<EstadoOperacion, EventoOperacion> build (Long nroOperacion) {
        Operacion operacion = this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operación con id: "
                + nroOperacion));

        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineFactory.getStateMachine(Long.toString(operacion.getNroOperacion()));

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(operacionStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(operacion.getEstado()
                            ,null, null, null));
                });

        sm.start();

        return sm;
    }
}
