package com.csinfotechbd.legal.report.datasheets.appellateDivisionReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/legal/report/appellate-division-report")
public class AppellateDivisionController {

    @Autowired
    private AppellateDivisionService appellateDivisionService;


        @GetMapping("/view")
        public String view(Model model){
            model.addAttribute("reportTitle","Case list of  Appellate Division as on");
            List<AppellateDivision> appellateDivisionList = appellateDivisionService.getCaseList();
            model.addAttribute("appellateDivisionList",appellateDivisionList);
            return "legal/report/appellateDivision/view";
        }
}
