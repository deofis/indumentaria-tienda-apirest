package com.deofis.tiendaapirest.pagos.repositories;

import com.deofis.tiendaapirest.pagos.domain.TarjetaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {

}
