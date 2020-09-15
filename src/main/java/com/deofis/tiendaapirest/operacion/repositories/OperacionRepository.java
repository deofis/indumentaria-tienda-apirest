package com.deofis.tiendaapirest.operacion.repositories;

import com.deofis.tiendaapirest.operacion.domain.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperacionRepository extends JpaRepository<Operacion, Long> {

    List<Operacion> findAllByOrderByFechaOperacionDesc();

}
