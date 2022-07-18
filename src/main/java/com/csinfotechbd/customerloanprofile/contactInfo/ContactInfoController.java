package com.csinfotechbd.customerloanprofile.contactInfo;

import com.csinfotechbd.retail.card.dataEntry.hotnotes.CardHotNotes;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/customerLoanProfile/ContactInfo")
public class ContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

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
