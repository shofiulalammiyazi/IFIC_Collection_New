package com.unisoft.reports.test;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ReportController {
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    @Value("C:/report/corpSanctionLetter")
    private String corpSanctionLetter;
    
    @GetMapping(value = "/report")
    public boolean report(){
        System.err.println("enter  d: ");
        Connection conn = null;
        File file = new File(corpSanctionLetter);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
        
            InputStream employeeReportStream
                    = getClass().getResourceAsStream("/simpleReport/sample_report.jrxml");
            JasperReport jasperReport
                    = JasperCompileManager.compileReport(employeeReportStream);
            jasperReport.setWhenNoDataType(jasperReport.getWhenNoDataTypeValue());
        
            conn = jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parameters = new HashMap<>();
        
//            parameters.put("corporateId",bprPayload.getLoanId());
//            parameters.put("branchName",bprPayload.getBranchName());
        
            
            System.out.println("parameters : "+parameters);
        
            //parameters.put("logo",image);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, conn);
        
        
//            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss-SSS");
//            String strDate = formatter.format(new Date());
//
//            String name="Branch Proposal "+strDate+".pdf";
//            // System.err.println("name_pdf"+name);
//
//            JRPdfExporter exporter = new JRPdfExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(
//                    new SimpleOutputStreamExporterOutput(corpSanctionLetter+"/"+name));
//
//            SimplePdfReportConfiguration reportConfig
//                    = new SimplePdfReportConfiguration();
//            reportConfig.setSizePageToContent(true);
//            reportConfig.setForceLineBreakPolicy(false);
//
//            SimplePdfExporterConfiguration exportConfig
//                    = new SimplePdfExporterConfiguration();
//            exportConfig.setMetadataAuthor("CS Infotech");
//
//            exportConfig.setEncrypted(true);
//            exportConfig.setAllowedPermissionsHint("PRINTING");
//
//            exporter.setConfiguration(reportConfig);
//            exporter.setConfiguration(exportConfig);
//            exporter.exportReport();
//
//            //save into dms
//
//            File upFile=new File(corpSanctionLetter+"/"+name);
//            //MultipartFile multipartFile = new MockMultipartFile("test.xlsx", new FileInputStream(new File("/home/admin/test.xlsx")));
//            FileInputStream inputStream=new FileInputStream(upFile);
//            MultipartFile multipartFile=new MockMultipartFile("file",""+name,"application/pdf",inputStream);
//            //String fileid = dmsFileSaver.saveFiletoDms(multipartFile,lid.toString(),"applicationform");
//
//            //upFile.delete();
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
}
