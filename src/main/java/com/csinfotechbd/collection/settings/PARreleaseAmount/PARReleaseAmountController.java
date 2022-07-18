package com.csinfotechbd.collection.settings.PARreleaseAmount;
/*
Created by Monirul Islam on 7/9/2019 
*/

import com.csinfotechbd.collection.settings.PARreleaseAmount.PARReleaseAmountEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.user.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(value = "/collection/par_release_amount/")
public class PARReleaseAmountController {

    private final PARReleaseAmountService paRreleaseAmountService;
    private final AgeCodeService ageCodeService;
    private final UserService userService;

    @GetMapping(value = "create")
    public String viewAddPage(Model model)
    {
        PARReleaseAmountEntity paRreleaseAmount=paRreleaseAmountService.getPAR();

        List<String> allowedAgeCodeIdList=new ArrayList<>();
        model.addAttribute("ageList",ageCodeService.getActiveList());

        String modifier="";
        System.err.println("paRreleaseAmount :"+paRreleaseAmount);
        if(paRreleaseAmount == null)
        {
            model.addAttribute("parAmount",new PARReleaseAmountEntity());
        }else {
            model.addAttribute("parAmount",paRreleaseAmount);
            if(userService.findById(Long.parseLong(paRreleaseAmount.getModifiedBy())).getEmployeeId().length()>0)
                modifier=userService.findById(Long.parseLong(paRreleaseAmount.getModifiedBy())).getEmployeeId();
            int sz = paRreleaseAmount.getAgeCodeList().size();
            for(int i=0;i < sz;i++)
            {
                allowedAgeCodeIdList.add(paRreleaseAmount.getAgeCodeList().get(i).getId().toString());
            }
        }
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.err.println("allowedAgeCode :"+allowedAgeCodeIdList);
        model.addAttribute("modifiedBy",modifier);
        model.addAttribute("allowedAgeCode",allowedAgeCodeIdList);
        //model.addAttribute("modifiedBy",user.getEmpId());
        return "collection/settings/PARreleaseAmount/create";
    }

    @PostMapping(value = "create")
    public String addOrUpdate(PARReleaseAmountEntity paRreleaseAmount, @RequestParam(value = "ageCodeId") List<Long> ageCodeIdList)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<AgeCodeEntity> ageCodeList=new ArrayList<>();

        for(int i=0;ageCodeIdList.size()>i;i++)
        {
            AgeCodeEntity codeEntity=new AgeCodeEntity();
            codeEntity.setId(ageCodeIdList.get(i));
            ageCodeList.add(codeEntity);
        }
        paRreleaseAmount.setAgeCodeList(ageCodeList);

        System.err.println("PARReleaseAmountEntity :\n"+paRreleaseAmount);
        if(paRreleaseAmount.getId()==null)
        {
            paRreleaseAmount.setCreatedBy(user.getUsername());
            paRreleaseAmount.setCreatedDate(new Date());
            paRreleaseAmount.setModifiedBy(user.getUsername());
            paRreleaseAmount.setModifiedDate(new Date());
            boolean save=paRreleaseAmountService.savePar(paRreleaseAmount);
        }else{
            paRreleaseAmount.setModifiedBy(user.getUsername());
            paRreleaseAmount.setModifiedDate(new Date());
            boolean update=paRreleaseAmountService.updatePAR(paRreleaseAmount);
        }

        return "redirect:/collection/par_release_amount/create";
    }
}
