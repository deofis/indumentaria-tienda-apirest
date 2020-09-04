package com.deofis.tiendaapirest.productos.controller.catalogo;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.service.CatalogoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    /**
     * Obtiene los productos destacados a mostrar.
     * URL: ~/api/catalogo/destacados
     * @return ResponseEntity con lista de productos destacados.
     */
    @GetMapping("/catalogo/destacados")
    public ResponseEntity<?> obtenerProductosDestacados() {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productosDestacados;

        try {
            productosDestacados = this.catalogoService.obtenerProductosDestacados();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener los productos destacados");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (productosDestacados.size() == 0) {
            response.put("error", "No existen productos destacados en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productosDestacados, HttpStatus.OK);
    }

    /**
     * Obtiene una lista con los productos buscados por un termino.
     * URL: ~/api/catalogo/buscar
     * @param termino Param 'termino' con el termino a buscar.
     * @return Lista de productos encontrados.
     */
    @GetMapping("/catalogo/buscar")
    public List<Producto> buscarProductos(@RequestParam String termino) {
        return this.catalogoService.buscarProductos(termino);
    }
}
