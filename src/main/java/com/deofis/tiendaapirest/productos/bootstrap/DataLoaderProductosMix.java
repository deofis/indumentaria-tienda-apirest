package com.deofis.tiendaapirest.productos.bootstrap;

import com.deofis.tiendaapirest.pagos.domain.MedioPago;
import com.deofis.tiendaapirest.pagos.domain.MedioPagoEnum;
import com.deofis.tiendaapirest.pagos.repositories.MedioPagoRepository;
import com.deofis.tiendaapirest.productos.domain.*;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Profile("prod")
public class DataLoaderProductosMix implements CommandLineRunner {

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

        if (this.marcaRepository.findAll().size() == 0) {
            List<Marca> marcas = new ArrayList<>();

            Marca samsung = Marca.builder()
                    .nombre("Samsung")
                    .build();
            marcas.add(samsung);

            Marca apple = Marca.builder()
                    .nombre("Apple")
                    .build();
            marcas.add(apple);

            Marca stanley = Marca.builder()
                    .nombre("Stanley")
                    .build();
            marcas.add(stanley);

            this.marcaRepository.saveAll(marcas);
        }

        if (this.medioPagoRepository.findByNombre(MedioPagoEnum.EFECTIVO).isEmpty()) {
            MedioPago efectivo = new MedioPago();
            efectivo.setNombre(MedioPagoEnum.EFECTIVO);

            this.medioPagoRepository.save(efectivo);
        }

        if (this.medioPagoRepository.findByNombre(MedioPagoEnum.PAYPAL).isEmpty()) {
            MedioPago paypal = new MedioPago();
            paypal.setNombre(MedioPagoEnum.PAYPAL);

            this.medioPagoRepository.save(paypal);
        }

        // CREACIÓN DE PROPIEDADES Y VALORES (PREVIO A CARGA SUB Y PROD)
        // CELULARES
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

        PropiedadProducto ram = PropiedadProducto.builder()
                .nombre("RAM").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto gb1 = ValorPropiedadProducto.builder().valor("1 GB").build();
        ValorPropiedadProducto gb2 = ValorPropiedadProducto.builder().valor("2 GB").build();
        ValorPropiedadProducto gb3 = ValorPropiedadProducto.builder().valor("3 GB").build();
        ValorPropiedadProducto gb4 = ValorPropiedadProducto.builder().valor("4 GB").build();
        ram.getValores().add(gb1);
        ram.getValores().add(gb2);
        ram.getValores().add(gb3);
        ram.getValores().add(gb4);


        //TARJETAS DE VIDEO
        PropiedadProducto vram = PropiedadProducto.builder()
                .nombre("VRAM").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto gb1vram = ValorPropiedadProducto.builder().valor("1 GB").build();
        ValorPropiedadProducto gb2vram = ValorPropiedadProducto.builder().valor("2 GB").build();
        ValorPropiedadProducto gb3vram = ValorPropiedadProducto.builder().valor("3 GB").build();
        ValorPropiedadProducto gb4vram = ValorPropiedadProducto.builder().valor("4 GB").build();
        ValorPropiedadProducto gb8vram = ValorPropiedadProducto.builder().valor("8 GB").build();
        vram.getValores().add(gb1vram);
        vram.getValores().add(gb2vram);
        vram.getValores().add(gb3vram);
        vram.getValores().add(gb4vram);
        vram.getValores().add(gb8vram);

        PropiedadProducto gpuEdicion = PropiedadProducto.builder()
                .nombre("Edición").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto gpuEstandar = ValorPropiedadProducto.builder().valor("Standar").build();
        ValorPropiedadProducto gpuOC = ValorPropiedadProducto.builder().valor("OC Edition").build();
        gpuEdicion.getValores().add(gpuEstandar);
        gpuEdicion.getValores().add(gpuOC);


        // MICROPROCESADORES
        PropiedadProducto cpuEdicion = PropiedadProducto.builder()
                .nombre("Edición").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto cpuEstandar = ValorPropiedadProducto.builder().valor("Standar").build();
        ValorPropiedadProducto cpuOC = ValorPropiedadProducto.builder().valor("OC Edition").build();
        cpuEdicion.getValores().add(cpuEstandar);
        cpuEdicion.getValores().add(cpuOC);


        // PERFUMES
        PropiedadProducto volumenPerfume = PropiedadProducto.builder()
                .nombre("Volúmen").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto mL100 = ValorPropiedadProducto.builder()
                .valor("100 mL").build();
        ValorPropiedadProducto mL200 = ValorPropiedadProducto.builder()
                .valor("200 mL").build();
        volumenPerfume.getValores().add(mL100);
        volumenPerfume.getValores().add(mL200);


        // MAQUILLAJE
        // Skus de maquillaje? Oyes lo loco que suena eso?


