package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Propiedad;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedad;

public interface PropiedadService {

    /**
     * Obtiene una propiedad a través de su id.
     *
     * @param propiedadId Long id de la propiedad requerida.
     * @return Propiedad.
     */
    Propiedad obtenerPropiedad(Long propiedadId);

    /**
     * Crea una nueva propiedad.
     *
     * @param propiedad Propiedad nueva a crear.
     * @return Propeidad creada.
     */
    Propiedad crearPropiedad(Propiedad propiedad);

    /**
     * Agrega un nuevo valor a una propiedad requerida.
     * @param propiedadId Long id de la propiedad a la cual agregar el nuevo valor.
     * @param valorPropiedad ValorPropiedad a agregar.
     * @return Propiedad actualizada con el nuevo valor ya agregado.
     */
    Propiedad agregarValor(Long propiedadId, ValorPropiedad valorPropiedad);
}
