package com.deofis.tiendaapirest.autenticacion.repository;

import com.deofis.tiendaapirest.autenticacion.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Role, Long> {

}
