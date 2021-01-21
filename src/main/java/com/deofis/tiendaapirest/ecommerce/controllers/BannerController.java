package com.deofis.tiendaapirest.ecommerce.controllers;

import com.deofis.tiendaapirest.ecommerce.domain.Banner;
import com.deofis.tiendaapirest.ecommerce.exceptions.BannerException;
import com.deofis.tiendaapirest.ecommerce.exceptions.SingletonException;
import com.deofis.tiendaapirest.ecommerce.services.BannerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
@AllArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    /**
     * API que se encarga de crear un nuevo banner con sus datos correspondientes:
     * action url y orden, y archivo que contiene la imagen del banner a crear.
     * <br>
     * Esta API consume el servicio {@link BannerService} subirBanner para la creación del Banner.
     * URL: ~/api/config/banners
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
        } catch (SingletonException | BannerException e) {
            response.put("error", "Error al crear el nuevo banner");
            response.put("mensaje", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("banner", nuevoBanner);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
