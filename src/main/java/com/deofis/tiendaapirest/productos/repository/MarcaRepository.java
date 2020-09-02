package com.deofis.tiendaapirest.productos.repository;

import com.deofis.tiendaapirest.productos.domain.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    List<Marca> findAllByOrderByNombreAsc();
}
