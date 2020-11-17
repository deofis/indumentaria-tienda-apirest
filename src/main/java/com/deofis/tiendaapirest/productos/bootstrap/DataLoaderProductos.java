package com.deofis.tiendaapirest.productos.bootstrap;

import com.deofis.tiendaapirest.pagos.domain.MedioPago;
import com.deofis.tiendaapirest.pagos.repositories.MedioPagoRepository;
import com.deofis.tiendaapirest.productos.domain.*;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoaderProductos implements CommandLineRunner {

    private final UnidadMedidaRepository unidadMedidaRepository;
    private final MarcaRepository marcaRepository;
    private final MedioPagoRepository medioPagoRepository;

    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    @Override
    public void run(String... args) {

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
        List<Categoria> categorias = new ArrayList<>();

        if (this.categoriaRepository.findByNombre("Tecnología").isEmpty()) {
            Subcategoria celulares;

            // Propiedad modelo
            PropiedadProducto modelo;
            ValorPropiedadProducto s20;

            s20 = ValorPropiedadProducto.builder().valor("Galaxy S20").build();

            modelo = PropiedadProducto.builder().nombre("Modelo").valores(new ArrayList<>()).build();
            modelo.getValores().add(s20);

            // Propiedad color
            PropiedadProducto color;
            ValorPropiedadProducto negro = ValorPropiedadProducto.builder().valor("Negro").build();
            ValorPropiedadProducto gris = ValorPropiedadProducto.builder().valor("Gris").build();
            ValorPropiedadProducto dorado = ValorPropiedadProducto.builder().valor("Droado").build();

            color = PropiedadProducto.builder().nombre("Color").valores(new ArrayList<>()).build();
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
            categorias.add(tecnologia);
            // this.categoriaRepository.save(tecnologia);
        }

        if (this.categoriaRepository.findByNombre("Ropa y Moda").isEmpty()) {
            Subcategoria mujeres = Subcategoria.builder()
                    .nombre("Mujeres")
                    .codigo("WOM")
                    .propiedades(new ArrayList<>())
                    .build();
            Subcategoria hombres = Subcategoria.builder()
                    .nombre("Hombres")
                    .codigo("MAN")
                    .propiedades(new ArrayList<>())
                    .build();

            Categoria ropa = Categoria.builder()
                    .nombre("Ropa y Moda")
                    .subcategorias(new ArrayList<>())
                    .build();

            ropa.getSubcategorias().add(mujeres);
            ropa.getSubcategorias().add(hombres);
            categorias.add(ropa);
        }

        if (this.categoriaRepository.findByNombre("Termos").isEmpty()) {
            Subcategoria acero = Subcategoria.builder()
                    .nombre("Acero")
                    .codigo("ACE")
                    .build();

            Categoria termos = Categoria.builder()
                    .nombre("Termos")
                    .subcategorias(new ArrayList<>())
                    .build();

            termos.getSubcategorias().add(acero);
            categorias.add(termos);
        }

        if (this.categoriaRepository.findByNombre("Cuidado Personal").isEmpty()) {
            Subcategoria perfumes = Subcategoria.builder()
                    .nombre("Perfumes")
                    .codigo("PER")
                    .build();

            Subcategoria maquillaje = Subcategoria.builder()
                    .nombre("maquillaje")
                    .codigo("MAQ")
                    .build();

            Categoria cuidadoPersonal = Categoria.builder()
                    .nombre("Cuidado Personal")
                    .subcategorias(new ArrayList<>())
                    .build();

            cuidadoPersonal.getSubcategorias().add(perfumes);
            cuidadoPersonal.getSubcategorias().add(maquillaje);
            categorias.add(cuidadoPersonal);
        }

        if (this.categoriaRepository.findByNombre("Servicios y Software").isEmpty()) {
            Subcategoria SO = Subcategoria.builder()
                    .nombre("Sistemas Operativos")
                    .codigo("SO")
                    .build();

            Categoria servicios = Categoria.builder()
                    .nombre("Servicios y Software")
                    .subcategorias(new ArrayList<>())
                    .build();

            servicios.getSubcategorias().add(SO);
            categorias.add(servicios);
        }

        if (this.categoriaRepository.findByNombre("Componentes de PC").isEmpty()) {
            Subcategoria cpu = Subcategoria.builder()
                    .nombre("Microprocesadores")
                    .codigo("CPU")
                    .build();

            Subcategoria gpu = Subcategoria.builder()
                    .nombre("Tarjetas de video")
                    .codigo("GPU")
                    .build();

            Categoria componentesPC = Categoria.builder()
                    .nombre("Componentes de PC")
                    .subcategorias(new ArrayList<>())
                    .build();

            componentesPC.getSubcategorias().add(cpu);
            componentesPC.getSubcategorias().add(gpu);
            categorias.add(componentesPC);
        }

        if (this.categoriaRepository.findByNombre("Videojuegos y consolas").isEmpty()) {
            Subcategoria consolas = Subcategoria.builder()
                    .nombre("Consolas")
                    .codigo("CON")
                    .build();

            Subcategoria juegos = Subcategoria.builder()
                    .nombre("Juegos")
                    .codigo("GAMES")
                    .build();

            Subcategoria accesorios = Subcategoria.builder()
                    .nombre("Accesorios para consolas")
                    .codigo("ACC")
                    .build();

            Categoria videojuegos = Categoria.builder()
                    .nombre("Videojuegos y consolas")
                    .subcategorias(new ArrayList<>())
                    .build();

            videojuegos.getSubcategorias().add(consolas);
            videojuegos.getSubcategorias().add(juegos);
            videojuegos.getSubcategorias().add(accesorios);
            categorias.add(videojuegos);
        }

        if (this.categoriaRepository.findByNombre("Televisores").isEmpty()) {
            Subcategoria smart = Subcategoria.builder()
                    .nombre("SMART TV's")
                    .codigo("SMART")
                    .build();

            Categoria tvs = Categoria.builder()
                    .nombre("Televisores")
                    .subcategorias(new ArrayList<>())
                    .build();

            tvs.getSubcategorias().add(smart);
            categorias.add(tvs);
        }

        //Guardar categorias
        this.categoriaRepository.saveAll(categorias);

        // Carga productos
        if (this.productoRepository.findAll().size() == 0) {

            // Creamos propiedades y valores
            PropiedadProducto color = PropiedadProducto.builder()
                    .nombre("Color").variable(true).valores(new ArrayList<>()).build();
            ValorPropiedadProducto negro = ValorPropiedadProducto.builder()
                    .valor("Negro").build();
            ValorPropiedadProducto dorado = ValorPropiedadProducto.builder()
                    .valor("Dorado").build();
            color.getValores().add(negro);
            color.getValores().add(dorado);

            PropiedadProducto memoria = PropiedadProducto.builder()
                    .nombre("Memoria").variable(true).valores(new ArrayList<>()).build();
            ValorPropiedadProducto gb32 = ValorPropiedadProducto.builder()
                    .valor("32 GB").build();
            ValorPropiedadProducto gb64 = ValorPropiedadProducto.builder()
                    .valor("64 GB").build();
            memoria.getValores().add(gb32);
            memoria.getValores().add(gb64);

            //Creamos y cargamos los productos con propiedades y Sku por defecto.
            List<Producto> productos = new ArrayList<>();

            Producto samsungJ2 = Producto.builder()
                    .nombre("Samsung J2 Grand Prime")
                    .descripcion("Modelo: J2 GP 16GB de memoria, 1GB de RAM")
                    .precio(25000.00)
                    .precioOferta(23000.00)
                    .disponibilidad(107)
                    .activo(true)
                    .destacado(true)
                    .fechaCreacion(new Date())
                    .subcategoria(this.subcategoriaRepository.getOne(1L))
                    .marca(this.marcaRepository.getOne(1L))
                    .unidadMedida(this.unidadMedidaRepository.getOne(1L))
                    .propiedades(new ArrayList<>())
                    .skus(new ArrayList<>())
                    .build();
            samsungJ2.getPropiedades().add(color);
            samsungJ2.getPropiedades().add(memoria);
            samsungJ2.setDefaultSku(Sku.builder()
                    .nombre(samsungJ2.getNombre())
                    .descripcion(samsungJ2.getDescripcion())
                    .precio(samsungJ2.getPrecio())
                    .precioOferta(samsungJ2.getPrecioOferta())
                    .disponibilidad(samsungJ2.getDisponibilidad())
                    .defaultProducto(samsungJ2).build());

            productos.add(samsungJ2);

            Producto samsungS20 = Producto.builder()
                    .nombre("Samsung Galaxy S20")
                    .descripcion("Modelo: S20 32GB de memoria, 4GB de RAM")
                    .precio(120000.00)
                    .disponibilidad(25)
                    .activo(true)
                    .destacado(true)
                    .fechaCreacion(new Date())
                    .subcategoria(this.subcategoriaRepository.getOne(1L))
                    .marca(this.marcaRepository.getOne(1L))
                    .unidadMedida(this.unidadMedidaRepository.getOne(1L))
                    .propiedades(new ArrayList<>())
                    .skus(new ArrayList<>())
                    .build();
            samsungS20.getPropiedades().add(color);
            samsungS20.getPropiedades().add(memoria);
            samsungS20.setDefaultSku(Sku.builder()
                    .nombre(samsungS20.getNombre())
                    .descripcion(samsungS20.getDescripcion())
                    .precio(samsungS20.getPrecio())
                    .precioOferta(samsungS20.getPrecioOferta())
                    .disponibilidad(samsungS20.getDisponibilidad())
                    .defaultProducto(samsungS20).build());
            productos.add(samsungS20);

            Producto samsungS10 = Producto.builder()
                    .nombre("Samsung Galaxy S10")
                    .descripcion("Modelo: S10 32GB de memoria, 2GB de RAM")
                    .precio(90000.00)
                    .disponibilidad(20)
                    .activo(true)
                    .destacado(true)
                    .fechaCreacion(new Date())
                    .subcategoria(this.subcategoriaRepository.getOne(1L))
                    .marca(this.marcaRepository.getOne(1L))
                    .unidadMedida(this.unidadMedidaRepository.getOne(1L))
                    .propiedades(new ArrayList<>())
                    .skus(new ArrayList<>())
                    .build();
            samsungS10.getPropiedades().add(color);
            samsungS10.getPropiedades().add(memoria);
            samsungS10.setDefaultSku(Sku.builder()
                    .nombre(samsungS10.getNombre())
                    .descripcion(samsungS10.getDescripcion())
                    .precio(samsungS10.getPrecio())
                    .precioOferta(samsungS10.getPrecioOferta())
                    .disponibilidad(samsungS10.getDisponibilidad())
                    .defaultProducto(samsungS10).build());
            productos.add(samsungS10);

            Producto iphone11plus = Producto.builder()
                    .nombre("iPhone 11 Plus")
                    .descripcion("Modelo: 11 Plus 64GB de memoria, 4GB de RAM")
                    .precio(130000.00)
                    .disponibilidad(11)
                    .activo(true)
                    .destacado(true)
                    .fechaCreacion(new Date())
                    .subcategoria(this.subcategoriaRepository.getOne(1L))
                    .marca(this.marcaRepository.getOne(1L))
                    .unidadMedida(this.unidadMedidaRepository.getOne(1L))
                    .propiedades(new ArrayList<>())
                    .skus(new ArrayList<>())
                    .build();
            iphone11plus.getPropiedades().add(color);
            iphone11plus.getPropiedades().add(memoria);
            iphone11plus.setDefaultSku(Sku.builder()
                    .nombre(iphone11plus.getNombre())
                    .descripcion(iphone11plus.getDescripcion())
                    .precio(iphone11plus.getPrecio())
                    .precioOferta(iphone11plus.getPrecioOferta())
                    .disponibilidad(iphone11plus.getDisponibilidad())
                    .defaultProducto(iphone11plus).build());
            productos.add(iphone11plus);

            Producto iphone11 = Producto.builder()
                    .nombre("iPhone 11")
                    .descripcion("Modelo: 11 32GB de memoria, 2GB de RAM")
                    .precio(120000.00)
                    .disponibilidad(15)
                    .activo(true)
                    .destacado(true)
                    .fechaCreacion(new Date())
                    .subcategoria(this.subcategoriaRepository.getOne(1L))
                    .marca(this.marcaRepository.getOne(1L))
                    .unidadMedida(this.unidadMedidaRepository.getOne(1L))
                    .propiedades(new ArrayList<>())
                    .skus(new ArrayList<>())
                    .build();
            iphone11.getPropiedades().add(color);
            iphone11.getPropiedades().add(memoria);
            iphone11.setDefaultSku(Sku.builder()
                    .nombre(iphone11.getNombre())
                    .descripcion(iphone11.getDescripcion())
                    .precio(iphone11.getPrecio())
                    .precioOferta(iphone11.getPrecioOferta())
                    .disponibilidad(iphone11.getDisponibilidad())
                    .defaultProducto(iphone11).build());
            productos.add(iphone11);

            Producto samsungA10 = Producto.builder()
                    .nombre("Samsung Galaxy A10")
                    .descripcion("Modelo: Galaxy A10 32GB de memoria, 2GB de RAM")
                    .precio(40000.00)
                    .disponibilidad(95)
                    .activo(true)
                    .destacado(true)
                    .fechaCreacion(new Date())
                    .subcategoria(this.subcategoriaRepository.getOne(1L))
                    .marca(this.marcaRepository.getOne(1L))
                    .unidadMedida(this.unidadMedidaRepository.getOne(1L))
                    .propiedades(new ArrayList<>())
                    .skus(new ArrayList<>())
                    .build();
            samsungA10.getPropiedades().add(color);
            samsungA10.getPropiedades().add(memoria);
            samsungA10.setDefaultSku(Sku.builder()
                    .nombre(samsungA10.getNombre())
                    .descripcion(samsungA10.getDescripcion())
                    .precio(samsungA10.getPrecio())
                    .precioOferta(samsungA10.getPrecioOferta())
                    .disponibilidad(samsungA10.getDisponibilidad())
                    .defaultProducto(samsungA10).build());
            productos.add(samsungA10);

            this.productoRepository.saveAll(productos);
        }
    }
}
