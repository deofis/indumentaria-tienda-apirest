package com.deofis.tiendaapirest.operaciones.repositories;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperacionRepository extends JpaRepository<Operacion, Long> {

    List<Operacion> findAllByOrderByFechaOperacionDesc();

    List<Operacion> findAllByClienteOrderByFechaOperacionAsc(Cliente cliente);

    Optional<Operacion> findByNroOperacionAndCliente(Long nroOperacion, Cliente cliente);
}
