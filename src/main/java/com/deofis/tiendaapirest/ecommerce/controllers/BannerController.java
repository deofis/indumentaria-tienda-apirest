package com.deofis.tiendaapirest.ecommerce.controllers;

import com.deofis.tiendaapirest.ecommerce.domain.Banner;
import com.deofis.tiendaapirest.ecommerce.exceptions.BannerException;
import com.deofis.tiendaapirest.ecommerce.services.BannerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    /**
     * API que se encarga de crear un nuevo banner con sus datos correspondientes:
     * action url y orden, y archivo que contiene la imagen del banner a crear.
     * <br>
     * Esta API consume el servicio {@link BannerService} subirBanner para la creación del Banner.
     * URL: ~/api/banners
     * HttpMethod: POST
     * HettpStatus: CREATED
     * @param actionUrl String action url.
     * @param orden Integer orden.
     * @param foto MultipartFile con el archivo imagen a subir para el nuevo Banner.
     * @return ResponseEntity<Map<String, Object>> con la response: el Banner creado.
     */
    @PostMapping("/banners")
    public ResponseEntity<?> crearNuevoBanner(@RequestParam String actionUrl,
                                              @RequestParam Integer orden,
                                              @RequestParam(name = "foto") MultipartFile foto) {
        Map<String, Object> response = new HashMap<>();
        Banner nuevoBanner;

        try {
            nuevoBanner = this.bannerService.subirBanner(actionUrl, orden, foto);
        } catch (BannerException e) {
            response.put("mensaje", "Error al crear el nuevo banner");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("banner", nuevoBanner);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * API para actualizar sólo la imagen de un Banner requerido.
     * URL: ~/api/banners/1/imagen
     * HttpMethod: PUT
     * HttpStatus: OK
     * @param foto MultipartFile nueva imagen.
     * @param bannerId Long id del Banner.
     * @return ResponseEntity con el Banner actualizado.
     */
    @PutMapping("/banners/{bannerId}/imagen")
    public ResponseEntity<?> actualizarImagenBanner(@RequestParam(name = "foto") MultipartFile foto,
                                                    @PathVariable Long bannerId) {
        Map<String, Object> response = new HashMap<>();
        Banner bannerActualizado;

        try {
            bannerActualizado = this.bannerService.actualizarImagenBanner(foto, bannerId);
        } catch (BannerException e) {
            response.put("mensaje", "Error al actualizar la imagen del Banner");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("banner", bannerActualizado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * API que se encarga de actualizar el action url de un Banner requerido.
     * URL: ~/api/banners/1/actionUrl
     * HttpMethod: PUT
     * HttpStatus: OK
     * @param bannerId Long id del Banner.
     * @param actionUrl String nuevo action url.
     * @return ResponseEntity con el Banner actualizado.
     */
    @PutMapping("/banners/{bannerId}/actionUrl")
    public ResponseEntity<?> actualizarActionUrlBanner(@PathVariable Long bannerId,
                                                       @RequestParam String actionUrl) {
        Map<String, Object> response = new HashMap<>();
        Banner bannerActualizado;

        try {
            bannerActualizado = this.bannerService.actualizarActionUrl(bannerId, actionUrl);
        } catch (BannerException e) {
            response.put("mensaje", "Error al actualizar el action url del Banner");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("banner", bannerActualizado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
