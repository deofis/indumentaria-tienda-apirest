package com.deofis.tiendaapirest.perfiles.repositories;

import com.deofis.tiendaapirest.perfiles.domain.CambioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CambioRolRepository extends JpaRepository<CambioRol, Long> {

    /**
     * Obtiene de la base de datos todos los registros de Cambio de rol ordenados por fecha,
     * de manera ascendente.
     * @return List con los registros.
     */
    List<CambioRol> findAllByOrderByFechaCambioAsc();
}
