package com.deofis.tiendaapirest.productos.controllers.catalogoadmin;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador que se encarga de obtener los productos de distintas formas.
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ObtenerProductosController {

    private final ProductoService productoService;

    /**
     * Obtiene los productos ordenados alfabéticamente.
     * URL: ~/api/productos
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con un listado de todos los productos.
     */
    @GetMapping("/productos")
    public ResponseEntity<?> obtenerProductos() {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productos;

        try {
            productos = this.productoService.obtenerProductos();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener los productos de la Base de Datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (productos.size() == 0) {
            response.put("error", "No existen productos registrados en la Base de Datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("productos", productos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene un producto específico.
     * URL: http://localhost:8080/api/productos/1
     * HttpStatus: OK
     * HttpMethod: GET
     * @param productoId PathVarible Long con el id solicitado.
     * @return ResponseEntity con el Producto.
     */
    @GetMapping("/productos/{productoId}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long productoId) {

        Map<String, Object> response = new HashMap<>();
        Producto producto;

        try {
            producto = this.productoService.obtenerProducto(productoId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener el producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("producto", producto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene las propiedades de un producto requerido.
     * URL: ~/api/productos/1/propiedades
     * HttpMethod: GET
     * HttpStatus: OK
     * @param productoId Long id del producto a listar propiedades.
     * @return ResponseEntity listado de propiedades.
     */
    @GetMapping("/productos/{productoId}/propiedades")
    public ResponseEntity<?> obtenerPropiedadesDeProducto(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        List<PropiedadProducto> propiedades;
        String producto;

        try {
            propiedades = this.productoService.obtenerPropiedadesDeProducto(productoId);
            producto = this.productoService.obtenerProducto(productoId).getNombre();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener las propiedades del producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("producto", producto);
        response.put("propiedades", propiedades);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene listado de las unidades de medida.
     * URL: ~/api/productos/unidades-medida
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con el listado de unidades de medida.
     */
    @GetMapping("/productos/unidades-medida")
    public ResponseEntity<?> obtenerUnidadesMedida() {
        Map<String, Object> response = new HashMap<>();
        List<UnidadMedida> unidadesMedida;

        try {
            unidadesMedida = this.productoService.obtenerUnidadesMedida();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener las unidades de medida");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(unidadesMedida, HttpStatus.OK);
    }
}