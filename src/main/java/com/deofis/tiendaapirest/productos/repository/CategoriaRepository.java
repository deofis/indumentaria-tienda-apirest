package com.deofis.tiendaapirest.productos.repository;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
