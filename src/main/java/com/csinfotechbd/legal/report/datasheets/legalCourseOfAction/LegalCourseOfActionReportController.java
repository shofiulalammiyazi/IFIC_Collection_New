package com.csinfotechbd.legal.report.datasheets.legalCourseOfAction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/legal/report/legal-course-of-action")
public class LegalCourseOfActionReportController {

    @Autowired
    private LegalCourseOfActionReportService legalCourseOfActionReportService;

    @GetMapping("/view")
    public String view(Model model){
        List<LegalCourseOfActionReportDto> contestedUnderArtharinSuit = legalCourseOfActionReportService.searchData("Artharin Suit", "Contested");
        List<LegalCourseOfActionReportDto> notContestedUnderArtharinSuit = legalCourseOfActionReportService.searchData("Artharin Suit", "Not Contested");

        model.addAttribute("reportTitle","Legal Course of Action Vintage Analysis");

        model.addAttribute("contestedUnderArtharinSuit", contestedUnderArtharinSuit);
        model.addAttribute("notContestedUnderArtharinSuit", notContestedUnderArtharinSuit);

        model.addAttribute("contestedUnderArtharinSuitTotal", new LegalCourseOfActionReportDto("Sub Total"));
        model.addAttribute("notContestedUnderArtharinSuitTotal", new LegalCourseOfActionReportDto("Sub Total"));

        return "legal/report/legalCourseOfAction/view";
    }
}
