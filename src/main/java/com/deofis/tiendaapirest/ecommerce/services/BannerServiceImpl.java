package com.deofis.tiendaapirest.ecommerce.services;

import com.deofis.tiendaapirest.ecommerce.domain.Banner;
import com.deofis.tiendaapirest.ecommerce.exceptions.BannerException;
import com.deofis.tiendaapirest.ecommerce.repositories.BannerRepository;
import com.deofis.tiendaapirest.productos.domain.Imagen;
import com.deofis.tiendaapirest.productos.services.images.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    private final ImageService imageService;

    private final String clientUrl;

    @Override
    public Banner subirBanner(String actionUrl, Integer orden, MultipartFile imagen) {

        // Mantenemos referencia de cuantos banners existen para no superar el número
        // máximo.
        int cantBanners = this.findAll().size();

        if (cantBanners > 5)
            throw new BannerException("Ya existen 4 Banners en la aplicación cargados. Porfavor, " +
                    "actualice un banner existente");

        Imagen imagenBanner = this.imageService.subirImagen(imagen);

        Banner banner = Banner.builder()
                .imagen(imagenBanner)
                .actionUrl(this.clientUrl.concat("/" + actionUrl))
                .orden(orden).build();

        return this.save(banner);
    }

    @Override
    public Banner actualizarImagenBanner(MultipartFile imagen, Long bannerId) {
        return null;
    }

    @Override
    public Banner actualizarDatosBanner(String actionUrl, Integer orden) {
        return null;
    }

    @Override
    public List<Banner> obtenerBanners() {
        return null;
    }

    @Override
    public Banner obtenerBanner(Long bannerId) {
        return null;
    }

    @Override
    public void eliminarBanner(Long bannerId) {

    }

    @Transactional(readOnly = true)
    @Override
    public List<Banner> findAll() {
        return this.bannerRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Banner findById(Long aLong) {
        return this.bannerRepository.findById(aLong)
                .orElseThrow(() -> new BannerException("No existe el banner con id: " + aLong));
    }

    @Transactional
    @Override
    public Banner save(Banner object) {
        return this.bannerRepository.save(object);
    }

    @Transactional
    @Override
    public void delete(Banner object) {
        this.bannerRepository.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) {
        this.bannerRepository.deleteById(aLong);
    }
}
