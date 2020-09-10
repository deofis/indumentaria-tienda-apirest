package com.deofis.tiendaapirest.autenticacion.repository;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Obtiene un usuario por el email.
     * @param email String email del usuario.
     * @return Optional con el usuario.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Obtiene los usuarios ordenados por fecha de creación de manera descendiente.
     * @return List usuarios;
     */
    List<Usuario> findAllByOrderByFechaCreacionDesc();
}
