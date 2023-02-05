package com.unisoft.collection.settings.smsAndAutoDistributionRules;

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


    @GetMapping("create")
    public  String save(Model model){
        SmsAndAutoDistributionRulesEntity unpaidInstallmentNumberEntity = new SmsAndAutoDistributionRulesEntity();

        List<SmsAndAutoDistributionRulesEntity> unpaidInstallmentNumberEntities = smsAndAutoDistributionRulesRepository.findAll();

        if (unpaidInstallmentNumberEntities.size() >0){
            unpaidInstallmentNumberEntity = unpaidInstallmentNumberEntities.get(0);
        }
         model.addAttribute("unpaidInstallmentNumber", unpaidInstallmentNumberEntity);

        if (unpaidInstallmentNumberEntity.getId() !=null){
            model.addAttribute("unpaidInstallmentNumber", unpaidInstallmentNumberEntity);
        }
        return "collection/settings/smsandautodistributionrules/create";
    }


    @PostMapping("/create")
    public String save(SmsAndAutoDistributionRulesEntity unpaidInstallmentNumberEntity, Model model){


        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(unpaidInstallmentNumberEntity.getId() == null){
            unpaidInstallmentNumberEntity.setCreatedBy(user.getUsername());
            unpaidInstallmentNumberEntity.setCreatedDate(new Date());
            smsAndAutoDistributionRulesRepository.save(unpaidInstallmentNumberEntity);
        }
        else{
            SmsAndAutoDistributionRulesEntity oldEntity = smsAndAutoDistributionRulesRepository.getOne(unpaidInstallmentNumberEntity.getId());
            SmsAndAutoDistributionRulesEntity previousEntity = new SmsAndAutoDistributionRulesEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            unpaidInstallmentNumberEntity.setCreatedBy(oldEntity.getCreatedBy());
            unpaidInstallmentNumberEntity.setCreatedDate(oldEntity.getCreatedDate());
            unpaidInstallmentNumberEntity.setModifiedBy(user.getUsername());
            unpaidInstallmentNumberEntity.setModifiedDate(new Date());
            smsAndAutoDistributionRulesRepository.save(unpaidInstallmentNumberEntity);
        }
        return "redirect:/collection/settings/smsandautodistributionrules//create";

    }


}

