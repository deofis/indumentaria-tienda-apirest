package com.deofis.tiendaapirest.autenticacion.repositories;

import com.deofis.tiendaapirest.autenticacion.domain.CambioHabilitacionUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CambioHabilitacionRepository extends JpaRepository<CambioHabilitacionUsuarios, Long> {

}
