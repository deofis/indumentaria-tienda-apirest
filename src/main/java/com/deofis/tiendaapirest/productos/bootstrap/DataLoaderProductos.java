package com.deofis.tiendaapirest.productos.bootstrap;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.CategoriaRepository;
import com.deofis.tiendaapirest.productos.repositories.MarcaRepository;
import com.deofis.tiendaapirest.productos.repositories.UnidadMedidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Profile({"qa", "qaheroku", "dev"})
public class DataLoaderProductos implements CommandLineRunner {

    private final UnidadMedidaRepository unidadMedidaRepository;
    private final MarcaRepository marcaRepository;

    private final CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) {

        if (this.unidadMedidaRepository.findByNombre("Unidad").isEmpty()) {
            UnidadMedida unidad = UnidadMedida.builder()
                    .nombre("Unidad")
                    .codigo("U").build();

            try {
                this.unidadMedidaRepository.save(unidad);
            } catch (DataIntegrityViolationException e) {
                throw new ProductoException(e.getMessage());
            }
        }

        if (this.marcaRepository.findAll().size() == 0) {
            List<Marca> marcas = new ArrayList<>();

            Marca puma = Marca.builder().nombre("Puma").build();
            marcas.add(puma);

            Marca topper = Marca.builder().nombre("Topper").build();
            marcas.add(topper);

            Marca jaguar = Marca.builder().nombre("Jaguar").build();
            marcas.add(jaguar);

            Marca jFoos = Marca.builder().nombre("John Foos").build();
            marcas.add(jFoos);

            Marca converse = Marca.builder().nombre("Converse").build();
            marcas.add(converse);

            Marca adidas = Marca.builder().nombre("Adidas").build();
            marcas.add(adidas);

            Marca nike = Marca.builder().nombre("Nike").build();
            marcas.add(nike);

            this.marcaRepository.saveAll(marcas);
        }

        // Carga de categorías y subcategorías

        if (this.categoriaRepository.findAll().size() == 0) {
            // Listado de subcategorias
            Subcategoria remeras = Subcategoria.builder()
                    .nombre("Remeras").codigo("REM").propiedades(new ArrayList<>()).build();

            Subcategoria pantalones = Subcategoria.builder()
                    .nombre("Pantalones").codigo("PAN").propiedades(new ArrayList<>()).build();

            Subcategoria zapatillas = Subcategoria.builder()
                    .nombre("Zapatillas").codigo("ZAP").propiedades(new ArrayList<>()).build();

            Subcategoria zapatosH = Subcategoria.builder()
                    .nombre("Zapatos").codigo("ZAT").propiedades(new ArrayList<>()).build();

            Subcategoria accesoriosH = Subcategoria.builder()
                    .nombre("Accesorios").codigo("ACC").propiedades(new ArrayList<>()).build();

            Subcategoria blusas = Subcategoria.builder()
                    .nombre("Blusas").codigo("BLU").propiedades(new ArrayList<>()).build();

            Subcategoria polleras = Subcategoria.builder()
                    .nombre("Polleras").codigo("POL").propiedades(new ArrayList<>()).build();

            Subcategoria sandalias = Subcategoria.builder()
                    .nombre("Sandalias").codigo("SAN").propiedades(new ArrayList<>()).build();

            Subcategoria zapatosM = Subcategoria.builder()
                    .nombre("Zapatos").codigo("ZAT").propiedades(new ArrayList<>()).build();

            Subcategoria accesoriosM = Subcategoria.builder()
                    .nombre("Accesorios").codigo("ACC").propiedades(new ArrayList<>()).build();

            Subcategoria remerasN = Subcategoria.builder()
                    .nombre("Remeras").codigo("REM").propiedades(new ArrayList<>()).build();

            Subcategoria blusasN = Subcategoria.builder()
                    .nombre("Blusas").codigo("BLU").propiedades(new ArrayList<>()).build();

            Subcategoria pantalonesN = Subcategoria.builder()
                    .nombre("Pantalones").codigo("PAN").propiedades(new ArrayList<>()).build();

            Subcategoria sandaliasN = Subcategoria.builder()
                    .nombre("Sandalias").codigo("SAN").propiedades(new ArrayList<>()).build();

            Subcategoria juguetes = Subcategoria.builder()
                    .nombre("Juguetes y Accesorios").codigo("JUG").propiedades(new ArrayList<>()).build();

            // Listado de categorias
            List<Categoria> categorias = new ArrayList<>();

            Categoria hombre = Categoria.builder()
                    .nombre("Hombre").subcategorias(new ArrayList<>()).build();
            hombre.getSubcategorias().add(remeras);
            hombre.getSubcategorias().add(pantalones);
            hombre.getSubcategorias().add(zapatillas);
            hombre.getSubcategorias().add(zapatosH);
            hombre.getSubcategorias().add(accesoriosH);
            categorias.add(hombre);

            Categoria mujer = Categoria.builder()
                    .nombre("Mujer").subcategorias(new ArrayList<>()).build();
            mujer.getSubcategorias().add(blusas);
            mujer.getSubcategorias().add(polleras);
            mujer.getSubcategorias().add(sandalias);
            mujer.getSubcategorias().add(zapatosM);
            mujer.getSubcategorias().add(accesoriosM);
            categorias.add(mujer);

            Categoria ninos = Categoria.builder()
                    .nombre("Niños").subcategorias(new ArrayList<>()).build();
            ninos.getSubcategorias().add(remerasN);
            ninos.getSubcategorias().add(blusasN);
            ninos.getSubcategorias().add(pantalonesN);
            ninos.getSubcategorias().add(sandaliasN);
            ninos.getSubcategorias().add(juguetes);
            categorias.add(ninos);

            // Guardar categorias y subcategorias
            this.categoriaRepository.saveAll(categorias);
        }
    }
}
