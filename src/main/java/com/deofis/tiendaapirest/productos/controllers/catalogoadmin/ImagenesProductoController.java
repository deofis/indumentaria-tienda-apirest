package com.deofis.tiendaapirest.productos.controllers.catalogoadmin;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.deofis.tiendaapirest.productos.domain.Imagen;
import com.deofis.tiendaapirest.productos.exceptions.FileException;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CatalogoAdminService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ImagenesProductoController {

    private final CatalogoAdminService catalogoAdminService;

    /**
     * Sube y asocia foto principal a un {@link com.deofis.tiendaapirest.productos.domain.Producto} requerido.
     * URL: ~/api/productos/1/fotos/principal
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param productoId PathVariable Long id del producto a subir foto principal.
     * @param foto MultipartFile archivo que contiene la foto del producto a crear.
     * @return ResponseEntity {@link Imagen} con los datos de la foto creada y subida.
     */
    @PostMapping("/productos/{productoId}/fotos/principal")
    public ResponseEntity<?> subirFotoPrincipalProducto(@PathVariable Long productoId,
                                                        @RequestParam(name = "foto")MultipartFile foto) {
        Map<String, Object> response = new HashMap<>();
        Imagen fotoPrincipal;

        try {
            fotoPrincipal = this.catalogoAdminService.subirFotoPpalProducto(productoId, foto);
        } catch (ProductoException | FileException | AmazonS3Exception e) {
            response.put("mensaje", "Error al subir foto principal del producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("foto", fotoPrincipal);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Sube y asocia foto a un {@link com.deofis.tiendaapirest.productos.domain.Sku} requerido.
     * URL: ~/api/productos/skus//1/fotos
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param skuId PathVariable Long id del sku a subir foto.
     * @param foto RequestParam MultipartFile archivo que contiene la foto del sku a crear y subir.
     * @return ResponseEntity {@link Imagen} con los datos de la foto creada y subida.
     */
    @PostMapping("/productos/skus/{skuId}/fotos")
    public ResponseEntity<?> subirFotoSku(@PathVariable Long skuId, @RequestParam(name = "foto")MultipartFile foto) {
        Map<String, Object> response = new HashMap<>();
        Imagen fotoSku;

        try {
            fotoSku = this.catalogoAdminService.subirFotoSku(skuId, foto);
        } catch (ProductoException | FileException | AmazonS3Exception e) {
            response.put("mensaje", "Error al subir foto del sku");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("foto", fotoSku);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Descarga y obtiene la foto principal asociada a un {@link com.deofis.tiendaapirest.productos.domain.Producto}.
     * URL: ~/api/productos/1/fotos/principal
     * HttpMethod: GET
     * HttpStatus: OK
     * @param productoId PathVariable Long id del producto a obtener foto principal.
     * @return ResponseEntity con la imagen como recurso.
     */
    @GetMapping("/productos/{productoId}/fotos/principal")
    public ResponseEntity<?> obtenerFotoPrincipalProducto(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        byte[] fotoBytes;
        ByteArrayResource fotoAsResource;
        String imagePath;

        try {
            fotoBytes = this.catalogoAdminService.obtenerFotoPpalProducto(productoId);
            imagePath = this.catalogoAdminService.obtenerPathFotoPpalProducto(productoId);
            fotoAsResource = new ByteArrayResource(fotoBytes);
        } catch (ProductoException | FileException | AmazonS3Exception e) {
            response.put("mensaje", "Error al obtener foto principal del producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity
                .ok()
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + imagePath + "\"")
                .body(fotoAsResource);
    }

    /**
     * Descarga y obtiene la foto asociada a un {@link com.deofis.tiendaapirest.productos.domain.Sku}.
     * URL: ~/api/productos/skus/1/fotos
     * HttpMethod: GET
     * HttpStatus: OK
     * @param skuId PathVariable Long id del sku a obtener foto.
     * @return ResponseEntity con la imagen como recurso.
     */
    @GetMapping("/productos/skus/{skuId}/fotos")
    public ResponseEntity<?> obtenerFotoSku(@PathVariable Long skuId) {
        Map<String, Object> response = new HashMap<>();
        byte[] fotoBytes;
        ByteArrayResource fotoAsResource;
        String imagePath;

        try {
            fotoBytes = this.catalogoAdminService.obtenerFotoSku(skuId);
            imagePath = this.catalogoAdminService.obtenerPathFotoSku(skuId);
            fotoAsResource = new ByteArrayResource(fotoBytes);
        } catch (ProductoException | FileException | AmazonS3Exception e) {
            response.put("mensaje", "Error al obtener foto del sku");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity
                .ok()
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + imagePath + "\"")
                .body(fotoAsResource);
    }

    /**
     * Elimina la foto principal asociada a un {@link com.deofis.tiendaapirest.productos.domain.Producto}.
     * URL: ~/api/productos/1/fotos/principal
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param productoId PathVariable Long id del producto a eliminar foto principal.
     * @return ResponseEntity con mensaje éxito/error sobre la eliminación de la foto principal del producto.
     */
    @DeleteMapping("/productos/{productoId}/fotos/principal")
    public ResponseEntity<?> eliminarFotoPrincipalProducto(@PathVariable Long productoId) {
        Map<String, Object> respose = new HashMap<>();
        String msg;

        try {
            this.catalogoAdminService.eliminarFotoPpalProducto(productoId);
            msg = "Foto Principal del producto eliminada con éxito";
        } catch (ProductoException | FileException | AmazonS3Exception e) {
            respose.put("mensaje", "Error al eliminar foto principal del producto");
            respose.put("error", e.getMessage());
            return new ResponseEntity<>(respose, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respose.put("mensaje", msg);
        return new ResponseEntity<>(respose, HttpStatus.OK);
    }

    /**
     * Elimina la foto asociada a un {@link com.deofis.tiendaapirest.productos.domain.Sku}.
     * URL: ~/api/productos/skus/1/fotos
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param skuId PathVariable Long id del sku a eliminar foto.
     * @return ResponseEntity con mensaje éxito/error sobre la eliminación de la foto del sku.
     */
    @DeleteMapping("/productos/skus/{skuId}/fotos")
    public ResponseEntity<?> eliminarFotoSku(@PathVariable Long skuId) {
        Map<String, Object> respose = new HashMap<>();
        String msg;

        try {
            this.catalogoAdminService.eliminarFotoSku(skuId);
            msg = "Foto del sku eliminada con éxito";
        } catch (ProductoException | FileException | AmazonS3Exception e) {
            respose.put("mensaje", "Error al eliminar foto del sku");
            respose.put("error", e.getMessage());
            return new ResponseEntity<>(respose, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respose.put("mensaje", msg);
        return new ResponseEntity<>(respose, HttpStatus.OK);
    }
}
