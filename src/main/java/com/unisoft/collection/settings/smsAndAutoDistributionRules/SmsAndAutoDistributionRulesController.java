package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import com.google.gson.Gson;
import com.unisoft.collection.settings.loanStatus.LoanStatusRepository;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/collection/settings/smsandautodistributionrules/")
public class SmsAndAutoDistributionRulesController {

    @Autowired
    private SmsAndAutoDistributionRulesRepository smsAndAutoDistributionRulesRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;


    @GetMapping("create")
    public  String save(Model model){
        Gson gson = new Gson();
        SmsAndAutoDistributionRulesEntity smsAndAutoDistributionRulesEntity = new SmsAndAutoDistributionRulesEntity();

        List<SmsAndAutoDistributionRulesEntity> smsAndAutoDistributionRulesEntities = smsAndAutoDistributionRulesRepository.findAll();

        if (smsAndAutoDistributionRulesEntities.size() >0){
            smsAndAutoDistributionRulesEntity = smsAndAutoDistributionRulesEntities.get(0);
        }
         model.addAttribute("smsAndAutoDistributionRules", smsAndAutoDistributionRulesEntity);

        if (smsAndAutoDistributionRulesEntity.getId() !=null){
            model.addAttribute("smsAndAutoDistributionRules", smsAndAutoDistributionRulesEntity);
        }

        model.addAttribute("loanStatusList",gson.toJson(loanStatusRepository.findAll()));

        return "collection/settings/smsandautodistributionrules/create";
    }


    @PostMapping("/create")
    public String save(SmsAndAutoDistributionRulesEntity smsAndAutoDistributionRulesEntity, Model model){


        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(smsAndAutoDistributionRulesEntity.getId() == null){
            smsAndAutoDistributionRulesEntity.setCreatedBy(user.getUsername());
            smsAndAutoDistributionRulesEntity.setCreatedDate(new Date());
            smsAndAutoDistributionRulesRepository.save(smsAndAutoDistributionRulesEntity);
        }
        else{
            SmsAndAutoDistributionRulesEntity oldEntity = smsAndAutoDistributionRulesRepository.getOne(smsAndAutoDistributionRulesEntity.getId());
            SmsAndAutoDistributionRulesEntity previousEntity = new SmsAndAutoDistributionRulesEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            smsAndAutoDistributionRulesEntity.setCreatedBy(oldEntity.getCreatedBy());
            smsAndAutoDistributionRulesEntity.setCreatedDate(oldEntity.getCreatedDate());
            smsAndAutoDistributionRulesEntity.setModifiedBy(user.getUsername());
            smsAndAutoDistributionRulesEntity.setModifiedDate(new Date());
            smsAndAutoDistributionRulesRepository.save(smsAndAutoDistributionRulesEntity);
        }
        return "redirect:/collection/settings/smsandautodistributionrules/create";

    }




}

