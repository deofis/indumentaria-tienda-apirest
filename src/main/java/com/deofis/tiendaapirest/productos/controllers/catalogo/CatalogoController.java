package com.deofis.tiendaapirest.productos.controllers.catalogo;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CatalogoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     *
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
     * Obtiene una lista con los productos buscados por un termino. El término puede ser: nombre, marca o subcategoría
     * de productos a buscar.
     * URL: ~/api/catalogo/buscar
     * HttpMethod: GET
     * HttpStatus: OK
     * @param termino Param 'termino' con el termino a buscar.
     * @return Lista de productos encontrados.
     */
    @GetMapping("/catalogo/buscar")
    public ResponseEntity<Map<String, Object>> buscarProductos(@RequestParam String termino) {
        Map<String, Object> response = this.catalogoService.buscarProductos(termino);

        if (response.get("totalProductos").equals(0)) response.put("mensaje", "No se encontraron resultados de la búsqueda");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene un producto para ver en detalle por el usuario.
     * URL: ~/api/catalogo/productos/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param productoId @PathVariable Long id del producto a obtener.
     * @return ResponseEntity Producto obtenido.
     */
    @GetMapping("/catalogo/productos/{productoId}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        Producto producto;

        try {
            producto = this.catalogoService.obtenerProducto(productoId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener el producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("producto", producto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Filtra los productos por categoría.
     * URL: ~/api/catalogo/filtrar/productos-por-categoria/1
     * HttpMethod: GET
     * HttpStatus: OK
     *
     * @param categoriaId @PathVariable Long con el id de la categoría.
     * @return ResponseEntity con la List de los productos filtrados por categoría.
     */
    @GetMapping("/catalogo/filtrar/productos-por-categoria/{categoriaId}")
    public ResponseEntity<?> filtrarProductosPorCategoria(@PathVariable Long categoriaId) {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productosPorCategoria;

        try {
            productosPorCategoria = this.catalogoService.productosPorSubcategoria(categoriaId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener los productos por categoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("cantidad", productosPorCategoria.size());
        response.put("productos", productosPorCategoria);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Filtra los productos por marca.
     * URL: ~/api/catalogo/filtrar/productos-por-marca/1
     * HttpMethod: GET
     * HttpStatus: OK
     *
     * @param marcaId @PathVariable Long con el id de la marca.
     * @return ResponseEntity List de los productos filtrados por marca.
     */
    @GetMapping("/catalogo/filtrar/productos-por-marca/{marcaId}")
    public ResponseEntity<?> filtrarProductosPorMarca(@PathVariable Long marcaId) {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productosPorMarca;

        try {
            productosPorMarca = this.catalogoService.productosPorMarca(marcaId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener los productos por marca");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("cantidad", productosPorMarca.size());
        response.put("productos", productosPorMarca);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Lista las marcas.
     *
     * @return List marcas.
     */
    @GetMapping("/catalogo/marcas")
    public List<Marca> listarMarcas() {
        return this.catalogoService.listarMarcas();
    }

    /**
     * Ordena los productos por precio de MENOR a MAYOR.
     * URL: ~/api/catalogo/precio-menor
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity List de los productos ordenados por precio de menor a mayor.
     */
    @GetMapping("/catalogo/precio-menor")
    public ResponseEntity<?> ordenarProductosPrecioMenorMayor() {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productosOrdenadosPrecioMenorAMayor;

        try {
            productosOrdenadosPrecioMenorAMayor = this.catalogoService.productosPrecioMenorMayor();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al ordear los productos por precio de menor a mayor");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("productos", productosOrdenadosPrecioMenorAMayor);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Ordena los productos por precio de MAYOR a MENOR.
     * URL: ~/api/catalogo/precio-mayor
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity List de los productos ordenados por precio de mayor a menor.
     */
    @GetMapping("/catalogo/precio-mayor")
    public ResponseEntity<?> ordenarProductosPrecioMayorMenor() {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productosOrdenadosPrecioMayorAMenor;

        try {
            productosOrdenadosPrecioMayorAMenor = this.catalogoService.productosPrecioMayorMenor();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al ordenar los productos por precio de mayor a menor");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("productos", productosOrdenadosPrecioMayorAMenor);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Filtra los productos por un precio máximo.
     * URL: ~/api/catalogo/filtrar/productos-por-precio
     * HttpMethod: GET
     * HttpStatus: OK
     * @param precioMax @RequestParam Double precio máximo a filtrar.
     * @return ResponseEntity List de productos dentro del rango del precio requerido.
     */
    @GetMapping("/catalogo/filtrar/productos-por-precio")
    public ResponseEntity<?> filtrarProductosPorPrecioMaximo(@RequestParam Double precioMax) {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productosPorPrecio;

        try {
            productosPorPrecio = this.catalogoService.productosPorPrecio(precioMax);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al filtrar los productos por precio máximo");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (productosPorPrecio.size() == 0) {
            response.put("error", "No existen productos en el rango indicado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("cantidad", productosPorPrecio.size());
        response.put("productos", productosPorPrecio);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
