package com.csinfotechbd.utillity;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/*
 * Author: Hasibul Islam
 * Software Engineer. CS InfoTech Ltd.
 * From Jun-2021,
 * For more query don't hesitate to Contact.
 * */
@Component
public class JasperReportManager {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /* To Export Jasper Report to PDF .pdf File Format
     * Always pass .jasper file as Jasper Parameter. */
    public void exportToPdf(HttpServletResponse response, Map<String, Object> parameters, String reportTitle, String jasperFile) {
        Connection conn = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss-SSS");
            String strDate = formatter.format(new Date());
            String name = reportTitle + strDate + ".pdf";
            InputStream resourceFile = getClass().getResourceAsStream(jasperFile);

            conn = jdbcTemplate.getDataSource().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(resourceFile, parameters, conn);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + name + ";");

            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /* To Export Jasper Report to Excel .xls File Format.
     * Always pass .jasper file as Jasper Parameter. */
    public void exportToExcel(HttpServletResponse response, Map<String, Object> parameters, String reportTitle, String jasperFile) {
        Connection conn = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss-SSS");
            String strDate = formatter.format(new Date());
            String name = reportTitle + strDate + ".xlsx";
            InputStream resourceFile = getClass().getResourceAsStream(jasperFile);

            conn = jdbcTemplate.getDataSource().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(resourceFile, parameters, conn);

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

            SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
            reportConfig.setFreezeRow(1);
            reportConfig.setSheetNames(new String[]{"sheet1"});

            SimpleXlsxExporterConfiguration exportConfig = new SimpleXlsxExporterConfiguration();
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + name);
            exporter.exportReport();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
