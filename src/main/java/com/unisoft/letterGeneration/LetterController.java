package com.unisoft.letterGeneration;

import com.unisoft.beans.Validation;
import com.unisoft.collection.distribution.card.CardController;
import com.unisoft.collection.distribution.card.CardViewModel;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.LoanController;
import com.unisoft.collection.distribution.loan.LoanViewModel;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicRepository;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.lettertemplate.LetterTemplateService;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.customerloanprofile.guarantorinfo.GurantorInfoRepository;
import com.unisoft.customerloanprofile.vehicleRepossession.VehicleRepossessionRepository;
import com.unisoft.detailsOfCollection.cardviewmodels.AccountInformation;
import com.unisoft.loanApi.model.CustomerAndBorrowerInfo;
import com.unisoft.loanApi.model.CustomerAndBorrowerRepo;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserService;
import com.unisoft.utillity.DateUtils;
import com.unisoft.utillity.JasperReportManager;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller("letterGeneration3")
@AllArgsConstructor
@RequestMapping("/collection/settings/issueletter")
public class LetterController {

    private LoanController loanController;
    private CardController cardController;
    private AgencyService agencyService;
    private EmployeeService employeeService;
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;
    private LoanAccountBasicRepository loanAccountBasicRepository;
    private CardAccountBasicRepository cardAccountBasicRepository;
    @Autowired
    private LoanAccountDistributionRepository loanAccountDistributionRepository;
    private CardAccountDistributionRepository cardAccountDistributionRepository;
    private JasperReportManager jasperReportManager;
    private Environment environment;
    private JavaMailSender mailSender;
    private EmailedLetterHistoriesService emailedLetterHistoriesService;

    private RetailLoanUcbApiService retailLoanUcbApiService;
    @Autowired
    private CustomerAndBorrowerRepo customerAndBorrowerRepo;
    @Autowired
    private GurantorInfoRepository gurantorInfoRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VehicleRepossessionRepository vehicleRepossessionRepository;
    private final DateUtils dateUtils;

    @Autowired
    private LetterTemplateService letterTemplateService;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @GetMapping("/aclist")
    public String accountList(Model model, String unit){
        model.addAttribute("unit", unit.toLowerCase());

        if (unit.toLowerCase().equals("loan")){
            List<LoanViewModel> loanViewModels = loanController.getLoanViewModels();
            List<EmployeeInfoEntity> dealerList = employeeService.getDealerFromDao();
            List<EmployeeInfoEntity> supervisorList = employeeService.getSuperVisorList();
            List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();

            dealerList.addAll(supervisorList);
            dealerList.addAll(teamLeaderList);

            Gson gson = new Gson();

            model.addAttribute("templates", letterTemplateService.getAllByUnit("Loan"));
            model.addAttribute("loanviewlist", loanViewModels);
            model.addAttribute("loanviewlistJson", gson.toJson(loanViewModels));
            model.addAttribute("dealerList", dealerList);
            model.addAttribute("agencyList", agencyService.getAll());

            return "collection/settings/LetterTemplates/allocatedLoanList";
        }
        else {
            List<CardViewModel> loanViewModels = cardController.getCardViewModels();
            List<EmployeeInfoEntity> dealerList = employeeService.getDealerFromDao();
            List<EmployeeInfoEntity> supervisorList = employeeService.getSuperVisorList();
            List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();

            dealerList.addAll(supervisorList);
            dealerList.addAll(teamLeaderList);

            Gson gson = new Gson();

            model.addAttribute("templates", letterTemplateService.getAllByUnit("Card"));
            model.addAttribute("loanviewlist", loanViewModels);
            model.addAttribute("loanviewlistJson", gson.toJson(loanViewModels));
            model.addAttribute("dealerList", dealerList);
            model.addAttribute("agencyList", agencyService.getAll());

            return "collection/settings/LetterTemplates/allocatedLoanList";
        }
    }

    @ResponseBody
    @PostMapping("/import-via-excel")
    public Map<String, Object> importViaExcel(MultipartFile file, String unit) {
        Map<String, Object> data = new HashMap<>();
        List<String> errorMessages = this.validate(file);

        if(errorMessages.size() == 0){
            if (unit.toLowerCase().equals("loan")){

            }
            else {

            }

            data.put("outcome", "success");
        }
        else {
            data.put("outcome", "failure");
            data.put("errors", errorMessages);
        }

        return data;
    }