        // ROPA
        List<PropiedadProducto> propiedadesRemera = new ArrayList<>();
        PropiedadProducto colorRemera = PropiedadProducto.builder()
                .nombre("Color de remera").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto remeraNegra = ValorPropiedadProducto.builder().valor("Negra").build();
        ValorPropiedadProducto remeraRoja = ValorPropiedadProducto.builder().valor("Roja").build();
        ValorPropiedadProducto remeraVerde = ValorPropiedadProducto.builder().valor("Verde").build();
        ValorPropiedadProducto remeraGris = ValorPropiedadProducto.builder().valor("Gris").build();
        colorRemera.getValores().add(remeraNegra);
        colorRemera.getValores().add(remeraRoja);
        colorRemera.getValores().add(remeraVerde);
        colorRemera.getValores().add(remeraGris);
        propiedadesRemera.add(colorRemera);

        PropiedadProducto tipoRemera = PropiedadProducto.builder()
                .nombre("Tipo").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto cuelloV = ValorPropiedadProducto.builder().valor("Cuello en V").build();
        ValorPropiedadProducto cuelloRedondo = ValorPropiedadProducto.builder().valor("Cuello redondo").build();
        tipoRemera.getValores().add(cuelloV);
        tipoRemera.getValores().add(cuelloRedondo);
        propiedadesRemera.add(tipoRemera);

        PropiedadProducto disenoTelaRemera = PropiedadProducto.builder()
                .nombre("Diseño de tela").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto remLiso = ValorPropiedadProducto.builder().valor("Liso").build();
        ValorPropiedadProducto remEstampado = ValorPropiedadProducto.builder().valor("Estampado").build();
        disenoTelaRemera.getValores().add(remLiso);
        disenoTelaRemera.getValores().add(remEstampado);
        propiedadesRemera.add(disenoTelaRemera);

        PropiedadProducto tipoMangaRemera = PropiedadProducto.builder()
                .nombre("Tipo de manga").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto remCorta = ValorPropiedadProducto.builder().valor("Manga corta").build();
        ValorPropiedadProducto remLarga = ValorPropiedadProducto.builder().valor("Manga larga").build();
        tipoMangaRemera.getValores().add(remCorta);
        tipoMangaRemera.getValores().add(remLarga);
        propiedadesRemera.add(tipoMangaRemera);


        // TERMOS
        PropiedadProducto colorTermo = PropiedadProducto.builder()
                .nombre("Color de termo").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto termoVerde = ValorPropiedadProducto.builder().valor("Verde").build();
        ValorPropiedadProducto termoGris = ValorPropiedadProducto.builder().valor("Gris").build();
        colorTermo.getValores().add(termoVerde);
        colorTermo.getValores().add(termoGris);


        // SISTEMAS OPERATIVOS
        PropiedadProducto arquitecturaSO = PropiedadProducto.builder()
                .nombre("Arquitectura").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto bits32 = ValorPropiedadProducto.builder().valor("32 bits").build();
        ValorPropiedadProducto bits64 = ValorPropiedadProducto.builder().valor("64 bits").build();
        arquitecturaSO.getValores().add(bits32);
        arquitecturaSO.getValores().add(bits64);

        // TELEVISORES SMART
        PropiedadProducto pulgadas = PropiedadProducto.builder()
                .nombre("Pulgadas").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto pulg32 = ValorPropiedadProducto.builder().valor("32\"").build();
        ValorPropiedadProducto pulg42 = ValorPropiedadProducto.builder().valor("42\"").build();
        ValorPropiedadProducto pulg50 = ValorPropiedadProducto.builder().valor("50\"").build();
        pulgadas.getValores().add(pulg32);
        pulgadas.getValores().add(pulg42);
        pulgadas.getValores().add(pulg50);

        PropiedadProducto resolucion = PropiedadProducto.builder()
                .nombre("Resolución").variable(true).valores(new ArrayList<>()).build();
        ValorPropiedadProducto res1080 = ValorPropiedadProducto.builder().valor("1080P").build();
        ValorPropiedadProducto res4K = ValorPropiedadProducto.builder().valor("4K").build();
        ValorPropiedadProducto res8K = ValorPropiedadProducto.builder().valor("8K").build();
        resolucion.getValores().add(res1080);
        resolucion.getValores().add(res4K);
        resolucion.getValores().add(res8K);

        // CARGA DE CATEGORIAS Y SUBCATEGORIAS
        List<Categoria> categorias = new ArrayList<>();

