package com.csinfotechbd.collection.settings.PARAccountRuleCard;
/*
Created by Monirul Islam on 7/14/2019 
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
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

@Controller("PARController2")
@RequestMapping(value = "/collection/par_logic_setup/")
public class PARAccountRuleCardController {

    @Autowired
    private PARAccountRuleCardService parAccountRuleCardService;

    @Autowired
    private AgeCodeService ageCodeService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "create")
    public String viewAddPage(Model model)
    {
        PARAccountRuleCardEntity parLogicSetUp=new PARAccountRuleCardEntity();
        parLogicSetUp= parAccountRuleCardService.getPar();

        List<String> allowedAgeCodeIdList=new ArrayList<>();
        //System.err.println("paRreleaseAmount :"+npLreleaseAmount);
        String modifier="";
        if(parLogicSetUp == null)
        {
            model.addAttribute("parLogic",new PARAccountRuleCardEntity());
            allowedAgeCodeIdList.add("");
        }else {
            if(userService.findById(Long.parseLong(parLogicSetUp.getModifiedBy())).getEmployeeId().length()>0)
                modifier=userService.findById(Long.parseLong(parLogicSetUp.getModifiedBy())).getEmployeeId();

            for(int i=0;parLogicSetUp.getAgeCodeList().size()>i;i++)
            {
                allowedAgeCodeIdList.add(parLogicSetUp.getAgeCodeList().get(i).getId().toString());
            }
            model.addAttribute("parLogic",parLogicSetUp);
        }
        model.addAttribute("modifiedBy",modifier);
        model.addAttribute("allowedAgeCode",allowedAgeCodeIdList);
        model.addAttribute("ageCodeList",ageCodeService.getActiveList());

        return "collection/settings/parLogicSetUp/create";

    }

    @PostMapping(value = "create")
    public String create(PARAccountRuleCardEntity parLogicSetUp, @RequestParam(value = "ageCodeId") List<Long> ageCodeIdList)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<AgeCodeEntity> ageCodeList=new ArrayList<>();
        int sz = ageCodeIdList.size();
        for(int i=0;i < sz;i++)
        {
            AgeCodeEntity codeEntity=new AgeCodeEntity();
            codeEntity.setId(ageCodeIdList.get(i));
            ageCodeList.add(codeEntity);
        }
        parLogicSetUp.setAgeCodeList(ageCodeList);

        //System.err.println("PARReleaseAmountEntity :\n"+parLogicSetUp);
        if(parLogicSetUp.getId()==null)
        {
            parLogicSetUp.setCreatedBy(user.getUsername());
            parLogicSetUp.setCreatedDate(new Date());
            parLogicSetUp.setModifiedBy(user.getUsername());
            parLogicSetUp.setModifiedDate(new Date());
            boolean save= parAccountRuleCardService.saveNew(parLogicSetUp);
        }else{
            parLogicSetUp.setModifiedBy(user.getUsername());
            parLogicSetUp.setModifiedDate(new Date());
            boolean update= parAccountRuleCardService.updatePAR(parLogicSetUp);
        }
        return "redirect:/collection/par_logic_setup/create";
    }

}
