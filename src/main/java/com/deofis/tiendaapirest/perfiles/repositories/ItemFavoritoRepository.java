package com.deofis.tiendaapirest.perfiles.repositories;

import com.deofis.tiendaapirest.perfiles.domain.ItemFavorito;
import com.deofis.tiendaapirest.productos.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFavoritoRepository extends JpaRepository<ItemFavorito, Long> {

    void deleteByProducto(Producto producto);

}
