package com.deofis.tiendaapirest.perfiles.repositories;

import com.deofis.tiendaapirest.perfiles.domain.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritosRepository extends JpaRepository<Favorito, Long> {

}
