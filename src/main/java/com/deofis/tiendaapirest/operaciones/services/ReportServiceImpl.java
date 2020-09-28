package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.dto.OperacionDTO;
import com.deofis.tiendaapirest.operaciones.repositories.OperacionRepository;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OperacionRepository operacionRepository;

    @Override
    public byte[] generarReportPDF() throws FileNotFoundException, JRException {
        byte[] bytes;

        List<OperacionDTO> operaciones = this.operacionRepository.findAllByOrderByFechaOperacionDesc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        File archivo = ResourceUtils.getFile("classpath:operaciones.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(archivo.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(operaciones);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "DEOFIS");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        bytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return bytes;
    }

    @Override
    public byte[] generarReportEXCEL() throws FileNotFoundException, JRException {
        byte[] bytes = null;

        List<OperacionDTO> operaciones = this.operacionRepository.findAllByOrderByFechaOperacionDesc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        File archivo = ResourceUtils.getFile("classpath:operaciones.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(archivo.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(operaciones);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "DEOFIS");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        var input = new SimpleExporterInput(jasperPrint);

        try (var byteArray = new ByteArrayOutputStream()){
            var output = new SimpleOutputStreamExporterOutput(byteArray);
            var exporter = new JRXlsxExporter();
            exporter.setExporterInput(input);
            exporter.setExporterOutput(output);
            exporter.exportReport();

            bytes = byteArray.toByteArray();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    private OperacionDTO mapToDto(Operacion operacion) {
        return OperacionDTO.builder()
                .nroOperacion(operacion.getNroOperacion())
                .estado(operacion.getEstado().toString())
                .fechaOperacion(operacion.getFechaOperacion())
                .fechaEnviada(operacion.getFechaEnviada())
                .fechaRecibida(operacion.getFechaRecibida())
                .cliente(operacion.getCliente().getApellido() + ", " + operacion.getCliente().getNombre())
                .formaPago(operacion.getFormaPago().getNombre())
                .total(operacion.getTotal())
                .build();
    }
}
