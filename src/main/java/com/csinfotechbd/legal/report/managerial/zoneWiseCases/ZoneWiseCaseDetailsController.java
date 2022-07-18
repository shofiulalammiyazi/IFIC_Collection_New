package com.csinfotechbd.legal.report.managerial.zoneWiseCases;

import com.csinfotechbd.collection.settings.district.DistrictService;
import com.csinfotechbd.utillity.JasperReportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/legal/report/managerial/zone-wise-cases")
public class ZoneWiseCaseDetailsController {

    @Autowired
    private DistrictService districtService;

    @Autowired
    private ZoneWiseCaseDetailsService service;
    
    @Autowired
    private JasperReportManager jasperReportManager;
    
    @Value("")
    private String jasperFile;
    
    @Value("Zone Wise Case Details")
    private String reportTitle;

    @GetMapping(value = "/view")
    public String view(Model model) {
        model.addAttribute("zoneList", service.getZonesContainingCases());
        model.addAttribute("districtList", districtService.getAll());
        model.addAttribute("reportTitle", "Zone wise Case details");
        return "legal/report/managerial/zoneWiseCases/view";
    }

    @GetMapping(value = "/report")
    @ResponseBody
    public List<ZoneWiseCaseDetailsDto> getZoneWiseCases(String zoneName, String[] districts) {
        return service.getReport(zoneName, Arrays.asList(districts));
    }
    
    @GetMapping(value = "/report/excel")
    public void generateExcel(HttpServletResponse response, @RequestParam(value = "zoneName") String zoneName,
                              @RequestParam(value = "districts") List<String> districts){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("zoneName",zoneName);
        parameters.put("districts",districts);
        jasperReportManager.exportToExcel(response,parameters,reportTitle,jasperFile);
    }
}
