package com.csinfotechbd.reports.test;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestRController {
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    @Value("C:/report/corpSanctionLetter")
    private String corpSanctionLetter;
    
    @GetMapping(value = "/report2")
    public void testJasperReports()throws Exception{
        System.err.println("enter  d: ");
        Connection conn = null;
        File file = new File(corpSanctionLetter);
        if(!file.exists()){
            file.mkdirs();
        }
        conn = jdbcTemplate.getDataSource().getConnection();
        String jrxmlPath =
                "/simpleReport/sample_report.jrxml";
        String jasperPath =
                "/simpleReport/sample_report.jasper";
        
        //Compile the template
        JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);
        
        //Construct data
        Map paramters = new HashMap();
        paramters.put("reportDate","2019-10-10");
        paramters.put("company","itcast");
        List<Map> list = new ArrayList<>();
        Map map1 = new HashMap();
        map1.put("name","xiaoming");
        map1.put("address","beijing");
        map1.put("email","xiaoming@itcast.cn");
        Map map2 = new HashMap();
        map2.put("name","xiaoli");
        map2.put("address","nanjing");
        map2.put("email","xiaoli@itcast.cn");
        list.add(map1);
        list.add(map2);
        
        //Data input
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperPath,paramters,conn);
        
        //Output file
        String pdfPath = corpSanctionLetter+"error.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,pdfPath);
    }
}
