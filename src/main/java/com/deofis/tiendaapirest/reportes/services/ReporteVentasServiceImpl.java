package com.deofis.tiendaapirest.reportes.services;

import com.deofis.tiendaapirest.operaciones.domain.DetalleOperacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.repositories.OperacionRepository;
import com.deofis.tiendaapirest.productos.domain.Sku;
import com.deofis.tiendaapirest.productos.services.SkuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReporteVentasServiceImpl implements ReporteVentasService {

    private final OperacionRepository operacionRepository;
    private final SkuService skuService;

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> generarReporteVentasSku(Long skuId) {
        Map<String, Object> reportData = new HashMap<>();

        Sku sku = this.skuService.obtenerSku(skuId);
        Integer vendidosTotal = 0;
        Double montoTotal = 0.00;
        List<Operacion> ventas = this.operacionRepository.findAll();

        for (Operacion venta: ventas) {
            List<DetalleOperacion> items = venta.getItems();

            for (DetalleOperacion item: items) {
                if (item.getSku().equals(sku)) {
                    vendidosTotal += item.getCantidad();
                    montoTotal += item.getSubtotal();
                }
            }
        }

        reportData.put("sku", sku);
        reportData.put("vendidosTotal", vendidosTotal);
        reportData.put("montoTotal", montoTotal);

        return reportData;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> generarReporteVentasSku(Long skuId, Date fechaDesde, Date fechaHasta) {
        Map<String, Object> reportData = new HashMap<>();

        Sku sku = this.skuService.obtenerSku(skuId);

        List<Operacion> ventas = this.operacionRepository.findAll();
        return null;
    }
}
