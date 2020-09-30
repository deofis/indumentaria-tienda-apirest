package com.deofis.tiendaapirest.productos.controllers.producto;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ImportarProductosController {

    private final ProductoService productoService;

    @PostMapping("/productos/importar-csv")
    public ResponseEntity<?> importarProductosDeCSV(@RequestParam MultipartFile archivo) {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productos;

        try {
            productos = this.productoService.importarDeCSV(archivo);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al importar archivo CSV");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("productos", productos);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/productos/importar-excel")
    public ResponseEntity<?> importarProductosDeExcel(@RequestParam MultipartFile archivo) {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productos;

        try {
            productos = this.productoService.importarDeExcel(archivo);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al importar archivo Excel");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("productos", productos);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
