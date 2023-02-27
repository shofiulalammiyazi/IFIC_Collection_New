package com.unisoft.collection.settingshelper;

import com.google.gson.Gson;
import com.unisoft.collection.distribution.card.CardController;
import com.unisoft.collection.distribution.card.CardViewModel;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.LoanController;
import com.unisoft.collection.distribution.loan.LoanViewModel;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicRepository;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.utillity.JasperReportManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/collection/settings/issueletter2")
public class Letter2Controller {

    private LoanController loanController;

    private CardController cardController;

    private AgencyService agencyService;

    private EmployeeService employeeService;

    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    private LoanAccountBasicRepository loanAccountBasicRepository;

    private CardAccountBasicRepository cardAccountBasicRepository;

    private LoanAccountDistributionRepository loanAccountDistributionRepository;

    private CardAccountDistributionRepository cardAccountDistributionRepository;

    private JasperReportManager jasperReportManager;

    @GetMapping("/aclist")
    public String accountList(Model model, String unit) {
        model.addAttribute("unit", unit.toLowerCase());

        if (unit.toLowerCase().equals("loan")) {
            List<LoanViewModel> loanViewModels = loanController.getLoanViewModels();
            List<EmployeeInfoEntity> dealerList = employeeService.getDealerFromDao();
            List<EmployeeInfoEntity> supervisorList = employeeService.getSuperVisorList();
            List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();

            dealerList.addAll(supervisorList);
            dealerList.addAll(teamLeaderList);

            Gson gson = new Gson();

            model.addAttribute("loanviewlist", loanViewModels);
            model.addAttribute("loanviewlistJson", gson.toJson(loanViewModels));
            model.addAttribute("dealerList", dealerList);
            model.addAttribute("agencyList", agencyService.getAll());

            return "collection/settings/LetterTemplates/allocatedLoanList";
        } else {
            List<CardViewModel> loanViewModels = cardController.getCardViewModels();
            List<EmployeeInfoEntity> dealerList = employeeService.getDealerFromDao();
            List<EmployeeInfoEntity> supervisorList = employeeService.getSuperVisorList();
            List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();

            dealerList.addAll(supervisorList);
            dealerList.addAll(teamLeaderList);

            Gson gson = new Gson();

            model.addAttribute("loanviewlist", loanViewModels);
            model.addAttribute("loanviewlistJson", gson.toJson(loanViewModels));
            model.addAttribute("dealerList", dealerList);
            model.addAttribute("agencyList", agencyService.getAll());

            return "collection/settings/LetterTemplates/allocatedLoanList";
        }
    }

    @GetMapping("/generate")
    public String generateLetter(@RequestParam(name = "account", required = true) String Account,
                                 @RequestParam(name = "letterType", required = true) String letterType,
                                 @RequestParam(name = "unit", required = true) String unit,
                                 Model model) {

        CustomerBasicInfoEntity customer = customerBasicInfoEntityRepository.findFirstByAccountNoOrderByAccountNoAsc(Account);
        model.addAttribute("customer", customer);

        if (unit.equalsIgnoreCase("loan")) {
            Optional<LoanAccountBasicInfo> loanAccountBasicInfo = loanAccountBasicRepository.findByAccountNo(customer.getAccountNo());
            LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), loanAccountBasicInfo.get(), "1");

