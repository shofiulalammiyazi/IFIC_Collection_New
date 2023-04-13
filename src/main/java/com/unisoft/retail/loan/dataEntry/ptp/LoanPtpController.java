package com.unisoft.retail.loan.dataEntry.ptp;

import com.unisoft.collection.settings.SMS.generate.GeneratedSMS;
import com.unisoft.collection.settings.SMS.sendSms.SendSmsToCustomerService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.schedulermonitoringstatus.SchedulerMonitoringStatus;
import com.unisoft.schedulermonitoringstatus.SchedulerMonitoringStatusService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/collection/loan/ptp")
public class LoanPtpController {
    @Autowired
    LoanPtpService service;
    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    private DateUtils dateUtils;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private SendSmsToCustomerService sendSmsToCustomerService;

    @Autowired
    private SchedulerMonitoringStatusService schedulerMonitoringStatusService;


    private static Date getNextOrPreviousDate(Date date, int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayIndex);
        return getFormattedDate(calendar.getTime(), "dd-MM-yyyy");
    }

    private static Date getFormattedDate(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @PostMapping(value="/save")
    public boolean saveReferenceInfo(LoanPtp loanPtp) {
        String sms = "";
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loanPtp.setCreatedBy(user.getUsername());
        loanPtp.setCreatedDate(new Date());
        loanPtp.setEnabled(true);
        loanPtp.setPin(user.getUsername());
        loanPtp.setUsername(user.getLastName());

        service.save(loanPtp);

        //SchedulerMonitoringStatus schedulerMonitoringStatus = new SchedulerMonitoringStatus();

        //for contact success based on consideration as attempt = Call Received
//        sms = "Your unpaid installment is BDT"+loanPtp.getLoan_amount()+" against {{F.productName}}. " +
//              "Pls, repay the amount within " +new SimpleDateFormat("dd-MMM-yyyy").format(loanPtp.getLoan_ptp_date())
//                +" as per your commitment to keep the loan regular.";



        sms = "Your unpaid installment is BDT"+loanPtp.getLoan_amount()+" against IFIC Aamar Bari loan. " +
                "Pls, repay the amount within " +new SimpleDateFormat("dd-MMM-yyyy").format(loanPtp.getLoan_ptp_date())
                +" as per your commitment to keep the loan regular.";

        //Your unpaid installment is BDT999,999.00 against IFIC Aamar Bari loan. Pls, repay the amount within 18/02/23 as per your commitment to keep the loan regular. (154)



        AccountInformationEntity acc = accountInformationRepository.getByLoanAccountNo(loanPtp.getAccNo());

        List<GeneratedSMS> generatedSMS = new ArrayList<>();
        if(acc.getNextEMIDate() != null){
            sms = sms.replace("{{F.accountNo}}",acc.getLoanACNo());
            sms = sms.replace("{{F.installmentAmount}}",acc.getOverdue());
            sms = sms.replace("{{F.nextEmiDate}}", acc.getNextEMIDate());
            sms = sms.replace("{{F.currentMonth}}",new SimpleDateFormat("MMM").format(new Date()));
            sms = sms.replace("{{F.productName}}",acc.getProductName().trim());
            //TODO change phone number here use acc.getMobile()
            //GeneratedSMS generatedSMS1 = new GeneratedSMS(acc.getId(),sms,acc.getLoanACNo(),"01950886895");
            GeneratedSMS generatedSMS1 = new GeneratedSMS(acc.getId(),sms,acc.getLoanACNo(),acc.getMobile(),acc.getDealReference());
            generatedSMS.add(generatedSMS1);
            // String status = sendSmsToCustomerService.sendBulksms(generatedSMS,"ptp");

//            schedulerMonitoringStatus.setExecutionDate(new Date());
//            schedulerMonitoringStatus.setSchedulerName("ptp");
//            schedulerMonitoringStatus.setStatus("Success");
//            schedulerMonitoringStatusService.saveCreatedData(schedulerMonitoringStatus);
        }

        return true;
    }

    @GetMapping(value="/list")
    public List<LoanPtp> list(@RequestParam(value = "id") String id) throws IOException{
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
        List<LoanPtp> ptpList =service.list(true, customerBasicInfoEntity);
        return ptpList;

    }
    @GetMapping(value="/listById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "loanPtpId", required = true) String loanPtpId ) throws IOException{
        UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> resultMap=new HashMap<String,Object>();
        LoanPtp loanPtplist =service.findbyId(Long.parseLong(loanPtpId));
        resultMap.put("loanptpList", loanPtplist);
        return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.OK);

    }

    @GetMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam(value = "id", required = true) String id){
        Map<String, Object> resultMap=new HashMap<>();
        LoanPtp dairyNotes =service.findbyId(Long.parseLong(id));
        dairyNotes.setEnabled(false);
        service.save(dairyNotes);
        resultMap.put("successMsg","Successful deleted");
        resultMap.put("dairyNotes", dairyNotes);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }


    @GetMapping("/ptp-list")
    @ResponseBody
    public List<LoanPtp> getLoanPtpByCustomerIdAndDateRange(@RequestParam(name = "customerId") String customerId,@RequestParam(name = "startDate") String startDate,
                                                            @RequestParam(name = "endDate") String endDate){
        List<LoanPtp> loanPtpList = service.getLoanPtpByCustomerIdAndDateRange(customerId, startDate, endDate);
        return loanPtpList;
    }

}