        if (this.categoriaRepository.findByNombre("Tecnología").isEmpty()) {
            Subcategoria celulares;

            celulares = Subcategoria.builder().nombre("Celulares").propiedades(new ArrayList<>()).codigo("CEL").build();
            celulares.getPropiedades().add(memoria);
            celulares.getPropiedades().add(color);
            celulares.getPropiedades().add(ram);

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
            mujeres.getPropiedades().addAll(propiedadesRemera);

            Subcategoria hombres = Subcategoria.builder()
                    .nombre("Hombres")
                    .codigo("MAN")
                    .propiedades(new ArrayList<>())
                    .build();
            hombres.getPropiedades().addAll(propiedadesRemera);

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
                    .propiedades(new ArrayList<>())
                    .build();
            acero.getPropiedades().add(colorTermo);

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
                    .propiedades(new ArrayList<>())
                    .build();
            perfumes.getPropiedades().add(volumenPerfume);

            Subcategoria maquillaje = Subcategoria.builder()
                    .nombre("maquillaje")
                    .codigo("MAQ")
                    .propiedades(new ArrayList<>())
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
                    .propiedades(new ArrayList<>())
                    .build();
            SO.getPropiedades().add(arquitecturaSO);

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
                    .propiedades(new ArrayList<>())
                    .build();
            cpu.getPropiedades().add(cpuEdicion);

            Subcategoria gpu = Subcategoria.builder()
                    .nombre("Tarjetas de video")
                    .codigo("GPU")
                    .propiedades(new ArrayList<>())
                    .build();
            gpu.getPropiedades().add(vram);
            gpu.getPropiedades().add(gpuEdicion);

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
                    .propiedades(new ArrayList<>())
                    .build();
            smart.getPropiedades().add(pulgadas);
            smart.getPropiedades().add(resolucion);

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
            //Creamos y cargamos los productos con propiedades y Sku por defecto.
            List<Producto> productos = new ArrayList<>();

            Producto samsungJ2 = Producto.builder()
                    .nombre("Samsung J2 Grand Prime")
                    .descripcion("Modelo: J2 GP 16GB de memoria, 1GB de RAM")
                    .precio(25000.00)
                    .disponibilidadGeneral(107)
                    .foto(null)
                    .imagenes(new ArrayList<>())
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
                    .fechaCreacion(new Date())
                    .precio(samsungJ2.getPrecio())
                    .disponibilidad(samsungJ2.getDisponibilidadGeneral())
                    .defaultProducto(samsungJ2).build());

            productos.add(samsungJ2);

            Producto samsungS20 = Producto.builder()
                    .nombre("Samsung Galaxy S20")
                    .descripcion("Modelo: S20 32GB de memoria, 4GB de RAM")
                    .precio(120000.00)
                    .disponibilidadGeneral(25)
                    .foto(null)
                    .imagenes(new ArrayList<>())
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
                    .fechaCreacion(new Date())
                    .disponibilidad(samsungS20.getDisponibilidadGeneral())
                    .defaultProducto(samsungS20).build());
            productos.add(samsungS20);

            Producto samsungS10 = Producto.builder()
                    .nombre("Samsung Galaxy S10")
                    .descripcion("Modelo: S10 32GB de memoria, 2GB de RAM")
                    .precio(90000.00)
                    .disponibilidadGeneral(20)
                    .foto(null)
                    .imagenes(new ArrayList<>())
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
                    .fechaCreacion(new Date())
                    .disponibilidad(samsungS10.getDisponibilidadGeneral())
                    .defaultProducto(samsungS10).build());
            productos.add(samsungS10);

            Producto iphone11plus = Producto.builder()
                    .nombre("iPhone 11 Plus")
                    .descripcion("Modelo: 11 Plus 64GB de memoria, 4GB de RAM")
                    .precio(130000.00)
                    .disponibilidadGeneral(11)
                    .foto(null)
                    .imagenes(new ArrayList<>())
                    .activo(true)
                    .destacado(true)
                    .fechaCreacion(new Date())
                    .subcategoria(this.subcategoriaRepository.getOne(1L))
                    .marca(this.marcaRepository.getOne(2L))
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
                    .fechaCreacion(new Date())
                    .disponibilidad(iphone11plus.getDisponibilidadGeneral())
                    .defaultProducto(iphone11plus).build());
            productos.add(iphone11plus);

            Producto iphone11 = Producto.builder()
                    .nombre("iPhone 11")
                    .descripcion("Modelo: 11 32GB de memoria, 2GB de RAM")
                    .precio(120000.00)
                    .disponibilidadGeneral(15)
                    .foto(null)
                    .imagenes(new ArrayList<>())
                    .activo(true)
                    .destacado(true)
                    .fechaCreacion(new Date())
                    .subcategoria(this.subcategoriaRepository.getOne(1L))
                    .marca(this.marcaRepository.getOne(2L))
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
                    .fechaCreacion(new Date())
                    .disponibilidad(iphone11.getDisponibilidadGeneral())
                    .defaultProducto(iphone11).build());
            productos.add(iphone11);

            Producto samsungA10 = Producto.builder()
                    .nombre("Samsung Galaxy A10")
                    .descripcion("Modelo: Galaxy A10 32GB de memoria, 2GB de RAM")
                    .precio(40000.00)
                    .disponibilidadGeneral(95)
                    .foto(null)
                    .imagenes(new ArrayList<>())
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
                    .fechaCreacion(new Date())
                    .disponibilidad(samsungA10.getDisponibilidadGeneral())
                    .defaultProducto(samsungA10).build());
            productos.add(samsungA10);

            this.productoRepository.saveAll(productos);
        }
    }
}
