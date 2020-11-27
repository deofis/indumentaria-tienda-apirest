package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Imagen;
import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.exceptions.CategoriaException;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.SubcategoriaRepository;
import com.deofis.tiendaapirest.productos.services.images.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class SubcategoriaServiceImpl implements SubcategoriaService {

    private final SubcategoriaRepository subcategoriaRepository;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    @Override
    public List<Subcategoria> obtenerSubcategorias() {
        return this.subcategoriaRepository.findAll();
    }

    @Override
    public Subcategoria obtenerSubcategoria(Long subcategoriaId) {
        return this.subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new ProductoException("No existe subcategoria con id: " + subcategoriaId));
    }

    @Transactional(readOnly = true)
    @Override
    public PropiedadProducto obtenerPropiedad(Long subcategoriaId, Long propiedadId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);
        PropiedadProducto propiedadProducto = null;

        boolean existePropiedad = false;
        for (PropiedadProducto propiedad: subcategoria.getPropiedades()) {
            if (propiedad.getId().equals(propiedadId)) {
                propiedadProducto = propiedad;
                existePropiedad = true;
                break;
            }
        }

        if (!existePropiedad) {
            throw new ProductoException("La propiedad requerida no pertenece a la subcategoria: " +
                    subcategoria.getNombre());
        }

        return propiedadProducto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PropiedadProducto> obtenerPropiedadesSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        return subcategoria.getPropiedades();
    }

    @Transactional
    @Override
    public Imagen subirFotoSubcategoria(Long subcategoriaId, MultipartFile foto) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        if (subcategoria.getFoto() != null)
            this.eliminarFotoSubcategoria(subcategoria.getId());

        Imagen fotoSubcategoria = this.imageService.subirImagen(foto);
        subcategoria.setFoto(fotoSubcategoria);
        this.subcategoriaRepository.save(subcategoria);
        return fotoSubcategoria;
    }

    @Transactional(readOnly = true)
    @Override
    public byte[] obtenerFotoSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);
        Imagen fotoSubcategoria = subcategoria.getFoto();

        if (fotoSubcategoria == null) throw new CategoriaException("La subcategoría: " + subcategoria.getNombre()
                + " no tiene foto");

        return this.imageService.descargarImagen(fotoSubcategoria);
    }

    @Transactional(readOnly = true)
    @Override
    public String obtenerPathFotoSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);
        return subcategoria.getFoto().getPath();
    }

    @Transactional
    @Override
    public void eliminarFotoSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        if (subcategoria.getFoto() == null) throw new CategoriaException("La subcategoría: " + subcategoria.getNombre()
                + " no tiene foto");

        Imagen fotoSubcategoria = subcategoria.getFoto();
        subcategoria.setFoto(null);
        this.subcategoriaRepository.save(subcategoria);
        this.imageService.eliminarImagen(fotoSubcategoria);
    }
}
