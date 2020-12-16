package com.deofis.tiendaapirest.productos.controllers.catalogoadmin.skus;

import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.exceptions.SkuException;
import com.deofis.tiendaapirest.productos.services.CatalogoAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class GenerarSkusProductoController {

    private final CatalogoAdminService catalogoAdminService;

    @PostMapping("/productos/{productoId}/generarSkus")
    public ResponseEntity<?> generarSkusProducto(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.catalogoAdminService.eliminarSkusProducto(productoId);
            response = this.catalogoAdminService.generarSkusProducto(productoId);
        } catch (ProductoException | SkuException e) {
            response.put("mensaje", "Error al generar los SKUs del producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
