package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.Sku;
import com.deofis.tiendaapirest.productos.exceptions.SkuException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class SkuServiceImpl implements SkuService {

    private final ProductoRepository productoRepository;
    private final GeneradorSkus generadorSkus;

    @Override
    public Sku crearNuevoSku(Sku sku, Producto producto) {
        return null;
    }

    @Override
    public Map<String, Object> generarSkusProducto(Producto producto) {
        Map<String, Object> map = new HashMap<>();
        int cantCombinacionesGeneradas = this.generadorSkus.generarSkusProducto(producto);

        if (cantCombinacionesGeneradas == -1) {
            throw new SkuException("No se generaron combinaciones: El producto seleccionado" +
                    " no posee propiedades.");
        }

        if (cantCombinacionesGeneradas == -2) {
            throw new SkuException("No se generaron combinaciones: Las propiedades de el producto " +
                    "seleccionado no poseen valores asociados");
        }

        map.put("combinaciones", cantCombinacionesGeneradas);
        map.put("skus", producto.getSkus());
        return map;
    }

    @Override
    public Sku actualizarSku(Long skuId, Sku sku) {
        return null;
    }

    @Override
    public Sku actualizarDisponibilidad(Long skuId, Integer disponibilidad) {
        return null;
    }

    @Override
    public Sku actualizarPrecio(Long skuId, Double precio) {
        return null;
    }

    @Override
    public void eliminarSku(Long skuId) {

    }
}
