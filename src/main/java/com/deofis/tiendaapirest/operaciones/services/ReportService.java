package com.deofis.tiendaapirest.operaciones.services;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {

    byte[] generarReportPDF() throws FileNotFoundException, JRException;

    byte[] generarReportEXCEL() throws FileNotFoundException, JRException;
}
