package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.dto.ProductoDTO;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;
    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final MarcaRepository marcaRepository;

    private final ProductoImportadorService productoImportadorService;

    @Override
    @Transactional
    public Producto crear(Producto producto) {
        Producto nuevoProducto = Producto.builder()
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .fechaCreacion(new Date())
                .foto(null)
                .activo(true)
                .destacado(true)
                .subcategoria(producto.getSubcategoria())
                .marca(producto.getMarca())
                .disponibilidad(producto.getDisponibilidad())
                .unidadMedida(producto.getUnidadMedida())
                .build();

        return this.productoRepository.save(nuevoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerProductos() {
        return this.productoRepository.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerProducto(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoException("Producto no existente con ID: " + id));
    }

    @Override
    @Transactional
    public Producto actualizar(Producto producto, Long id) {

        Producto productoActual = this.obtenerProducto(id);

        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setPrecio(producto.getPrecio());
        productoActual.setSubcategoria(producto.getSubcategoria());
        productoActual.setMarca(producto.getMarca());
        productoActual.setDisponibilidad(producto.getDisponibilidad());
        productoActual.setUnidadMedida(producto.getUnidadMedida());

        return this.productoRepository.save(productoActual);
    }

    @Override
    @Transactional
    public void darDeBaja(Producto producto, Long id) {
        Producto productoActual = this.obtenerProducto(id);

        if (!productoActual.isActivo()) {
            throw new ProductoException("Este producto ya está dado de baja");
        }

        productoActual.setActivo(false);
        this.productoRepository.save(productoActual);
    }

    @Override
    @Transactional
    public void darDeAlta(Producto producto, Long id) {
        Producto productoActual = this.obtenerProducto(id);

        if (productoActual.isActivo()) {
            throw new ProductoException("Este producto ya está activo");
        }

        productoActual.setActivo(true);
        this.productoRepository.save(productoActual);
    }

    @Override
    @Transactional
    public void destacar(Producto producto, Long id) {
        Producto productoActual = this.obtenerProducto(id);

        productoActual.setDestacado(!producto.isDestacado());
        this.productoRepository.save(productoActual);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UnidadMedida> obtenerUnidadesMedida() {
        return this.unidadMedidaRepository.findAll();
    }

    @Transactional
    @Override
    public List<Producto> importarDeCSV(MultipartFile archivo) {
        List<ProductoDTO> dtos = this.productoImportadorService.recibirCsv(archivo);
        System.out.println(dtos);
        List<Producto> productos = new ArrayList<>();

        for (ProductoDTO productoDTO : dtos) {
            productos.add(this.mapProductoDto(productoDTO));
        }

        return this.productoRepository.saveAll(productos);
    }

    @Transactional
    @Override
    public List<Producto> importarDeExcel(MultipartFile archivo) {
        List<ProductoDTO> dtos = this.productoImportadorService.recibirExcel(archivo);
        System.out.println(dtos);
        List<Producto> productos = new ArrayList<>();

        for (ProductoDTO productoDTO: dtos) {
            productos.add(this.mapProductoDto(productoDTO));
        }

        return this.productoRepository.saveAll(productos);
    }

    @Transactional
    @Override
    public List<Producto> actualizarStockDeExcel(MultipartFile archivo) {
        List<ProductoDTO> dtos = this.productoImportadorService.recibirExcelActualizarStock(archivo);
        System.out.println(dtos);
        List<Producto> productosActualizados = new ArrayList<>();

        for (ProductoDTO productoDTO: dtos) {
            Producto productoBD = this.productoRepository.getOne(productoDTO.getId());

            productoBD.setDisponibilidad(productoDTO.getDisponibilidad());
            Producto productoActuzliado = this.productoRepository.save(productoBD);
            productosActualizados.add(productoActuzliado);
        }

        return productosActualizados;
    }

    @Transactional(readOnly = true)
    public Producto mapProductoDto(ProductoDTO productoDTO) {
        Subcategoria subcategoria = this.subcategoriaRepository.findById(productoDTO.getSubcategoriaId())
                .orElseThrow(() -> new ProductoException("No se encontro la categoría con id: " +
                        productoDTO.getSubcategoriaId()));

        Marca marca = this.marcaRepository.findById(productoDTO.getMarcaId())
                .orElseThrow(() -> new ProductoException("No se encontro la marca con id: " +
                        productoDTO.getMarcaId()));

        UnidadMedida unidadMedida = this.unidadMedidaRepository.findById(productoDTO.getUnidadMedidaId())
                .orElseThrow(() -> new ProductoException("No se encontro la unidad de medida con id: " +
                        productoDTO.getUnidadMedidaId()));

        return Producto.builder()
                .nombre(productoDTO.getNombre())
                .descripcion(productoDTO.getDescripcion())
                .precio(productoDTO.getPrecio())
                .disponibilidad(productoDTO.getDisponibilidad())
                .subcategoria(subcategoria)
                .marca(marca)
                .unidadMedida(unidadMedida)
                .activo(true)
                .fechaCreacion(new Date())
                .destacado(true)
                .foto(null)
                .build();
    }
}
