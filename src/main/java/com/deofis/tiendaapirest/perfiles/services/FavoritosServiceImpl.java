package com.deofis.tiendaapirest.perfiles.services;

import com.deofis.tiendaapirest.perfiles.domain.Favoritos;
import com.deofis.tiendaapirest.perfiles.domain.ItemFavorito;
import com.deofis.tiendaapirest.perfiles.repositories.FavoritosRepository;
import com.deofis.tiendaapirest.perfiles.repositories.ItemFavoritoRepository;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.services.ProductoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class FavoritosServiceImpl implements FavoritosService {

    private final FavoritosRepository favoritosRepository;
    private final ItemFavoritoRepository itemFavoritoRepository;
    private final ProductoService productoService;
    private final PerfilService perfilService;

    @Transactional
    @Override
    public Favoritos agregarFavorito(Long productoId) {
        boolean existeProd = false;
        Favoritos favoritos = this.perfilService.obtenerFavoritos();
        Producto productoAgregar = this.productoService.obtenerProducto(productoId);

        for (ItemFavorito item: favoritos.getItems()) {
            if (item.getProducto().equals(productoAgregar)) {
                existeProd = true;
                log.info("Producto ya cargado en favs");
            }
        }

        if (!existeProd) {
            ItemFavorito item = ItemFavorito.builder()
                    .producto(productoAgregar).build();

            favoritos.getItems().add(item);
            return this.favoritosRepository.save(favoritos);
        }

        return null;
    }

    @Transactional
    @Override
    public Favoritos quitarFavorito(Long productoId) {
        Favoritos favoritos = this.perfilService.obtenerFavoritos();
        Producto productoQuitar = this.productoService.obtenerProducto(productoId);

        favoritos.getItems().removeIf(item -> item.getProducto().equals(productoQuitar));
        this.itemFavoritoRepository.deleteByProducto(productoQuitar);

        return this.favoritosRepository.save(favoritos);
    }
}
