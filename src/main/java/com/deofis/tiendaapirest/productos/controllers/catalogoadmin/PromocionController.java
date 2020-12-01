package com.deofis.tiendaapirest.productos.controllers.catalogoadmin;

import com.deofis.tiendaapirest.productos.domain.Promocion;
import com.deofis.tiendaapirest.productos.services.PromocionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PromocionController {

    private final PromocionService promocionService;

    @PostMapping("/productos/{productoId}/promociones")
    public ResponseEntity<?> crearPromocionProducto(@PathVariable Long productoId,
                                                    @Valid @RequestBody Promocion promocion) {
        return null;
    }

    @PostMapping("/productos/skus/{skuId}/promociones")
    public ResponseEntity<?> crearPromocionSku(@PathVariable Long skuId,
                                               @Valid @RequestBody Promocion promocion) {
        return null;
    }

    @PostMapping("/subcategorias/{subcategoriaId}/promociones")
    public ResponseEntity<?> crearPromocionProductosDeSubcategoria(@PathVariable Long subcategoriaId,
                                                                   @Valid @RequestBody Promocion promocion) {
        return null;
    }

}
