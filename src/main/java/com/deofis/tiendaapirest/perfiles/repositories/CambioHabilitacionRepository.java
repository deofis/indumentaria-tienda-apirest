package com.deofis.tiendaapirest.perfiles.repositories;

import com.deofis.tiendaapirest.perfiles.domain.CambioHabilitacionUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CambioHabilitacionRepository extends JpaRepository<CambioHabilitacionUsuarios, Long> {

    /**
     * Obtiene de la base de datos todos los registros de habilitaciones de usuarios ordenados por fecha,
     * de manera ascendente.
     * @return List con los registros.
     */
    List<CambioHabilitacionUsuarios> findAllByOrderByFechaCambioAsc();

}
