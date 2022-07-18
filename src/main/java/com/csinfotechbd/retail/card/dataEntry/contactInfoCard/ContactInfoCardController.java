package com.csinfotechbd.retail.card.dataEntry.contactInfoCard;

import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customerCardProfile/contactInfoCard")
public class ContactInfoCardController {

    @Autowired
    private ContactInfoCardService contactInfoCardService;

    @GetMapping(value = "/list")
    @ResponseBody
    public List<ContactInfoCard> getList(@RequestParam(value = "id")String id){
        List<ContactInfoCard> contactInfoCardList = contactInfoCardService.getContactInfoCardList(new Long(id));
        return contactInfoCardList;
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public List<ContactInfoCard> saveContactInfo(ContactInfoCard contactInfoCard) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        contactInfoCard.setCreatedBy(user.getFirstName());
        contactInfoCard.setPin(user.getUsername());
        contactInfoCard.setCreatedDate(new Date());
        contactInfoCardService.saveContactInfoCard(contactInfoCard);
        return contactInfoCardService.getContactInfoCardList(contactInfoCard.getCustomerBasicInfo().getId());
    }

    @GetMapping(value = "/attempt-unattempt-call/List")
    @ResponseBody
    public List<ContactInfoCardDto> getAttemptUnattemptContact(){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return contactInfoCardService.findCurrentMonthContactInfoByDealerPin(user.getUsername());
    }

    @GetMapping(value = "/attempt-call-list")
    @ResponseBody
    public List<ContactInfoCard> findAttemptCallList(@RequestParam Long customerId) {
        return contactInfoCardService.findAttemptCallListByCustomerId(customerId);
    }

    @GetMapping(value = "/unattempt-call-list")
    @ResponseBody
    public List<ContactInfoCard> findUnAttemptCallList(@RequestParam Long customerId) {
        return contactInfoCardService.findUnAttemptCallListByCustomerId(customerId);
    }


}
