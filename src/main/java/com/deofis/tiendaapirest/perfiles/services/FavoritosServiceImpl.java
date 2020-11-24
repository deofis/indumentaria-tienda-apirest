package com.deofis.tiendaapirest.perfiles.services;

import com.deofis.tiendaapirest.perfiles.domain.Favoritos;
import com.deofis.tiendaapirest.perfiles.domain.ItemFavorito;
import com.deofis.tiendaapirest.perfiles.repositories.FavoritosRepository;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class FavoritosServiceImpl implements FavoritosService {

    private final FavoritosRepository favoritosRepository;
    private final ProductoRepository productoRepository;
    private final PerfilService perfilService;

    @Transactional
    @Override
    public Favoritos agregarFavorito(Long productoId) {
        boolean existeProd = false;
        Favoritos favoritos = this.perfilService.obtenerFavoritos();
        Producto productoAgregar = this.obtenerProducto(productoId);

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
        Producto productoQuitar = this.obtenerProducto(productoId);

        favoritos.getItems().removeIf(item -> item.getProducto().equals(productoQuitar));
        return this.favoritosRepository.save(favoritos);
    }

    private Producto obtenerProducto(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoException("Producto no encontrado con id " + id));
    }
}
