package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedadProducto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.SubcategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubcategoriaServiceImpl implements SubcategoriaService {

    private final SubcategoriaRepository subcategoriaRepository;

    private final PropiedadService propiedadService;

    @Override
    public List<Subcategoria> listarSubcategorias() {
        return this.subcategoriaRepository.findAll();
    }

    @Override
    public Subcategoria obtenerSubcategoria(Long subcategoriaId) {
        return this.subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new ProductoException("No existe subcategoria con id: " + subcategoriaId));
    }

    @Override
    public Subcategoria agregarPropiedad(Long subcategoriaId, PropiedadProducto propiedadProducto) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        PropiedadProducto nuevaPropiedadProducto = this.propiedadService.crearPropiedad(propiedadProducto);

        subcategoria.getPropiedades().add(nuevaPropiedadProducto);
        return this.subcategoriaRepository.save(subcategoria);
    }

    @Override
    public PropiedadProducto obtenerPropiedad(Long subcategoriaId, Long propiedadId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);
        PropiedadProducto propiedadProducto = this.propiedadService.obtenerPropiedad(propiedadId);

        if (!subcategoria.getPropiedades().contains(propiedadProducto)) {
            throw new ProductoException("La propiedad requerida no pertenece a la subcategoria: " +
                    subcategoria.getNombre());
        }

        return propiedadProducto;
    }

    @Override
    public List<PropiedadProducto> obtenerPropiedadesSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        return subcategoria.getPropiedades();
    }

    @Override
    public PropiedadProducto agregarValor(Long subcategoriaId, Long propiedadId, ValorPropiedadProducto valorPropiedadProducto) {
        PropiedadProducto propiedadProducto = this.obtenerPropiedad(subcategoriaId, propiedadId);

        // Se hace el llamado a obtener propiedad para verificar si pertenece a la subcategoría, para no repetir codigo
        // de verificación.

        return this.propiedadService.agregarValor(propiedadProducto.getId(), valorPropiedadProducto);
    }

    @Override
    public List<ValorPropiedadProducto> obtenerValoresPropiedad(Long subcategoriaId, Long propiedadId) {
        PropiedadProducto propiedadProducto = this.obtenerPropiedad(subcategoriaId, propiedadId);

        return propiedadProducto.getValores();
    }
}
