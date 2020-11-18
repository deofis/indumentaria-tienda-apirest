package com.deofis.tiendaapirest.productos.controllers.catalogoadmin;

import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CatalogoAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CrearPropiedadProductoController {

    private final CatalogoAdminService catalogoAdminService;

    @PostMapping("/productos/propiedades")
    public ResponseEntity<?> crearPropiedadProducto(@RequestBody PropiedadProducto propiedadProducto) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        try {
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProducto(propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/productos/{productoId}/propiedades")
    public ResponseEntity<?> crearPropiedadProducto(@PathVariable Long productoId,
                                                    @RequestBody PropiedadProducto propiedadProducto) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        try {
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProducto(productoId, propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/subcategorias/{subcategoriaId}/propiedades")
    public ResponseEntity<?> crearPropiedadProductoSubcategoria(@PathVariable Long subcategoriaId,
                                                                @RequestBody PropiedadProducto propiedadProducto) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        try {
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProductoSubcategoria(subcategoriaId, propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto para la subcategoria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
