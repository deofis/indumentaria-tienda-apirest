package com.deofis.tiendaapirest.productos.controllers.catalogoadmin.skus;

import com.deofis.tiendaapirest.productos.domain.Sku;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.exceptions.SkuException;
import com.deofis.tiendaapirest.productos.services.CatalogoAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CrearSkuController {

    private final CatalogoAdminService catalogoAdminService;

    @PostMapping("/productos/{productoId}/skus")
    public ResponseEntity<?> crearNuevoSkuProducto(@PathVariable Long productoId, @Valid @RequestBody Sku sku) {
        Map<String, Object> response = new HashMap<>();
        Sku nuevoSku;

        try {
            nuevoSku = this.catalogoAdminService.crearSku(productoId, sku);
        } catch (ProductoException | SkuException e) {
            response.put("mensaje", "Error al crear nuevo Sku de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("sku", nuevoSku);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
