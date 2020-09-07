package com.deofis.tiendaapirest.autenticacion.repository;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
