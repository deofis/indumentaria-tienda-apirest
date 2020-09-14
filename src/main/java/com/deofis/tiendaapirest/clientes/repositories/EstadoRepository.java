package com.deofis.tiendaapirest.clientes.repositories;

import com.deofis.tiendaapirest.clientes.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
