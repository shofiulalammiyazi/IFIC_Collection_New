package com.csinfotechbd.legal.report.managerial.lawyerWiseCaseDetails;


import com.csinfotechbd.legal.setup.lawyers.LawyerDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/legal/report/managerial/lawyer-wise-case-details")
public class LawyerWiseCaseDetailsController {

    @Autowired
    private LawyerWiseCaseDetailsService service;
    @Autowired
    private JasperReportManager jasperReportManager;
//    @Autowired
//    private LawyerService lawyerService;

    @Value("")
    private String jasperFile;
    
    @Value("Lawyer wise Case details")
    private String reportTitle;

    @GetMapping("/view")
    public String viewlawyer(Model model) {
//        List<Lawyer> lawyers = lawyerService.findAll();
        List<LawyerDto> lawyers = service.getAssignedLawyerList();
        model.addAttribute("reportTitle", "Lawyer wise Case details");
        model.addAttribute("lawyerList",lawyers);
        return "legal/report/managerial/lawyerWiseCaseDetails/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<LawyerWiseCaseDetailsControllerDto> getReport(Long lawyerId) {
        return service.getReport(lawyerId);
    }

    @GetMapping(value = "/report/excel")
    public void generateExcel(HttpServletResponse response, @RequestParam(value = "lawyerId") Long lawyerId){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("lawyerId",lawyerId);
        jasperReportManager.exportToExcel(response,parameters,reportTitle,jasperFile);
    }
}
