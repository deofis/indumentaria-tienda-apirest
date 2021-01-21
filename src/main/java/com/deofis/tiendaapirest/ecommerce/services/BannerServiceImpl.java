package com.deofis.tiendaapirest.ecommerce.services;

import com.deofis.tiendaapirest.ecommerce.domain.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BannerServiceImpl implements BannerService {
    @Override
    public Banner subirBanner(MultipartFile imagen) {
        return null;
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

    @Override
    public List<Banner> findAll() {
        return null;
    }

    @Override
    public Banner findById(Long aLong) {
        return null;
    }

    @Override
    public Banner save(Banner object) {
        return null;
    }

    @Override
    public void delete(Banner object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
