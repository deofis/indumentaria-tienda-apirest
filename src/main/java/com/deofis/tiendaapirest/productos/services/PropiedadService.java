package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedadProducto;

public interface PropiedadService {

    /**
     * Obtiene una propiedad a través de su id.
     *
     * @param propiedadId Long id de la propiedad requerida.
     * @return Propiedad.
     */
    PropiedadProducto obtenerPropiedad(Long propiedadId);

    /**
     * Crea una nueva propiedad.
     *
     * @param propiedadProducto Propiedad nueva a crear.
     * @return Propeidad creada.
     */
    PropiedadProducto crearPropiedad(PropiedadProducto propiedadProducto);

    /**
     * Agrega un nuevo valor a una propiedad requerida.
     * @param propiedadId Long id de la propiedad a la cual agregar el nuevo valor.
     * @param valorPropiedadProducto ValorPropiedad a agregar.
     * @return Propiedad actualizada con el nuevo valor ya agregado.
     */
    PropiedadProducto agregarValor(Long propiedadId, ValorPropiedadProducto valorPropiedadProducto);
}
