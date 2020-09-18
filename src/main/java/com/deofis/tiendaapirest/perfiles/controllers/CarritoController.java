package com.deofis.tiendaapirest.perfiles.controllers;

import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.perfiles.domain.Carrito;
import com.deofis.tiendaapirest.perfiles.exceptions.CarritoException;
import com.deofis.tiendaapirest.perfiles.exceptions.PerfilesException;
import com.deofis.tiendaapirest.perfiles.services.CarritoService;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    /**
     * Añade un nuevo producto al carrito del perfil actual. Si ya existe en el carrito, agrega
     * uno a la cantidad.
     * URL: ~/api/carrito/producto/agregar
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param productoId Long id del producto a agregar.
     * @return ResponseEntity con el carrito actualizado.
     */
    @PostMapping("/carrito/producto/agregar")
    public ResponseEntity<?> agregarProducto(@RequestParam Long productoId) {
        Map<String, Object> response = new HashMap<>();
        Carrito carrito;

        try {
            carrito = this.carritoService.agregarProducto(productoId);
        } catch (PerfilesException | ProductoException | ClienteException e) {
            response.put("mensaje", "Error al agregar producto al carrito");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("carritoActualizado", carrito);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     * URL: ~/api/carrito/producto/cantidad
     * HttpMethod: PUT
     * HttpStatus: CREATED
     * @param productoId Long id del producto a actualizar cantidad en carrito.
     * @param cantidad Integer cantidad a agregar (o quitar).
     * @return ResponseEntity con el carrito actualizado.
     */
    @PutMapping("/carrito/producto/cantidad")
    public ResponseEntity<?> actualizarCantidad(@RequestParam Long productoId, @RequestParam Integer cantidad) {
        Map<String, Object> response = new HashMap<>();
        Carrito carritoActualizado;

        try {
            carritoActualizado = this.carritoService.actualizarCantidad(productoId, cantidad);
        } catch (ProductoException | PerfilesException | ClienteException | CarritoException e) {
            response.put("mensaje", "Error al actualizar la cantidad de productos en el carrito");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("carritoActualizado", carritoActualizado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Quita un producto del carrito del perfil.
     * URL: ~/api/carrito/producto/quitar
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param productoId Long id del producto a quitar del carrito.
     * @return ResponseEntity del carrito actualizado.
     */
    @DeleteMapping("/carrito/producto/quitar")
    public ResponseEntity<?> quitarProducto(@RequestParam Long productoId) {
        Map<String, Object> response = new HashMap<>();
        Carrito carritoActualizado;

        try {
            carritoActualizado = this.carritoService.quitarProducto(productoId);
        } catch (CarritoException | PerfilesException | ProductoException e) {
            response.put("mensaje", "Error al quitar el producto del carrito");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("carritoActualizado", carritoActualizado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Vacía el carrito del perfil.
     * URL: ~/api/carrito/vaciar
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @return ResponseEntity mensaje éxito/error.
     */
    @DeleteMapping("/carrito/vaciar")
    public ResponseEntity<String> vaciarCarrito() {
        try {
            this.carritoService.vaciar();
        } catch (PerfilesException | CarritoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Carrito vaciado", HttpStatus.OK);
    }
}
