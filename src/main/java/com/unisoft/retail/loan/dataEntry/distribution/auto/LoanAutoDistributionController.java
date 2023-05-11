package com.unisoft.retail.loan.dataEntry.distribution.auto;

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.settings.SMS.generate.GeneratedSMS;
import com.unisoft.collection.settings.SMS.sendSms.SendSmsToCustomerService;
import com.unisoft.collection.settings.SMS.smsType.SMSEntity;
import com.unisoft.collection.settings.SMS.smsType.SMSService;
import com.unisoft.collection.settings.SMS.template.TemplateGenerate;
import com.unisoft.collection.settings.SMS.template.TemplateGenerateRepository;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeInfoDto;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.smsAndAutoDistributionRules.SmsAndAutoDistributionRulesService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import com.unisoft.schedulerinformation.SchedulerInformationEntity;
import com.unisoft.schedulerinformation.SchedulerInformationRepository;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/retail/loan/data-entry/distribution/auto/")
public class LoanAutoDistributionController {

    private final LoanAutoDistributionService service;
    private final EmployeeService employeeService;
    private final AgencyService agencyService;
    private final LoanAutoDistributionService autoDistributionService;
    private final AccountInformationService accountInformationService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private TemplateGenerateRepository templateGenerateRepository;

    @Autowired
    private SendSmsToCustomerService sendSmsToCustomerService;

    @Autowired
    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private SmsAndAutoDistributionRulesService smsAndAutoDistributionRulesService;

    @Value("${ific.excel.file-path}")
    private String excelServerPath;

    @Autowired
    private SchedulerInformationRepository schedulerInformationRepository;

    @GetMapping("approval")
    public String getDelinquentAccountList(Model model) {

        List<LoanAutoDistributionViewModel> distributionSummary = service.getCurrentMonthAutoDistributionSummary();
        List<LoanAutoDistributionDto> unallocatedList = service.getCurrentMonthUnallocatedList();
        List<String> exceptionUnits = Arrays.asList("SAM", "Write Off", "Legal"); // Avoid employees from SAM, Legal or Write Off
        List<EmployeeInfoDto> dealerList = employeeService.findEmployeePinsByUnitAndDesignation(Arrays.asList("Dealer", "Supervisor"), "Loan", exceptionUnits);

        List<AgencyEntity> agencyList = agencyService.getActiveList();

        model.addAttribute("distributionSummary", distributionSummary);
        model.addAttribute("unallocatedList", unallocatedList);
        model.addAttribute("employeeList", dealerList);
        model.addAttribute("agencyList", agencyList);

        return "retail/loan/dataEntry/distribution/auto/approval";
    }


    @GetMapping("list")
    public String getTemporaryDistributionList(Model model) {

        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("smsEntityList", smsEntityList);

        return "retail/loan/dataEntry/distribution/auto/distributionlist";
    }

    @GetMapping("delinquint-ac-list")
    public String getAllDelinquentAccList(Model model) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(userPrincipal.getUsername());
        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("roleName",employeeInfoEntity.getUser().getRoles().get(0).getName());
        model.addAttribute("smsEntityList", smsEntityList);

