package com.deofis.tiendaapirest.productos.repository;

import com.deofis.tiendaapirest.productos.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findAllByOrderByNombreDesc();
}
