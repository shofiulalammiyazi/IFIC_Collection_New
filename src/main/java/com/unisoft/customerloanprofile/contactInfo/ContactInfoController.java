package com.unisoft.customerloanprofile.contactInfo;

import com.unisoft.collection.settings.SMS.generate.GeneratedSMS;
import com.unisoft.collection.settings.SMS.sendSms.SendSmsToCustomerService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/customerLoanProfile/ContactInfo")
public class ContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private SendSmsToCustomerService sendSmsToCustomerService;


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


    @GetMapping(value = "/list")
    @ResponseBody
    public List<ContactInfo> viewContactInfoList(@RequestParam(value = "id") String id) {
        List<ContactInfo> contactInfos = contactInfoService.getContactInfoList(new Long(id));
        return contactInfos;
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public List<ContactInfo> saveContactInfo(ContactInfo contactInfo) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        contactInfo.setCreatedBy(user.getFirstName());
        contactInfo.setPin(user.getUsername());
        contactInfo.setCreatedDate(new Date());

        String sms = "";
        //if contact fail based on consideration as attempt = Call Not Received
        if(!contactInfo.getAttempt().equalsIgnoreCase("Call Received")) {
            sms = "We have tried and failed to reach you over phone." +
                    " Pls, pay the unpaid installment BDT {{F.installmentAmount}} against " +
                    "{{F.productName}} by " + getNextOrPreviousDate(new Date(), 3) + " to avoid penal charge.";

            AccountInformationEntity acc = accountInformationRepository.getByLoanAccountNo(contactInfo.getAccNo());

            List<GeneratedSMS> generatedSMS = new ArrayList<>();
            if (acc.getNextEMIDate() != null) {
                sms = sms.replace("{{F.accountNo}}", acc.getLoanACNo());
                sms = sms.replace("{{F.installmentAmount}}", acc.getOverdue());
                sms = sms.replace("{{F.nextEmiDate}}", acc.getNextEMIDate());
                sms = sms.replace("{{F.currentMonth}}", new SimpleDateFormat("MMM").format(new Date()));
                sms = sms.replace("{{F.productName}}", acc.getProductName().trim());
                //TODO change phone number here use acc.getMobile()
                GeneratedSMS generatedSMS1 = new GeneratedSMS(acc.getId(), sms, acc.getLoanACNo(), "01950886895");
                generatedSMS.add(generatedSMS1);
                String status = sendSmsToCustomerService.sendBulksms(generatedSMS);
            }
        }
        contactInfoService.saveContactInfo(contactInfo);
        return contactInfoService.getContactInfoList(contactInfo.getCustomerBasicInfo().getId());
    }


    @GetMapping("/attempt-unattempt-call/list")
    @ResponseBody
    public List<ContactInfoDto> getAttemptAndUnattemptContact(String designation) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();

        if (designation.replaceAll(" ", "").toLowerCase().equals("supervisor") ){
            contactInfoDtoList = contactInfoService.findCurrentMonthContactInfoBySupervisorPin(user.getUsername());

        }
        else if (designation.replaceAll(" ", "").toLowerCase().equals("manager")){
            contactInfoDtoList =  contactInfoService.findCurrentMonthContactInfoByManagerPin(user.getUsername());
        }
        else if (designation.replaceAll(" ", "").toLowerCase().equals("dealer")){
            contactInfoDtoList =  contactInfoService.findCurrentMonthContactInfoByDealerPin(user.getUsername());
        }
        else if (designation.replaceAll(" ", "").toLowerCase().equals("teamleader")){
            contactInfoDtoList =  contactInfoService.findCurrentMonthContactInfoByTeamleadPin(user.getUsername());
        }

       return contactInfoDtoList;

    }

    @GetMapping("/attempt-unattempt-card-call/list")
    @ResponseBody
    public List<ContactInfoDto> getAttemptAndUnattemptCardContact(String designation) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();

        if (designation.replaceAll(" ", "").toLowerCase().equals("supervisor") ){
            contactInfoDtoList = contactInfoService.findCurrentMonthContactInfoBySupervisorPin(user.getUsername());

        }
        else if (designation.replaceAll(" ", "").toLowerCase().equals("manager")){
            contactInfoDtoList =  contactInfoService.findCurrentMonthContactInfoByManagerPin(user.getUsername());
        }
        else if (designation.replaceAll(" ", "").toLowerCase().equals("dealer")){
            contactInfoDtoList =  contactInfoService.findCurrentMonthContactInfoByCardDealerPin(user.getUsername());
        }
        else if (designation.replaceAll(" ", "").toLowerCase().equals("teamleader")){
            contactInfoDtoList =  contactInfoService.findCardCurrentMonthContactInfoByTeamleadPin(user.getUsername());//findCurrentMonthContactInfoByTeamleadPin(user.getUsername());
        }

        return contactInfoDtoList;

    }


    @GetMapping("/attempt-call-list")
    @ResponseBody
    public List<ContactInfo> findAttemptCallList(@RequestParam Long customerId) {
        return contactInfoService.findAttemptCallListByCustomerId(customerId);
    }

    @GetMapping("/unattempt-call-list")
    @ResponseBody
    public List<ContactInfo> findUnAttemptCallList(@RequestParam Long customerId) {
        return contactInfoService.findUnAttemptCallListByCustomerId(customerId);
    }

    @GetMapping(value="/ById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "id", required = true) String id ) throws IOException {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        Optional<ContactInfo> ci =contactInfoRepository.findById(Long.parseLong(id));
        resultMap.put("ci", ci);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
}
