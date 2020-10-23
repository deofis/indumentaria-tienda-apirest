package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Propiedad;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedad;
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
    public Subcategoria agregarPropiedad(Long subcategoriaId, Propiedad propiedad) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        Propiedad nuevaPropiedad = this.propiedadService.crearPropiedad(propiedad);

        subcategoria.getPropiedades().add(nuevaPropiedad);
        return this.subcategoriaRepository.save(subcategoria);
    }

    @Override
    public Propiedad obtenerPropiedad(Long subcategoriaId, Long propiedadId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);
        Propiedad propiedad = this.propiedadService.obtenerPropiedad(propiedadId);

        if (!subcategoria.getPropiedades().contains(propiedad)) {
            throw new ProductoException("La propiedad requerida no pertenece a la subcategoria: " +
                    subcategoria.getNombre());
        }

        return propiedad;
    }

    @Override
    public List<Propiedad> obtenerPropiedadesSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        return subcategoria.getPropiedades();
    }

    @Override
    public Propiedad agregarValor(Long subcategoriaId, Long propiedadId, ValorPropiedad valorPropiedad) {
        Propiedad propiedad = this.obtenerPropiedad(subcategoriaId, propiedadId);

        // Se hace el llamado a obtener propiedad para verificar si pertenece a la subcategoría, para no repetir codigo
        // de verificación.

        return this.propiedadService.agregarValor(propiedad.getId(), valorPropiedad);
    }

    @Override
    public List<ValorPropiedad> obtenerValoresPropiedad(Long subcategoriaId, Long propiedadId) {
        Propiedad propiedad = this.obtenerPropiedad(subcategoriaId, propiedadId);

        return propiedad.getValores();
    }
}
