package com.deofis.tiendaapirest.operaciones.controllers;

import com.deofis.tiendaapirest.operaciones.services.ReportService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OperacionesReportsController {

    private final ReportService reportService;

    @GetMapping("/operaciones/reportes/operaciones.pdf")
    public ResponseEntity<?> generarReportePDF() {
        Map<String, Object> response = new HashMap<>();
        byte[] bytes;

        try {
            bytes = this.reportService.generarReportPDF();
        } catch (FileNotFoundException | JRException e) {
            response.put("mensaje", "Error al generar el reporte en PDF");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + "operaciones.pdf\"")
                .body(bytes);
    }

    @GetMapping("/operaciones/reportes/operaciones.excel")
    public ResponseEntity<?> generarReporteEXCEL() {
        Map<String, Object> response = new HashMap<>();
        ByteArrayInputStream bytes;

        try {
            bytes = this.reportService.generarReportEXCEL();
        } catch (IOException | JRException e) {
            response.put("mensaje", "Error al generar el reporte en EXCEL");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8")
                .header("Content-Disposition", "attachment;filename=operaciones.xls")
                .body(new InputStreamResource(bytes));
    }

}
