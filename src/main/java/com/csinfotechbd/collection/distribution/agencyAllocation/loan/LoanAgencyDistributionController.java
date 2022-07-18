package com.csinfotechbd.collection.distribution.agencyAllocation.loan;
/*
Created by Monirul Islam at 7/22/2019
*/

import com.csinfotechbd.collection.distribution.loan.LoanAccountDistributionRepository;
import com.csinfotechbd.collection.distribution.loan.LoanDualEnum;
import com.csinfotechbd.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccount.LoanAccountService;
import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/loanagency/distribution/")
public class LoanAgencyDistributionController {

    private final LoanAccountBasicService loanAccountBasicService;
    private final LoanAgencyDistributionService loanAgencyDistributionService;
    private final LoanAccountService loanAccountService;
    private final LoanAccountOtherService loanAccountOtherService;


    @GetMapping("/add")
    public String addDistribution(Model model) {
        return "collection/distribution/agencyAllocation/loan/create";
    }

    @PostMapping("/save")
    public String saveDistribution(Model model, @RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        Map<String, Object> response = loanAgencyDistributionService.storeData(multipartFile);

        if (response.get("outcome").equals("failure")) {
            model.addAttribute("response", response);
            return "samd/dataEntry/loanAccountDistribution/manual";
        }
        else
            return "redirect:/collection/samd/loan-account-distribution/list";
    }
















    public Date getStartDate() {
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        return Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDate() {
        LocalDate today = LocalDate.now();
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