        return "retail/loan/dataEntry/distribution/auto/delinquentaccountlist";
    }

    @GetMapping("distributionlist")
    public String getDistributionList(Model model) {

        model.addAttribute("dealerList", peopleAllocationLogicRepository.findByUnitAndDistributionType("Loan", "Regular"));
        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("smsEntityList", smsEntityList);

        return "retail/loan/dataEntry/distribution/auto/autodistributionlist";
    }


    @GetMapping("redistribute")
    public String redistributeDelinquentAccounts() {
        autoDistributionService.getCurrentMonthDelinquentAccountsFromClientApi();
        autoDistributionService.temporarilyDistributeDelinquentAccounts();
        return "redirect:list";
    }

    @GetMapping("/sendsms")
    public ResponseEntity<Map<String, Object>> generatesms(@RequestParam(value = "accountNo") List<String> loanViewModelForSMS,
                                                           @RequestParam(value = "smsType") Long smsType, Model model) {

        Map<String, Object> map = new HashMap<>();
        String sms = "";

        List<GeneratedSMS> generatedSMS = new ArrayList<>();

        SMSEntity smsEntity = smsService.findSmsById(smsType);
        TemplateGenerate templateGenerate = templateGenerateRepository.findTemGenBySmsTypeId(smsType);

        for (String acc : loanViewModelForSMS) {
            String[] content = acc.split(":");
            sms = templateGenerate.getMassege();
            sms = sms.replace("{{F.accountNo}}", content[0]);
            sms = sms.replace("{{F.installmentAmount}}", String.valueOf(Integer.parseInt(content[3]) / 100));
            sms = sms.replace("{{F.nextEmiDate}}", content[4]);
            sms = sms.replace("{{F.currentMonth}}", content[5]);
            sms = sms.replace("{{F.productName}}", content[2]);
            GeneratedSMS generatedSMS1 = new GeneratedSMS(Long.valueOf(content[8]), smsEntity, sms, content[0], content[1]);
            generatedSMS.add(generatedSMS1);
        }

        String status = sendSmsToCustomerService.sendBulksms(generatedSMS,"emi");
        map.put("state", status);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Scheduled(cron = "0 00 11 * * *")
    //@Scheduled(cron = "0 5 10 * * *")
    @GetMapping("/sendAllSms")
    public String autoSmsEmiDateWise() {
//        String sms1 = "";
//        String sms = "Your IFIC Aamar Bari Loan EMI due date is {{F.nextEmiDate}}. " +
//                "Pls, deposit BDT{{F.installmentAmount}} within due date  to keep the loan regular. " +
//                "Pls, ignore if it is already paid.";

//        String sms = "Your {{F.productName}} EMI due date is {{F.nextEmiDate}}. " +
//                "Pls, deposit BDT{{F.installmentAmount}} to keep the loan regular. " +
//                "Pls, ignore if it is already paid.";

        SchedulerInformationEntity accountInformation = schedulerInformationRepository.findBySchedulerNameAndStatus("SMS", 1);

        if(accountInformation == null)
            return "OK";


        List<AccountInformationEntity> accountInformationEntities = accountInformationRepository.finAllEligibleSmsList();
        List<GeneratedSMS> generatedSMS = new ArrayList<>();
        for (AccountInformationEntity acc : accountInformationEntities) {
            String sms = "Your IFIC Aamar Bari loan installment due date is {{F.nextEmiDate}}. " +
                    "If it is unpaid, please deposit BDT{{F.installmentAmount}} within due date to keep the loan regular." ;


            //Your IFIC Aamar Bari loan installment due date is 10/02/23. If it is unpaid, pls, deposit BDT999,999.00 within due date to keep the loan regular.  (145)

            sms = sms.replace("{{F.accountNo}}", acc.getLoanACNo());
            if(acc.getEmiAmount() != null)
                sms = sms.replace("{{F.installmentAmount}}",acc.getEmiAmount());
            else sms = sms.replace("{{F.installmentAmount}}","0");
            sms = sms.replace("{{F.nextEmiDate}}", acc.getNextEMIDate());
            sms = sms.replace("{{F.currentMonth}}", new SimpleDateFormat("MMM").format(new Date()));
            sms = sms.replace("{{F.productName}}", acc.getProductName().trim());
            //TODO change phone number here use acc.getMobile()
            String mobileNo = acc.getMobile().trim();
            mobileNo = StringUtils.right(mobileNo,11);
            GeneratedSMS generatedSMS1 = new GeneratedSMS(acc.getId(), sms, acc.getLoanACNo().trim(), mobileNo.trim(),acc.getDealReference());
            generatedSMS.add(generatedSMS1);
        }
        String status = sendSmsToCustomerService.sendBulksms(generatedSMS,"emi");

        return status;
    }


    @Scheduled(cron = "0 30 11 * * *")
    //@Scheduled(cron = "0 5 10 * * *")
    @GetMapping("/sendAllSmsAfterEmi")
    public String autoSmsAfterEmiDateWise() {

        SchedulerInformationEntity accountInformation = schedulerInformationRepository.findBySchedulerNameAndStatus("SMS", 1);

        if(accountInformation == null)
            return "OK";


        List<AccountInformationEntity> accountInformationEntities = accountInformationRepository.getAllBySmsSentDatePlusTwo();
        List<GeneratedSMS> generatedSMS = new ArrayList<>();
        for (AccountInformationEntity acc : accountInformationEntities) {
            String sms = "Pls, adjust your unpaid installment of BDT{{F.installmentAmount}} against IFIC Aamar Bari loan within next 03 days to" +
                    " keep the loan regular. Pls, ignore if  already  paid." ;


            //sms = sms.replace("{{F.accountNo}}", acc.getLoanACNo());
            if(acc.getEmiAmount() != null)
                sms = sms.replace("{{F.installmentAmount}}",acc.getEmiAmount());
            else sms = sms.replace("{{F.installmentAmount}}","0");
//            sms = sms.replace("{{F.nextEmiDate}}", acc.getNextEMIDate());
//            sms = sms.replace("{{F.currentMonth}}", new SimpleDateFormat("MMM").format(new Date()));
//            sms = sms.replace("{{F.productName}}", acc.getProductName().trim());
            //TODO change phone number here use acc.getMobile()
            String mobileNo = acc.getMobile().trim();
            mobileNo = StringUtils.right(mobileNo,11);
            GeneratedSMS generatedSMS1 = new GeneratedSMS(acc.getId(), sms, acc.getLoanACNo().trim(), mobileNo.trim(),acc.getDealReference());
            generatedSMS.add(generatedSMS1);
        }
        String status = sendSmsToCustomerService.sendBulksms(generatedSMS,"after emi");

        return status;
    }







    public void toExcel(String type) throws IOException {
        if(type.equalsIgnoreCase("delinquent"))
            accountInformationService.writeDelinquentExcel();
        else
            accountInformationService.writeExcel();
    }

    public void deleteFile() throws IOException {
        File file = new File("src/main/resources/generatedExcel/");

        FileUtils.cleanDirectory(file);
    }

    @GetMapping("/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {

        toExcel("unallocated");
        String fileName = "UnAllocated_Account_List.xlsx";
        //File file = new File("src/main/resources/generatedExcel");
        File file = new File(excelServerPath);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try {
            FileInputStream fileInputStream = new FileInputStream(file + "/" + fileName);
            int i;
            while ((i = fileInputStream.read()) != -1) {
                response.getWriter().write(i);
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/delinquent-download")
    public void downloadDelinquentExcel(HttpServletResponse response) throws IOException {

        toExcel("delinquent");
        String fileName = "Delinquent_Account_List.xlsx";
        File file = new File(excelServerPath);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try {
            FileInputStream fileInputStream = new FileInputStream(file + "/" + fileName);
            int i;
            while ((i = fileInputStream.read()) != -1) {
                response.getWriter().write(i);
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
