package com.deofis.tiendaapirest.operaciones.services;

import net.sf.jasperreports.engine.JRException;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ReportService {

    byte[] generarReportPDF() throws FileNotFoundException, JRException;

    ByteArrayInputStream generarReportEXCEL() throws IOException, JRException;
}
