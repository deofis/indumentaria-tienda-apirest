package com.deofis.tiendaapirest.perfiles.services;

import com.deofis.tiendaapirest.perfiles.domain.Carrito;
import com.deofis.tiendaapirest.perfiles.domain.DetalleCarrito;
import com.deofis.tiendaapirest.perfiles.exceptions.CarritoException;
import com.deofis.tiendaapirest.perfiles.repositories.CarritoRepository;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final PerfilService perfilService;
    private final ProductoRepository productoRepository;

    @Transactional
    @Override
    public Carrito agregarProducto(Long productoId) {
        boolean existeProd = false;
        Carrito carrito = this.perfilService.obtenerCarrito();
        Producto producto = this.obtenerProducto(productoId);

        for (DetalleCarrito item: carrito.getItems()) {
            if (item.getProducto().equals(producto)) {
                existeProd = true;
                item.setCantidad(item.getCantidad() + 1);
                log.info("Ya existe producto. Sumar cantidad");
            } else {
                existeProd = false;
                log.info("No existe producto. agregar nuevo detalle");
            }
        }

        if (!existeProd) {
            DetalleCarrito detalleCarrito = new DetalleCarrito();
            detalleCarrito.setProducto(producto);
            detalleCarrito.setCantidad(1);
            carrito.getItems().add(detalleCarrito);
        }

        return this.carritoRepository.save(carrito);
    }

    @Transactional
    @Override
    public Carrito actualizarCantidad(Long productoId, Integer cantidad) {
        Carrito carrito = this.perfilService.obtenerCarrito();
        Producto producto = this.obtenerProducto(productoId);

        if (carrito.getItems().size() == 0) {
            throw new CarritoException("Carrito vacío.");
        }

        for (DetalleCarrito item: carrito.getItems()) {
            if (item.getProducto().equals(producto)) {
                log.info("Existe producto en el carrito");
                item.setCantidad(cantidad);
                break;
            } else {
                log.info("No existe el producto en el carrito");
                throw new CarritoException("Producto no agregado al carrito");
            }
        }
        return this.carritoRepository.save(carrito);
    }

    @Transactional
    @Override
    public Carrito quitarProducto(Long productoId) {
        Carrito carrito = this.perfilService.obtenerCarrito();
        Producto producto = this.obtenerProducto(productoId);

        carrito.getItems().removeIf(item -> item.getProducto().equals(producto));

        return this.carritoRepository.save(carrito);
    }

    @Transactional
    @Override
    public void vaciar() {
        Carrito carrito = this.perfilService.obtenerCarrito();

        carrito.getItems().clear();
    }

    private Producto obtenerProducto(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoException("Producto no encontrado con id " + id));
    }
}
