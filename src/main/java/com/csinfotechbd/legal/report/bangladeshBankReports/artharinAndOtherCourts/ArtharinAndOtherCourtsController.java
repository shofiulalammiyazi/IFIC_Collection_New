package com.csinfotechbd.legal.report.bangladeshBankReports.artharinAndOtherCourts;


import com.csinfotechbd.legal.setup.artharinCourtReport.ArtharinCourtReportEntity;
import com.csinfotechbd.legal.setup.artharinCourtReport.ArtharinCourtReportRepository;
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
@RequestMapping("/legal/report/bangladesh-bank-reports/artharin-and-other-court")
public class ArtharinAndOtherCourtsController {

    private final ArtharinAndOtherCourtsService service;
    private final DateUtils dateUtils;

    private final ArtharinCourtReportRepository repository;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "অর্থঋণ আদালত ও অন্যান্য আদালত");
        return "legal/report/bangladeshBankReports/artharinAndOtherCourt/view";
    }

    @GetMapping("/report")
    public String getReport(String endDate, Model model) {
        Date date = dateUtils.getFormattedDate(endDate, "yyyy-MM-dd");
        date = dateUtils.getEndingPointOfDay(date);

        List<ArtharinCourtReportEntity> artharinCourtReportEntityList = repository.findAll();
        ArtharinCourtReportEntity data = artharinCourtReportEntityList.get(0);

        int numberOne = 0;
        Double claimAmountOne = 0.00;
        int numberTwo = 0;
        Double claimAmountTwo = 0.00;
        Double actualRealization = 0.00;
        Double claimAmountOfCollection = 0.00;

       if (data.getArtharinNumberOne() != null)
           numberOne = Integer.parseInt(data.getArtharinNumberOne());

       if (data.getArtharinClaimAmountOne() != null)
           claimAmountOne = Double.parseDouble(data.getArtharinClaimAmountOne())/100000;

       if (data.getArtharinNumberTwo() != null)
           numberTwo = Integer.parseInt(data.getArtharinNumberTwo());

       if (data.getArtharinClaimAmountTwo() != null)
           claimAmountTwo = Double.parseDouble(data.getArtharinClaimAmountTwo())/100000;

       if (data.getArtharinActualRealization() != null)
           actualRealization = Double.parseDouble(data.getArtharinActualRealization())/100000;

       if (data.getArtharinClaimAmountOfCollection() !=null)
           claimAmountOfCollection = Double.parseDouble(data.getArtharinClaimAmountOfCollection())/100000;

        model.addAttribute("numberOne",numberOne);
        model.addAttribute("claimAmountOne",claimAmountOne);
        model.addAttribute("numberTwo",numberTwo);
        model.addAttribute("claimAmountTwo",claimAmountTwo);
        model.addAttribute("actualRealization",actualRealization);
        model.addAttribute("claimAmountOfCollection",claimAmountOfCollection);


        int bankruptcyNumberOne = 0;
        Double bankruptcyClaimAmountOne = 0.00;
        int bankruptcyNumberTwo = 0;
        Double bankruptcyClaimAmountTwo = 0.00;
        Double bankruptcyActualRealization = 0.00;
        Double bankruptcyClaimAmountOfCollection = 0.00;

        if (data.getBankruptcyNumberOne() != null)
            bankruptcyNumberOne = Integer.parseInt(data.getBankruptcyNumberOne());

        if (data.getBankruptcyClaimAmountOne() !=null)
            bankruptcyClaimAmountOne = Double.parseDouble(data.getBankruptcyClaimAmountOne())/100000;

        if (data.getBankruptcyNumberTwo() !=null)
            bankruptcyNumberTwo = Integer.parseInt(data.getBankruptcyNumberTwo());


        if (data.getBankruptcyClaimAmountTwo() !=null)
            bankruptcyClaimAmountTwo = Double.parseDouble(data.getBankruptcyClaimAmountTwo())/100000;

        if (data.getBankruptcyActualRealization() !=null)
            bankruptcyActualRealization = Double.parseDouble(data.getBankruptcyActualRealization())/100000;

        if (data.getBankruptcyClaimAmountOfCollection() !=null)
            bankruptcyClaimAmountOfCollection = Double.parseDouble(data.getBankruptcyClaimAmountOfCollection())/100000;

        model.addAttribute("bankruptcyNumberOne",bankruptcyNumberOne);
        model.addAttribute("bankruptcyClaimAmountOne",bankruptcyClaimAmountOne);
        model.addAttribute("bankruptcyNumberTwo",bankruptcyNumberTwo);
        model.addAttribute("bankruptcyClaimAmountTwo",bankruptcyClaimAmountTwo);
        model.addAttribute("bankruptcyActualRealization",bankruptcyActualRealization);
        model.addAttribute("bankruptcyClaimAmountOfCollection",bankruptcyClaimAmountOfCollection);


        int othersNumberOne = 0;
        Double othersClaimAmountOne = 0.00;
        int othersNumberTwo = 0;
        Double othersClaimAmountTwo = 0.00;
        Double othersActualRealization = 0.00;
        Double othersClaimAmountOfCollection = 0.00;

        if (data.getOthersNumberOne() !=null)
            othersNumberOne = Integer.parseInt(data.getOthersNumberOne());

        if (data.getOthersClaimAmountOne() !=null)
            othersClaimAmountOne = Double.parseDouble(data.getOthersClaimAmountOne())/1000000;

        if (data.getOthersNumberTwo() !=null)
            othersNumberTwo = Integer.parseInt(data.getOthersNumberTwo());

        if (data.getOthersClaimAmountTwo() !=null)
            othersClaimAmountTwo = Double.parseDouble(data.getOthersClaimAmountTwo())/100000;

        if (data.getOthersActualRealization() !=null)
            othersActualRealization = Double.parseDouble(data.getOthersActualRealization())/100000;

        if (data.getOthersClaimAmountOfCollection() !=null)
            othersClaimAmountOfCollection = Double.parseDouble(data.getOthersClaimAmountOfCollection())/100000;

        model.addAttribute("othersNumberOne",othersNumberOne);
        model.addAttribute("othersClaimAmountOne",othersClaimAmountOne);
        model.addAttribute("othersNumberTwo",othersNumberTwo);
        model.addAttribute("othersClaimAmountTwo",othersClaimAmountTwo);
        model.addAttribute("othersActualRealization",othersActualRealization);
        model.addAttribute("othersClaimAmountOfCollection",othersClaimAmountOfCollection);

        int totalNumberOne = 0;
        Double totalClaimAmountOne = 0.00;
        int totalNumberTwo = 0;
        Double totalClaimAmountTwo = 0.00;
        Double totalActualRealization = 0.00;
        Double totalClaimAmountOfCollection = 0.00;

        if (data.getTotalNumberOne() !=null)
            totalNumberOne = Integer.parseInt(data.getTotalNumberOne());

        if (data.getTotalClaimAmountOne() !=null)
            totalClaimAmountOne = Double.parseDouble(data.getTotalClaimAmountOne())/100000;

        if (data.getTotalNumberTwo() !=null)
            totalNumberTwo = Integer.parseInt(data.getTotalNumberTwo());

        if (data.getTotalClaimAmountTwo() !=null)
            totalClaimAmountTwo = Double.parseDouble(data.getTotalClaimAmountTwo())/100000;

        if (data.getTotalActualRealization() !=null)
            totalActualRealization = Double.parseDouble(data.getTotalActualRealization())/100000;

        if (data.getTotalClaimAmountOfCollection() !=null)
            totalClaimAmountOfCollection = Double.parseDouble(data.getTotalClaimAmountOfCollection())/100000;


        model.addAttribute("totalNumberOne",totalNumberOne);
        model.addAttribute("totalClaimAmountOne",totalClaimAmountOne);
        model.addAttribute("totalNumberTwo",totalNumberTwo);
        model.addAttribute("totalClaimAmountTwo",totalClaimAmountTwo);
        model.addAttribute("totalActualRealization",totalActualRealization);
        model.addAttribute("totalClaimAmountOfCollection",totalClaimAmountOfCollection);

        model.addAttribute("reportTitle", "অর্থঋণ আদালত ও অন্যান্য আদালত");
        model.addAttribute("summary", service.getReport(date));
        model.addAttribute("reportDate", dateUtils.getBanglaShortDate(date));
        return "legal/report/bangladeshBankReports/artharinAndOtherCourt/view";
    }


}
