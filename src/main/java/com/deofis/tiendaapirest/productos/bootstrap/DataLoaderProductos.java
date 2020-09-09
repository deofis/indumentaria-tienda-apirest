package com.deofis.tiendaapirest.productos.bootstrap;

import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.repository.CategoriaRepository;
import com.deofis.tiendaapirest.productos.repository.MarcaRepository;
import com.deofis.tiendaapirest.productos.repository.UnidadMedidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoaderProductos implements CommandLineRunner {

    private final UnidadMedidaRepository unidadMedidaRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!this.unidadMedidaRepository.findByNombre("Unidad").isPresent()) {
            UnidadMedida unidad = UnidadMedida.builder()
                    .nombre("Unidad")
                    .codigo("U")
                    .build();
            try {
                this.unidadMedidaRepository.save(unidad);
            } catch (DataIntegrityViolationException e) {
                throw new ProductoException(e.getMessage());
            }

        }

        if (!this.unidadMedidaRepository.findByNombre("Kilo").isPresent()) {
            UnidadMedida kilo = UnidadMedida.builder()
                    .nombre("Kilo")
                    .codigo("KG")
                    .build();

            try {
                this.unidadMedidaRepository.save(kilo);
            } catch (DataIntegrityViolationException e) {
                throw new ProductoException(e.getMessage());
            }

        }

        if (!this.unidadMedidaRepository.findByNombre("Litro").isPresent()) {
            UnidadMedida litro = UnidadMedida.builder()
                    .nombre("Litro")
                    .codigo("L")
                    .build();

            try {
                this.unidadMedidaRepository.save(litro);
            } catch (DataIntegrityViolationException e) {
                throw new ProductoException(e.getMessage());
            }

        }

    }
}
