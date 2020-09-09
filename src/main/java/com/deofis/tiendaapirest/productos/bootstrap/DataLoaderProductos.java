package com.deofis.tiendaapirest.productos.bootstrap;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.repository.CategoriaRepository;
import com.deofis.tiendaapirest.productos.repository.MarcaRepository;
import com.deofis.tiendaapirest.productos.repository.UnidadMedidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoaderProductos implements CommandLineRunner {

    private final UnidadMedidaRepository unidadMedidaRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!this.categoriaRepository.findByNombre("Celulares").isPresent()) {
            Categoria celulares = Categoria.builder()
                    .nombre("Celulares")
                    .codigo("CELULAR")
                    .build();
            this.categoriaRepository.save(celulares);
        }

        if (!this.marcaRepository.findByNombre("Samsung").isPresent()) {
            Marca samsung = Marca.builder()
                    .nombre("Samsung")
                    .build();
            this.marcaRepository.save(samsung);
        }

        if (!this.marcaRepository.findByNombre("iPhone").isPresent()) {
            Marca iphone = Marca.builder()
                    .nombre("iPhone")
                    .build();
            this.marcaRepository.save(iphone);
        }

        if (!this.unidadMedidaRepository.findByNombre("Unidad").isPresent()) {
            UnidadMedida unidad = UnidadMedida.builder()
                    .nombre("Unidad")
                    .codigo("U")
                    .build();
            this.unidadMedidaRepository.save(unidad);
        }

        if (!this.unidadMedidaRepository.findByNombre("Kilo").isPresent()) {
            UnidadMedida kilo = UnidadMedida.builder()
                    .nombre("Kilo")
                    .codigo("KG")
                    .build();
            this.unidadMedidaRepository.save(kilo);
        }

        if (!this.unidadMedidaRepository.findByNombre("Litro").isPresent()) {
            UnidadMedida litro = UnidadMedida.builder()
                    .nombre("Litro")
                    .codigo("L")
                    .build();
            this.unidadMedidaRepository.save(litro);
        }

    }
}
