package com.deofis.tiendaapirest.productos.bootstrap;

import com.deofis.tiendaapirest.pagos.domain.MedioPago;
import com.deofis.tiendaapirest.pagos.repositories.MedioPagoRepository;
import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.CategoriaRepository;
import com.deofis.tiendaapirest.productos.repositories.MarcaRepository;
import com.deofis.tiendaapirest.productos.repositories.UnidadMedidaRepository;
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
    private final MedioPagoRepository medioPagoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (this.unidadMedidaRepository.findByNombre("Unidad").isEmpty()) {
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

        if (this.unidadMedidaRepository.findByNombre("Kilo").isEmpty()) {
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

        if (this.unidadMedidaRepository.findByNombre("Litro").isEmpty()) {
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

        if (this.categoriaRepository.findByNombre("Celulares").isEmpty()) {
            Categoria celulares = Categoria.builder()
                    .nombre("Celulares")
                    .codigo("CEL")
                    .build();

            this.categoriaRepository.save(celulares);
        }

        if (this.categoriaRepository.findByNombre("Termos").isEmpty()) {
            Categoria termos = Categoria.builder()
                    .nombre("Termos")
                    .codigo("TER")
                    .build();

            this.categoriaRepository.save(termos);
        }

        if (this.marcaRepository.findByNombre("Samsung").isEmpty()) {
            Marca samsung = Marca.builder()
                    .nombre("Samsung")
                    .build();

            this.marcaRepository.save(samsung);
        }

        if (this.marcaRepository.findByNombre("Apple").isEmpty()) {
            Marca apple = Marca.builder()
                    .nombre("Apple")
                    .build();

            this.marcaRepository.save(apple);
        }

        if (this.marcaRepository.findByNombre("Stanley").isEmpty()) {
            Marca stanley = Marca.builder()
                    .nombre("Stanley")
                    .build();

            this.marcaRepository.save(stanley);
        }

        if (this.medioPagoRepository.findByNombre("Efectivo").isEmpty()) {
            MedioPago efectivo = new MedioPago();
            efectivo.setNombre("Efectivo");

            this.medioPagoRepository.save(efectivo);
        }

        if (this.medioPagoRepository.findByNombre("PayPal").isEmpty()) {
            MedioPago paypal = new MedioPago();
            paypal.setNombre("PayPal");

            this.medioPagoRepository.save(paypal);
        }

    }
}
