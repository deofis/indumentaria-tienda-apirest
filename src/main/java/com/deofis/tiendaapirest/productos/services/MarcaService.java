package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Marca;

import java.util.List;

public interface MarcaService {

    /**
     * Método para registrar una nueva marca.
     * @param marca Marca a guardar.
     * @return Marca guardada en la BD.
     */
    Marca crear(Marca marca);

    /**
     * Método para obtener todas las marcas ordenadas por nombre asc.
     * @return Lista de todas las marcas en la BD.
     */
    List<Marca> obtenerMarcas();

    /**
     * Método para obtener una marca específica por su Id.
     * @param id Long id de la marca a obtener.
     * @return Marca en la BD con el id solicitado.
     */
    Marca obtenerMarca(Long id);

    /**
     * Método para actualizar una marca.
     * @param marca Marca modificada.
     * @param id Long id de la marca a modificar.
     * @return Marca actualizada.
     */
    Marca actualizar(Marca marca, Long id);
}
