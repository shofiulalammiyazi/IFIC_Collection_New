package com.csinfotechbd.legal.report.bangladeshBankReports.bankBimaPendingCases;


import com.csinfotechbd.legal.setup.artharinCourtReport.ArtharinCourtReportEntity;
import com.csinfotechbd.legal.setup.artharinCourtReport.ArtharinCourtReportRepository;
import com.csinfotechbd.utillity.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/bangladesh-bank-reports/bank-bima-pending-cases")
public class BankBimaPendingCasesController {

    private final BankBimaPendingCasesService service;
    private final ArtharinCourtReportRepository repository;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "ব্যাংক , বীমা ও আর্থিক প্রতিষ্ঠান");
        return "legal/report/bangladeshBankReports/bankBimaPendingCases/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public BankBimaPendingCasesDto getReport(String startDate, String endDate,  Model model) {
        List<ArtharinCourtReportEntity> artharinCourtReportEntityList = repository.findAll();
        ArtharinCourtReportEntity data = artharinCourtReportEntityList.get(0);

        int bankruptcyNumberOne = 0;
        Double bankruptcyClaimAmountOne = 0.00;
        int bankruptcyNumberTwo = 0;
        Double bankruptcyClaimAmountTwo = 0.00;
        Double bankruptcyActualRealization = 0.00;
        Double bankruptcyClaimAmountOfCollection = 0.00;

        if (data.getBankruptcyNumberOne() != null)
            bankruptcyNumberOne = Integer.parseInt(data.getBankruptcyNumberOne());

        if (data.getBankruptcyClaimAmountOne() !=null)
            bankruptcyClaimAmountOne = (Double.parseDouble(data.getBankruptcyClaimAmountOne())/10000000);

        if (data.getBankruptcyNumberTwo() !=null)
            bankruptcyNumberTwo = Integer.parseInt(data.getBankruptcyNumberTwo());


        if (data.getBankruptcyClaimAmountTwo() !=null)
            bankruptcyClaimAmountTwo = (Double.parseDouble(data.getBankruptcyClaimAmountTwo())/10000000);

        if (data.getBankruptcyActualRealization() !=null)
            bankruptcyActualRealization = Double.parseDouble(data.getBankruptcyActualRealization())/10000000;

        if (data.getBankruptcyClaimAmountOfCollection() !=null)
            bankruptcyClaimAmountOfCollection = Double.parseDouble(data.getBankruptcyClaimAmountOfCollection())/10000000;


        Integer othersNumberOne = 0;
        Double othersClaimAmountOne = 0.00;
        Integer othersNumberTwo = 0;
        Double othersClaimAmountTwo = 0.00;
        Double othersActualRealization = 0.00;
        Double othersClaimAmountOfCollection = 0.00;

        if (data.getOthersNumberOne() !=null)
            othersNumberOne = Integer.parseInt(data.getOthersNumberOne());

        if (data.getOthersClaimAmountOne() !=null)
            othersClaimAmountOne = Double.parseDouble(data.getOthersClaimAmountOne())/10000000;

        if (data.getOthersNumberTwo() !=null)
            othersNumberTwo = Integer.parseInt(data.getOthersNumberTwo());

        if (data.getOthersClaimAmountTwo() !=null)
            othersClaimAmountTwo = Double.parseDouble(data.getOthersClaimAmountTwo())/10000000;

        if (data.getOthersActualRealization() !=null)
            othersActualRealization = Double.parseDouble(data.getOthersActualRealization())/10000000;

        if (data.getOthersClaimAmountOfCollection() !=null)
            othersClaimAmountOfCollection = Double.parseDouble(data.getOthersClaimAmountOfCollection())/10000000;



        BankBimaPendingCasesDto data2 = service.getReport(startDate, endDate);
        Integer bankcrupt = bankruptcyNumberOne-bankruptcyNumberTwo;
        Double bankcruptClaimAmount = bankruptcyClaimAmountOne-bankruptcyClaimAmountTwo;

        data2.setTotalBankrupcyCount(calculateIntValue(data2.getTotalBankrupcyCount(),bankcrupt));
        data2.setTotalBankrupcyCaseAmount(calculateDoubleValue(data2.getTotalBankrupcyCaseAmount(),bankcruptClaimAmount));

        Integer setupCase = othersNumberOne-othersNumberTwo;
        Double setupCaseAmount = othersClaimAmountOne-othersClaimAmountTwo;

        data2.setOthersCase(otherCase(setupCase));
        data2.setOthersCaseAmount(otherCaseAmount(setupCaseAmount));

        data2.setTotalCount(totalCaseCountConvert(data2.getTotalCount().replaceAll(",",""), bankcrupt, setupCase));
        data2.setTotalCaseAmount(totalCaseAmountConvert(data2.getTotalCaseAmount(), bankcruptClaimAmount, setupCaseAmount));

        return data2;
    }

    public String calculateIntValue(String value, int value2){
        NumberUtils numberUtils = new NumberUtils();

        String convertBangla = numberUtils.convertToBanglaNumber(value2);
        Integer b = Integer.parseInt(value);
        Integer c = Integer.parseInt(convertBangla);
        Integer d = 0+c;

        return numberUtils.convertToBanglaNumber(d);
    }

    public String calculateDoubleValue(String value, Double value2){
        NumberUtils numberUtils = new NumberUtils();

        Double c = Double.parseDouble(value);
        Double d = value2+0;

        return numberUtils.convertToBanglaNumber(d);
    }

    public String otherCase(Integer value){
        NumberUtils numberUtils = new NumberUtils();

        return numberUtils.convertToBanglaNumber(value);
    }

    public String otherCaseAmount(Double value){
        NumberUtils numberUtils = new NumberUtils();

        return numberUtils.convertToBanglaNumber(value);
    }

    public String totalCaseCountConvert(String total, Integer bank, Integer setupCase){
        NumberUtils numberUtils = new NumberUtils();

        Integer t = Integer.parseInt(total);

        Integer totals = t+bank+setupCase;

        return numberUtils.convertToBanglaNumber(totals);
    }

    public String totalCaseAmountConvert(String total, Double bank, Double setupCaseAmount){
        NumberUtils numberUtils = new NumberUtils();

        Double t = Double.parseDouble(total.replace(",",""));

        Double totals = t+bank+setupCaseAmount;

        return numberUtils.convertToBanglaNumber(totals);
    }
}
