package com.deofis.tiendaapirest.ecommerce.services;

import com.deofis.tiendaapirest.ecommerce.domain.Banner;
import com.deofis.tiendaapirest.globalservices.CrudService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Servicio que se encarga de subir y obtener las imágenes de los distintos
 * Banners cargados en el sistema.
 * <br>
 * Sólo pueden existir 4 (Cuatro) Banners dentro del Banner Config.
 */
public interface BannerService extends CrudService<Banner, Long> {

    /**
     * Crea un nuevo Banner y le carga la imagen pasada por parámetro. Para la
     * creación del Banner se debe verificar:
     *                  - BannerConfig no posee más de 4 Banners
     *                  - Si BannerConfig.banners > 4 se larga excepción para
     *                  sugerir utilizar el servicio de actualizar banner
     * @param imagen MultipartFile con la imagen a cargar en el banner.
     * @return {@link Banner} creado, con su imagen subida y guardado en la base de datos.
     */
    Banner subirBanner(MultipartFile imagen);

    /**
     * Actualiza la imagen que posee un banner ya creado.
     * @param imagen MultipartFile archivo con la nueva imagen.
     * @param bannerId Long id del banner a actualizar.
     * @return Banner actualizado y guardado en la base de datos.
     */
    Banner actualizarImagenBanner(MultipartFile imagen, Long bannerId);

    /**
     * Actualiza los datos del banner: action url / orden.
     * @param actionUrl String nuevo action url asociado al banner.
     * @param orden Integer nuevo orden del banner.
     * @return {@link Banner} actualizado y guardado en la base de datos.
     */
    Banner actualizarDatosBanner(String actionUrl, Integer orden);

    /**
     * Obtiene todos los banners creados.
     * @return List de todos los banners
     */
    List<Banner> obtenerBanners();

    /**
     * Obtiene un banner requerido a través de su id.
     * @param bannerId Long id del banner a obtener.
     * @return {@link Banner}.
     */
    Banner obtenerBanner(Long bannerId);

    /**
     * Elimina un banner.
     * @param bannerId Long id del banner.
     */
    void eliminarBanner(Long bannerId);
}
