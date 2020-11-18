package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.dto.ProductoDTO;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.MarcaRepository;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import com.deofis.tiendaapirest.productos.repositories.SubcategoriaRepository;
import com.deofis.tiendaapirest.productos.repositories.UnidadMedidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ImportadorProductosServiceImpl implements ImportadorProductosService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;
    private final ProductoArchivosHandler productoArchivosHandler;

    @Transactional
    @Override
    public List<Producto> importarDeCSV(MultipartFile archivo) {
        List<ProductoDTO> dtos = this.productoArchivosHandler.recibirCsv(archivo);
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
        List<ProductoDTO> dtos = this.productoArchivosHandler.recibirExcel(archivo);
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
        List<ProductoDTO> dtos = this.productoArchivosHandler.recibirExcelActualizarStock(archivo);
        System.out.println(dtos);
        List<Producto> productosActualizados = new ArrayList<>();

        for (ProductoDTO productoDTO: dtos) {
            Producto productoBD = this.productoRepository.getOne(productoDTO.getId());

            productoBD.setDisponibilidadGeneral(productoDTO.getDisponibilidad());
            Producto productoActuzliado = this.productoRepository.save(productoBD);
            productosActualizados.add(productoActuzliado);
        }

        return productosActualizados;
    }

    private Producto mapProductoDto(ProductoDTO productoDTO) {
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
                .disponibilidadGeneral(productoDTO.getDisponibilidad())
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
