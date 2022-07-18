package com.csinfotechbd.collection.settings.agencyPerformance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection/agencyPerformance")
public class AgencyPerformanceController {

    @GetMapping(value = "/view")
    public String view(Model model){
        model.addAttribute("agencyPerformance", model);
        return "collection/kpi/agency/loan/agencyPerformance/view";
    }

}