            model.addAttribute("customerAddress", loanAccountBasicInfo.get().getLocation());
            model.addAttribute("overdueAmount", String.format("%.2f", Double.valueOf(loanAccountDistributionInfo.getOpeningOverDue())));
            model.addAttribute("emiAmount", String.format("%.2f", Double.valueOf(loanAccountDistributionInfo.getEmiAmount())));
            model.addAttribute("outstandingAmount", String.format("%.2f", Double.valueOf(loanAccountDistributionInfo.getOutStanding())));
            model.addAttribute("productGroup", loanAccountDistributionInfo.getProductGroup());
            model.addAttribute("accountLimit", "0.00");
        } else {
            CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicRepository.findByCardNo(customer.getAccountNo());
            CardAccountDistributionInfo cardAccountDistributionInfo =
                    cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), cardAccountBasicInfo, "1");

            model.addAttribute("customerId", customer.getCustomerId());
            model.addAttribute("customerAddress", "-");
            model.addAttribute("minDueAmount", String.format("%.2f", Double.valueOf(cardAccountDistributionInfo.getMinDuePayment())));
            model.addAttribute("outstandingAmount", String.format("%.2f", Double.valueOf(cardAccountDistributionInfo.getOutstandingAmount())));
            model.addAttribute("productGroup", cardAccountDistributionInfo.getProductGroup());
        }

        return "collection/settings/LetterTemplates/" + letterType;
    }

    @GetMapping("/generate-bulk")
    public void generateLetterBulk(HttpServletResponse response,
                                   @RequestParam(name = "accounts") List<String> accounts,
                                   @RequestParam(name = "letterType") String letterType,
                                   @RequestParam(name = "unit") String unit) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("acNo", accounts);

        if ((accounts != null && accounts.size() != 0)
                && (letterType != null && !letterType.isEmpty())
                && (unit != null && !unit.isEmpty())) {
            if (unit.toLowerCase().equals("card")) {
                if (letterType.equals("letterAge1"))
                    jasperReportManager.exportToPdf(response, parameters, "Age Code 1 | ",
                            "letters/card/age_code1/age_code1.jasper");
                else if (letterType.equals("letterAge2"))
                    jasperReportManager.exportToPdf(response, parameters, "Age Code 2 | ",
                            "letters/card/age_code2/age_code2.jasper");
                else if (letterType.equals("letterAge3"))
                    jasperReportManager.exportToPdf(response, parameters, "Age Code 3 | ",
                            "letters/card/age_code3/age_code3.jasper");
                else if (letterType.equals("letterAge4"))
                    jasperReportManager.exportToPdf(response, parameters, "Age Code 4 | ",
                            "letters/card/age_code4/age_code4.jasper");
                else if (letterType.equals("letterAge5"))
                    jasperReportManager.exportToPdf(response, parameters, "Age Code 5 | ",
                            "letters/card/age_code5/age_code5.jasper");
                else if (letterType.equals("letterAge6"))
                    jasperReportManager.exportToPdf(response, parameters, "Age Code 6 | ",
                            "letters/card/age_code6/age_code6.jasper");
                else if (letterType.equals("letterAge7"))
                    jasperReportManager.exportToPdf(response, parameters, "Age Code 7",
                            "letters/card/age_code7/age_code7.jasper");
            } else if (unit.toLowerCase().equals("loan")) {
                if (letterType.equals("soft_reminder"))
                    jasperReportManager.exportToPdf(response, parameters, "Soft Reminder | ",
                            "letters/loan/soft_remainder/soft_remainder.jasper");
                else if (letterType.equals("first_reminder"))
                    jasperReportManager.exportToPdf(response, parameters, "First Reminder | ",
                            "letters/loan/first_remainder/first_remainder.jasper");
                else if (letterType.equals("second_reminder"))
                    jasperReportManager.exportToPdf(response, parameters, "Second Reminder | ",
                            "letters/loan/second_remainder/second_remainder.jasper");
                else if (letterType.equals("final_reminder"))
                    jasperReportManager.exportToPdf(response, parameters, "Final Reminder | ",
                            "letters/loan/final_reminder/final_remainder.jasper");
                else if (letterType.equals("facility_callup"))
                    jasperReportManager.exportToPdf(response, parameters, "Facility Call Up | ",
                            "letters/loan/facility_callup/facility_callup.jasper");
                else if (letterType.equals("car_reposess"))
                    jasperReportManager.exportToPdf(response, parameters, "Car Repossess | ",
                            "letters/loan/car_repose/car_repose.jasper");
                else if (letterType.equals("personal_gurrantor"))
                    jasperReportManager.exportToPdf(response, parameters, "Personal Guarantor | ",
                            "letters/loan/personal_gurantor/personal_gurantor.jasper");
            }
        }
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
            System.err.println(e.getMessage());
        }
        return null;
    }

}
