package com.deofis.tiendaapirest.pagos.repositories;

import com.deofis.tiendaapirest.pagos.domain.FormaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagoRepository extends JpaRepository<FormaPago, Long> {

}
