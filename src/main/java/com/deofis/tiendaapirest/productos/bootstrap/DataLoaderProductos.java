package com.deofis.tiendaapirest.productos.bootstrap;

import com.deofis.tiendaapirest.pagos.domain.MedioPago;
import com.deofis.tiendaapirest.pagos.repositories.MedioPagoRepository;
import com.deofis.tiendaapirest.productos.domain.*;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.CategoriaRepository;
import com.deofis.tiendaapirest.productos.repositories.MarcaRepository;
import com.deofis.tiendaapirest.productos.repositories.UnidadMedidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class DataLoaderProductos implements CommandLineRunner {

    private final UnidadMedidaRepository unidadMedidaRepository;
    private final MarcaRepository marcaRepository;
    private final MedioPagoRepository medioPagoRepository;

    private final CategoriaRepository categoriaRepository;

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

        // CARGA DE CATEGORIAS Y SUBCATEGORIAS

        if (this.categoriaRepository.findByNombre("Tecnología").isEmpty()) {
            Subcategoria celulares;

            // Propiedad modelo
            Propiedad modelo;
            ValorPropiedad s20;

            s20 = ValorPropiedad.builder().valor("Galaxy S20").build();

            modelo = Propiedad.builder().nombre("Modelo").valores(new ArrayList<>()).build();
            modelo.getValores().add(s20);

            // Propiedad color
            Propiedad color;
            ValorPropiedad negro = ValorPropiedad.builder().valor("Negro").build();
            ValorPropiedad gris = ValorPropiedad.builder().valor("Gris").build();
            ValorPropiedad dorado = ValorPropiedad.builder().valor("Droado").build();

            color = Propiedad.builder().nombre("Color").valores(new ArrayList<>()).build();
            color.getValores().add(negro);
            color.getValores().add(gris);
            color.getValores().add(dorado);

            celulares = Subcategoria.builder().nombre("Celulares").propiedades(new ArrayList<>()).codigo("CEL").build();
            celulares.getPropiedades().add(modelo);
            celulares.getPropiedades().add(color);

            Categoria tecnologia = Categoria.builder()
                    .nombre("Tecnología")
                    .subcategorias(new ArrayList<>())
                    .build();

            tecnologia.getSubcategorias().add(celulares);
            this.categoriaRepository.save(tecnologia);
        }

        /*
        if (this.categoriaRepository.findByNombre("Termos").isEmpty()) {
            Subcategoria subcategoria;

            Categoria termos = Categoria.builder()
                    .nombre("Termos")
                    .codigo("TER")
                    .build();

            this.categoriaRepository.save(termos);
        }
         */

    }
}
