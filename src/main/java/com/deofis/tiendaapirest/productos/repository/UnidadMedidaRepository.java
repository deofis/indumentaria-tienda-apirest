package com.deofis.tiendaapirest.productos.repository;

import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

}
