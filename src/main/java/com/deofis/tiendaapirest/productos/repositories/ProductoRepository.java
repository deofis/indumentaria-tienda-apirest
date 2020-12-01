package com.deofis.tiendaapirest.productos.repositories;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findAllByOrderByNombreAsc();

    List<Producto> findAllByDestacadoIsTrueAndActivoIsTrue();

    List<Producto> findAllByNombreContainingIgnoringCaseAndActivoIsTrueOrderByNombreAsc(String termino);

    List<Producto> findAllBySubcategoriaAndActivoIsTrue(Subcategoria subcategoria);

    List<Producto> findAllByMarcaAndActivoIsTrue(Marca marca);

    List<Producto> findAllByOrderByPrecioAsc();

    List<Producto> findAllByOrderByPrecioDesc();

    List<Producto> findAllByPrecioBetween(Double min, Double max);

    List<Producto> findAllBySubcategoria(Subcategoria subcategoria);
}
