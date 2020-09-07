package com.deofis.tiendaapirest.autenticacion.repository;

import com.deofis.tiendaapirest.autenticacion.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

}
