package com.deofis.tiendaapirest.perfiles.repositories;

import com.deofis.tiendaapirest.perfiles.domain.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