    @GetMapping("/generate")
    public String generateLetter(@RequestParam(name = "account", required = true) String Account,
                                 @RequestParam(name = "letterType", required = true)String letterType,
                                 @RequestParam(name = "unit", required = true)String unit,
                                 LetterModel letterModel,
                                 Model model){
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomerBasicInfoEntity customer = customerBasicInfoEntityRepository.findFirstByAccountNoOrderByAccountNoSubStr(Account);
        AccountInformationEntity accountInformation = accountInformationRepository.getByLoanAccountNoSub(Account);

        if(accountInformation != null){
            accountInformation.setEmiDate(dateUtils.db2ToOracleDateFormat(accountInformation.getEmiDate()));
            accountInformation.setDisbursementDate(dateUtils.db2ToOracleDateFormat(accountInformation.getDisbursementDate()));
        }

        model.addAttribute("customer", customer);
        //model.addAttribute("customerInfo",retailLoanUcbApiService.getCustomerInfo(customer.getCustomerId()));
        //model.addAttribute("loanAccDetails",retailLoanUcbApiService.getLoanAccountDetails(Account));
        model.addAttribute("loanAccDetails",accountInformation);
        model.addAttribute("letterModel",letterModel);
        model.addAttribute("guarantorInfo",gurantorInfoRepository.findByAccountNo(Account));
        model.addAttribute("letterPayload",loanAccountDistributionRepository.getLetterInfoBySubAcNo(Account,dateUtils.getMonthStartDate(),dateUtils.getLocalMonthEndDate()));
        model.addAttribute("employeeInfo",employeeRepository.findByPin(principal.getUsername()));
        model.addAttribute("vehicleInfo",vehicleRepossessionRepository.findByCustomerId(String.valueOf(customer.getId())));
        if(customer != null){
            CustomerAndBorrowerInfo customerAndBorrowerInfo = customerAndBorrowerRepo.findByCustomerId(customer.getId());
            model.addAttribute("customerAndBorrowerInfo",customerAndBorrowerInfo == null ? new CustomerAndBorrowerInfo(): customerAndBorrowerInfo);
        }

        if (unit.toLowerCase().equals("loan")){
            Optional<LoanAccountBasicInfo> loanAccountBasicInfo = loanAccountBasicRepository.findByAccountNo(customer.getAccountNo());
            LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), loanAccountBasicInfo.get(), "1");

            model.addAttribute("customerAddress", loanAccountBasicInfo.get().getLocation());
            model.addAttribute("overdueAmount", String.format("%.2f", Double.valueOf(loanAccountDistributionInfo.getOpeningOverDue())));
            model.addAttribute("emiAmount", String.format("%.2f", Double.valueOf(loanAccountDistributionInfo.getEmiAmount())));
            model.addAttribute("outstandingAmount", String.format("%.2f", Double.valueOf(loanAccountDistributionInfo.getOutStanding())));
            model.addAttribute("productGroup", loanAccountDistributionInfo.getProductGroup());
            model.addAttribute("accountLimit", "0.00");
        }
        else{
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
                                   @RequestParam(name = "unit") String unit){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("acNo", accounts);

        if((accounts != null && accounts.size() != 0)
                && (letterType != null && !letterType.isEmpty())
                && (unit != null && !unit.isEmpty())){
            if(unit.toLowerCase().equals("card")){
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
            }
            else if (unit.toLowerCase().equals("loan")){
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

    @ResponseBody
    @PostMapping("/send-email")
    public boolean sendEmail(EmailedLetterHistories emailLetter){
        System.out.println("email letter : "+emailLetter.getEmailBody());
        emailLetter.setEmailBody(StringEscapeUtils.unescapeHtml(emailLetter.getEmailBody()));
        System.out.println("after escape : "+emailLetter.getEmailBody());
        emailedLetterHistoriesService.insertData(emailLetter);

        Thread sentEmailThread = new Thread(() -> {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(environment.getProperty("support.email"));
                mimeMessageHelper.setTo(emailLetter.getEmailTo());
                if(emailLetter.getEmailCC() != null && !emailLetter.getEmailCC().isEmpty()) {
                    String[] emailCC = emailLetter.getEmailCC().split(",");
                    mimeMessageHelper.setCc(emailCC);
                }
                mimeMessageHelper.setSubject(emailLetter.getEmailSubject());
                mimeMessageHelper.setText(emailLetter.getEmailBody(), true);

                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        sentEmailThread.start();
        return true;
    }

    public Date getStartDate(){
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        return Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDate(){
        LocalDate today = LocalDate.now();
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> validate(MultipartFile file) {
        ArrayList<String> errors = new ArrayList<>();
        List<String> validCells = Arrays.asList(
                new String[]{"ACCOUNT NO", "LETTER TYPE"});

        if (Validation.isStringEmpty(file.getOriginalFilename())){
            errors.add("Attachment File required");
        }
        else {
            try{
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();

                Iterator<Cell> cells = row.cellIterator();
                List<String> addedCellNames = new ArrayList<String>();

                while (cells.hasNext()){
                    addedCellNames.add(Objects.toString(cells.next(), "").toUpperCase());
                }

                //Check Columns Validity
                for (String cellName:validCells){
                    if (!addedCellNames.contains(cellName)){
                        errors.add(cellName + " is required");
                    }
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return errors;
    }

}
