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
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
                                                           @RequestParam(value = "smsType") Long smsType, Model model){

        Map<String, Object> map = new HashMap<>();
        String sms = "";

        List<GeneratedSMS> generatedSMS = new ArrayList<>();

        SMSEntity smsEntity = smsService.findSmsById(smsType);
        TemplateGenerate templateGenerate = templateGenerateRepository.findTemGenBySmsTypeId(smsType);

        for(String acc : loanViewModelForSMS){
            String[] content = acc.split(":");
            sms = templateGenerate.getMassege();
            sms = sms.replace("{{F.accountNo}}",content[0]);
            sms = sms.replace("{{F.installmentAmount}}",content[3]);
            sms = sms.replace("{{F.nextEmiDate}}",content[4]);
            sms = sms.replace("{{F.currentMonth}}",content[5]);
            sms = sms.replace("{{F.productName}}",content[2]);
            GeneratedSMS generatedSMS1 = new GeneratedSMS(Long.valueOf(content[8]),smsEntity,sms,content[0],content[1]);
            generatedSMS.add(generatedSMS1);
        }

        String status = sendSmsToCustomerService.sendBulksms(generatedSMS);
        map.put("state",status);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Scheduled(cron = "0 30 7 * * *")
    @GetMapping("/sendAllSms")
    public String autoSmsEmiDateWise(){
        String smsType = "";
        String sms = "Your {{F.productName}} EMI due date is {{F.nextEmiDate}}. " +
                "Pls, deposit BDT{{F.installmentAmount}} to keep the loan regular. " +
                "Pls, ignore if it is already paid.";

        List<AccountInformationEntity> accountInformationEntities = accountInformationRepository.findAllByEmiDatePlusThree();

        List<GeneratedSMS> generatedSMS = new ArrayList<>();
        for(AccountInformationEntity acc : accountInformationEntities){
            //sms = templateGenerate.getMassege();
            if(acc.getNextEMIDate() != null){
                sms = sms.replace("{{F.accountNo}}",acc.getLoanACNo());
                sms = sms.replace("{{F.installmentAmount}}",acc.getEmiAmount());
                sms = sms.replace("{{F.nextEmiDate}}",acc.getNextEMIDate());
                sms = sms.replace("{{F.currentMonth}}",new SimpleDateFormat("MMM").format(new Date()));
                sms = sms.replace("{{F.productName}}",acc.getProductName().trim());
                GeneratedSMS generatedSMS1 = new GeneratedSMS(acc.getId(),sms,acc.getLoanACNo(),"01750734960");
                generatedSMS.add(generatedSMS1);
            }
        }
        String status = sendSmsToCustomerService.sendBulksms(generatedSMS);

        return status;
    }

    public void toExcel() throws IOException {
        accountInformationService.writeExcel();
    }

    public void deleteFile() throws IOException {
        File file = new File("src/main/resources/generatedExcel/");

        FileUtils.cleanDirectory(file);
    }

    @GetMapping("/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        toExcel();
        String fileName = "UnAllocated_Account_List.xlsx";
        File file = new File("src/main/resources/generatedExcel");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+ fileName);

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
