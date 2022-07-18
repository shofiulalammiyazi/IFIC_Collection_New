package com.csinfotechbd.collection.settings.NPLreleaseAmount;
/*
Created by Monirul Islam on 7/9/2019
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.user.User;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/collection/npl_release_amount/")
public class NPLReleaseAmountController {

    @Autowired
    private  NPLReleaseAmountService npLreleaseAmountService;

    @Autowired
    private AgeCodeService ageCodeService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "create")
    public String viewAddPage(Model model) {
        NPLReleaseAmountEntity nplReleaseAmount = npLreleaseAmountService.getNPL();
        List<String> allowedAgeCodeIdList = new ArrayList<>();

        if (nplReleaseAmount == null) {
            nplReleaseAmount = new NPLReleaseAmountEntity();
            allowedAgeCodeIdList.add("");
        } else {
            User user = userService.getUserByUsername(nplReleaseAmount.getModifiedBy());
            String username = user.getFirstName() + " " + user.getLastName();
            model.addAttribute("username", username);
            model.addAttribute("userId", user.getEmployeeId());
        }

        nplReleaseAmount.getAgeCodeList().forEach(ageCode -> allowedAgeCodeIdList.add(ageCode.getId().toString()));

        model.addAttribute("ageList", ageCodeService.getActiveList());
        model.addAttribute("nplAmount", nplReleaseAmount);
        model.addAttribute("allowedAgeCode", allowedAgeCodeIdList);
        return "collection/settings/NPLreleaseAmount/create";
    }

    @PostMapping(value = "create")
    public String addOrUpdate(NPLReleaseAmountEntity npLreleaseAmount,
                              @RequestParam(value = "ageCodeId") List<Long> ageCodeIdList) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<AgeCodeEntity> ageCodeList = new ArrayList<>();

        for (Long ageCodeId : ageCodeIdList) {
            AgeCodeEntity ageCode = new AgeCodeEntity();
            ageCode.setId(ageCodeId);
            ageCodeList.add(ageCode);
        }

        npLreleaseAmount.setAgeCodeList(ageCodeList);
        npLreleaseAmount.setModifiedBy(user.getUsername());
        npLreleaseAmount.setModifiedDate(new Date());

        if (npLreleaseAmount.getId() == null) {
            npLreleaseAmount.setCreatedBy(user.getUsername());
            npLreleaseAmount.setCreatedDate(new Date());
            npLreleaseAmountService.saveNPL(npLreleaseAmount);
        } else {
            npLreleaseAmountService.updateNPL(npLreleaseAmount);
        }
        return "redirect:/collection/npl_release_amount/create";
    }
}
