package com.csinfotechbd.legal.report.bangladeshBankReports.bikolpoARDreport;


import com.csinfotechbd.legal.setup.alternativeWayReport.AlternativeWayReportEntity;
import com.csinfotechbd.legal.setup.alternativeWayReport.AlternativeWayReportRepository;
import com.csinfotechbd.legal.setup.artharinCourtReport.ArtharinCourtReportEntity;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/bangladesh-bank-reports/bikolpo-ard-report")
public class BikolpoARDreportController {

    private final BikolpoARDreportService service;
    private final DateUtils dateUtils;

    private final AlternativeWayReportRepository repository;

    @GetMapping("/view")
    public String view(Model model) {

        model.addAttribute("reportTitle", "বিকল্প পদ্ধতিতে বিরোধ নিষ্পত্তি (ADR)");
        return "legal/report/bangladeshBankReports/bikolpoARDreport/view";
    }

    @GetMapping("/report")
    public String getReport(String endDate, Model model) {
        Date date = dateUtils.getFormattedDate(endDate, "yyyy-MM-dd");
        date = dateUtils.getEndingPointOfDay(date);

        List<AlternativeWayReportEntity> alternativeWayReportEntities = repository.findAll();
        Integer pendingCases = 0;
        if(alternativeWayReportEntities.get(0).getNumberOne() !=null){
            pendingCases = Integer.parseInt(alternativeWayReportEntities.get(0).getNumberOne());
        }

        Double totalAmountOfPendingCases = 0.0;
        if(alternativeWayReportEntities.get(0).getClaimAmountOne() !=null){
            totalAmountOfPendingCases = (Double.parseDouble(alternativeWayReportEntities.get(0).getClaimAmountOne()));
        }

        Integer adjustedCases = 0;
        if(alternativeWayReportEntities.get(0).getNumberTwo() !=null){
            adjustedCases = Integer.parseInt(alternativeWayReportEntities.get(0).getNumberTwo());
        }

        Double totalAmountOfAdjustedCases = 0.0;
        if(alternativeWayReportEntities.get(0).getClaimAmountOne() !=null){
            totalAmountOfAdjustedCases = (Double.parseDouble(alternativeWayReportEntities.get(0).getClaimAmountTwo()));
        }

        Double totalRecoveryAfterCaseFromAdjustedCases = 0.0;
        if(alternativeWayReportEntities.get(0).getClaimAmountOne() !=null){
            totalRecoveryAfterCaseFromAdjustedCases = (Double.parseDouble(alternativeWayReportEntities.get(0).getActualRealization()));
        }

        model.addAttribute("alternativeWay",alternativeWayReportEntities.get(0));
        model.addAttribute("pendingCases",pendingCases+service.getReport(date).getTotalPendingCases());
        model.addAttribute("totalAmountOfPendingCases",(totalAmountOfPendingCases+service.getReport(date).getTotalAmountOfPendingCases())/100000);
        model.addAttribute("adjustedCases",adjustedCases+service.getReport(date).getTotalAdjustedCases());
        model.addAttribute("totalAmountOfAdjustedCases",(totalAmountOfAdjustedCases+service.getReport(date).getTotalAmountOfAdjustedCases())/100000);
        model.addAttribute("totalRecoveryAfterCaseFromAdjustedCases",(totalRecoveryAfterCaseFromAdjustedCases+service.getReport(date).getTotalRecoveryAfterCaseFromAdjustedCases())/100000);

        model.addAttribute("reportTitle", "বিকল্প পদ্ধতিতে বিরোধ নিষ্পত্তি (ADR)");
        model.addAttribute("summary", service.getReport(date));
        model.addAttribute("reportDate", dateUtils.getBanglaShortDate(date));
        return "legal/report/bangladeshBankReports/bikolpoARDreport/view";
    }


}
