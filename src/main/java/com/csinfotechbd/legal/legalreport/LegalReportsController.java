package com.csinfotechbd.legal.legalreport;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/legal-report")
public class LegalReportsController {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Value("C:/report/corpSanctionLetter")
    private String corpSanctionLetter;
    
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(value = "/branch-wise")
    public void branchwiseReprt(@RequestParam(value = "branch_code") String branch_code,
            HttpServletResponse response) {

        Connection conn = null;
        File file = new File(corpSanctionLetter);
        if(!file.exists()){
            file.mkdirs();
        }

        try {

            InputStream employeeReportStream = getClass().getResourceAsStream("/branchWiseLegalExpenses/branchWiseLegalExpenses.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);

            conn = jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("BRANCH_CODE", branch_code);
            parameters.put("CASE_TYPE_IDCASE_TYPE_ID", "12626");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

            OutputStream outputStream = response.getOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
           // Foreign Exchange Branch 12626
            SimplePdfReportConfiguration reportConfig
                    = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig
                    = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("abdullah shariar");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            exporter.exportReport();

            response.setContentType("application/pdf");
            exporter.exportReport();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public boolean tst(@RequestParam(value = "branch_code") String branch_code){
        System.err.println("enter  d: ");
        Connection conn = null;
        File file = new File(corpSanctionLetter);
        if(!file.exists()){
            file.mkdirs();
        }
        try {

            InputStream employeeReportStream
                    = getClass().getResourceAsStream("/branchwiseCourtCase/branchWiseCourtCases.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
            jasperReport.setWhenNoDataType(jasperReport.getWhenNoDataTypeValue());
            //InputStream employeeReportStream= getClass().getResourceAsStream("/branchwiseCourtCase/branchWiseCourtCases.jrxml");
            //JasperReport jasperReport = (JasperReport)JRLoader.loadObject(employeeReportStream);
           
            
            conn = jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("branch_id",branch_code);
            //parameters.put("branchName",bprPayload.getBranchName());

            BufferedImage image = ImageIO.read(getClass().getResource("/logo/ucbl_logo_x.png"));
            System.out.println("image url : "+image);
            System.out.println("parameters : "+parameters);

            parameters.put("logo",image);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, conn);


            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss-SSS");
            String strDate = formatter.format(new Date());

            String name="Branch Proposal "+strDate+".pdf";
            // System.err.println("name_pdf"+name);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(
                    new SimpleOutputStreamExporterOutput(corpSanctionLetter+"/"+name));

            SimplePdfReportConfiguration reportConfig
                    = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig
                    = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("CS Infotech");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            exporter.exportReport();
            
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    @GetMapping(value = "/test2")
    @ResponseBody
    public boolean tst2(){
        System.err.println("enter  d: ");
        Connection conn = null;
        File file = new File(corpSanctionLetter);
        if(!file.exists()){
            file.mkdirs();
        }
        try {

            InputStream employeeReportStream
                    = getClass().getResourceAsStream("/simpleReport/sample_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
            jasperReport.setWhenNoDataType(jasperReport.getWhenNoDataTypeValue());
            //InputStream employeeReportStream= getClass().getResourceAsStream("/branchwiseCourtCase/branchWiseCourtCases.jrxml");
            //JasperReport jasperReport = (JasperReport)JRLoader.loadObject(employeeReportStream);
            
            
            conn = jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parameters = new HashMap<>();

            //parameters.put("branch_id",branch_code);
            //parameters.put("branchName",bprPayload.getBranchName());

            BufferedImage image = ImageIO.read(getClass().getResource("/logo/ucbl_logo_x.png"));
            System.out.println("image url : "+image);
            System.out.println("parameters : "+parameters);
            
            //parameters.put("logo",image);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters,conn);
            
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss-SSS");
            String strDate = formatter.format(new Date());
            
            String name="Branch Proposal "+strDate+".pdf";
            // System.err.println("name_pdf"+name);
            
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(
                    new SimpleOutputStreamExporterOutput(corpSanctionLetter+"/"+name));
            
            SimplePdfReportConfiguration reportConfig
                    = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig
                    = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("CS Infotech");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            exporter.exportReport();
            
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    @GetMapping(value = "/test3")
    public boolean ts3(HttpServletResponse response){
        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
//            String report = "D:\\UCB_COLLECTION\\src\\main\\resources\\simpleReport\\sample_report.jrxml";
//            JasperReport jasperReport=JasperCompileManager.compileReport(report);
//            JasperPrint print=JasperFillManager.fillReport(jasperReport,null,conn);
//            JasperViewer.viewReport(print);
    
            String path = resourceLoader.getResource("D:\\\\UCB_COLLECTION\\\\src\\\\main\\\\resources\\\\simpleReport\\\\sample_report.jrxml").getURI().getPath();
    
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
    
            // Parameters for report
            Map<String, Object> parameters = new HashMap<String, Object>();
    
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
    
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"users.pdf\""));
    
            OutputStream out = response.getOutputStream();
            //jasperPrint = userService.exportPdfFile();
            JasperExportManager.exportReportToPdfStream(print, out);
    
    
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        
    }
}
