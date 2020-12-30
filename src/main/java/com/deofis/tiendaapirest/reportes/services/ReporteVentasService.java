package com.deofis.tiendaapirest.reportes.services;

import java.util.Date;
import java.util.Map;

public interface ReporteVentasService {

    Map<String, Object> generarReporteVentasSku(Long skuId);

    Map<String, Object> generarReporteVentasSku(Long skuId, Date fechaDesde, Date fechaHasta);
}
