package com.deofis.tiendaapirest.clientes.repositories;

import com.deofis.tiendaapirest.clientes.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Optional<Estado> findByNombre(String estado);
}
