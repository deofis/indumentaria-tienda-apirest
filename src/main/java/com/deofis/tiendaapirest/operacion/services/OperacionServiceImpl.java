package com.deofis.tiendaapirest.operacion.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.repositories.ClienteRepository;
import com.deofis.tiendaapirest.operacion.domain.DetalleOperacion;
import com.deofis.tiendaapirest.operacion.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operacion.domain.Operacion;
import com.deofis.tiendaapirest.operacion.exceptions.OperacionException;
import com.deofis.tiendaapirest.operacion.repositories.OperacionRepository;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OperacionServiceImpl implements OperacionService {

    private final OperacionRepository operacionRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    @Override
    public Operacion registrar(Operacion operacion) {
        Cliente cliente = this.clienteRepository.findById(operacion.getCliente().getId())
                .orElseThrow(() -> new OperacionException("El cliente seleccionado debe estar cargado " +
                        "en la base de datos."));

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

        return this.operacionRepository.save(nuevaOperacion);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> listarVentas() {
        return this.operacionRepository.findAllByOrderByFechaOperacionDesc();
    }

    private Double calcularTotal(Double total, Double subTotal) {
        return total + subTotal;
    }
}
