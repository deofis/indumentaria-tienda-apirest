package com.deofis.tiendaapirest.clientes.services;

import com.deofis.tiendaapirest.clientes.domain.Estado;
import com.deofis.tiendaapirest.clientes.domain.Pais;

import java.util.List;

/**
 * Servicio que provee la lógica para listar los paises y estados cargados en la base
 * de datos.
 */

public interface PaisService {

    /**
     * Lista los paises de forma ordenada de menor a mayor por nombre.
     * @return List de paises.
     */
    List<Pais> listarPaises();

    /**
     * Obtener un pais por su id.
     * @param id Long id del pais.
     * @return Pais en la base de datos.
     */
    Pais obtenerPais(Long id);

    /**
     * Obtener un pais por su nombre.
     * @param nombrePais String nombre del pais.
     * @return Pais en la base de datos.
     */
    Pais obtenerPais(String nombrePais);

    /**
     * Lista los estados que pertenecen a un país seleccionoado.
     * @param nombrePais String del nombre del pais seleccionado.
     * @return List de estados del país.
     */
    List<Estado> estadosDePais(String nombrePais);
}
