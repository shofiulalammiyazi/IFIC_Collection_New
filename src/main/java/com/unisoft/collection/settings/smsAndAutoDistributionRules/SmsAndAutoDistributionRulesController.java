package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import com.google.gson.Gson;
import com.unisoft.collection.settings.loanStatus.LoanStatusEntity;
import com.unisoft.collection.settings.loanStatus.LoanStatusRepository;
import com.unisoft.collection.settings.loanType.LoanTypeEntity;
import com.unisoft.collection.settings.loanType.LoanTypeRepository;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/collection/settings/smsandautodistributionrules/")
public class SmsAndAutoDistributionRulesController {

    @Autowired
    private SmsAndAutoDistributionRulesRepository smsAndAutoDistributionRulesRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    @Autowired
    private LoanTypeRepository loanTypeRepository;


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
        model.addAttribute("loanTypeList",gson.toJson(loanTypeRepository.findAll()));
        model.addAttribute("selectedLoanStatus",gson.toJson(smsAndAutoDistributionRulesEntity.getLoanStatusEntity()));
        model.addAttribute("selectedLoanTypes",gson.toJson(smsAndAutoDistributionRulesEntity.getLoanTypeEntities()));

        return "collection/settings/smsandautodistributionrules/create";
    }


    @PostMapping("/create")
    public String save(SmsAndAutoDistributionRulesEntity smsAndAutoDistributionRulesEntity, Model model){


        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<LoanStatusEntity> loanStatusEntities = new ArrayList<>();
        List<LoanTypeEntity> loanTypeEntities = new ArrayList<>();
        if(smsAndAutoDistributionRulesEntity.getId() == null){
            for(String id: smsAndAutoDistributionRulesEntity.getLoanStatusIds()){
                LoanStatusEntity loanStatusEntity = loanStatusRepository.findByLoanStatusId(new Long(id));
                loanStatusEntities.add(loanStatusEntity);
            }

            for(String id: smsAndAutoDistributionRulesEntity.getLoanTypeIds()){
                LoanTypeEntity loanTypeEntity = loanTypeRepository.findByLoanTypeId(new Long(id));
                loanTypeEntities.add(loanTypeEntity);
            }

            smsAndAutoDistributionRulesEntity.setLoanStatusEntity(loanStatusEntities);
            smsAndAutoDistributionRulesEntity.setLoanTypeEntities(loanTypeEntities);
            smsAndAutoDistributionRulesEntity.setCreatedBy(user.getUsername());
            smsAndAutoDistributionRulesEntity.setCreatedDate(new Date());
            smsAndAutoDistributionRulesRepository.save(smsAndAutoDistributionRulesEntity);
        }
        else{
            //SmsAndAutoDistributionRulesEntity oldEntity = smsAndAutoDistributionRulesRepository.getOne(smsAndAutoDistributionRulesEntity.getId());
            //SmsAndAutoDistributionRulesEntity previousEntity = new SmsAndAutoDistributionRulesEntity();
            //BeanUtils.copyProperties(oldEntity, previousEntity);

            for(String id: smsAndAutoDistributionRulesEntity.getLoanStatusIds()){
                LoanStatusEntity loanStatusEntity = loanStatusRepository.findByLoanStatusId(new Long(id));
                loanStatusEntities.add(loanStatusEntity);
            }

            for(String id: smsAndAutoDistributionRulesEntity.getLoanTypeIds()){
                LoanTypeEntity loanTypeEntity = loanTypeRepository.findByLoanTypeId(new Long(id));
                loanTypeEntities.add(loanTypeEntity);
            }

            smsAndAutoDistributionRulesEntity.setLoanStatusEntity(loanStatusEntities);
            smsAndAutoDistributionRulesEntity.setLoanTypeEntities(loanTypeEntities);
            smsAndAutoDistributionRulesEntity.setModifiedBy(user.getUsername());
            smsAndAutoDistributionRulesEntity.setModifiedDate(new Date());
            smsAndAutoDistributionRulesRepository.save(smsAndAutoDistributionRulesEntity);
        }
        return "redirect:/collection/settings/smsandautodistributionrules/create";

    }




}

