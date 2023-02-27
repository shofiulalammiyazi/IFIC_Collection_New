package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import com.google.gson.Gson;
import com.unisoft.collection.settings.loanStatus.LoanStatusEntity;
import com.unisoft.collection.settings.loanStatus.LoanStatusRepository;
import com.unisoft.collection.settings.loanType.LoanTypeEntity;
import com.unisoft.collection.settings.loanType.LoanTypeRepository;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/collection/settings/smsandautodistributionrules/")
public class SmsAndAutoDistributionRulesController {

    @Autowired
    private SmsAndAutoDistributionRulesRepository smsAndAutoDistributionRulesRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Autowired
    private SmsAndAutoDistributionRulesService smsAndAutoDistributionRulesService;


    @GetMapping("create")
    public  String save(Model model){
        Gson gson = new Gson();
        SmsAndAutoDistributionRulesEntity smsAndAutoDistributionRulesEntity = new SmsAndAutoDistributionRulesEntity();

        model.addAttribute("smsAndAutoDistributionRules", smsAndAutoDistributionRulesEntity);


        model.addAttribute("ruleType",smsAndAutoDistributionRulesEntity.getType());
        model.addAttribute("loanStatusList",gson.toJson(loanStatusRepository.findAll()));
        model.addAttribute("loanTypeList",gson.toJson(loanTypeRepository.findAll()));
        model.addAttribute("selectedLoanStatus",gson.toJson(smsAndAutoDistributionRulesEntity.getLoanStatusEntity()));
        model.addAttribute("selectedLoanTypes",gson.toJson(smsAndAutoDistributionRulesEntity.getLoanTypeEntities()));


        return "collection/settings/smsAndAutoDistributionRules/create";
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

            SmsAndAutoDistributionRulesEntity smsAndAutoDistributionRulesEntity1 = smsAndAutoDistributionRulesService.getByType(smsAndAutoDistributionRulesEntity.getType());

            for(String id: smsAndAutoDistributionRulesEntity.getLoanStatusIds()){
                LoanStatusEntity loanStatusEntity = loanStatusRepository.findByLoanStatusId(new Long(id));
                loanStatusEntities.add(loanStatusEntity);
            }

            for(String id: smsAndAutoDistributionRulesEntity.getLoanTypeIds()){
                LoanTypeEntity loanTypeEntity = loanTypeRepository.findByLoanTypeId(new Long(id));
                loanTypeEntities.add(loanTypeEntity);
            }

            smsAndAutoDistributionRulesEntity1.setLoanStatusEntity(loanStatusEntities);
            smsAndAutoDistributionRulesEntity1.setLoanTypeEntities(loanTypeEntities);
            smsAndAutoDistributionRulesEntity1.setNoOfDaysBeforeEmiDate(smsAndAutoDistributionRulesEntity.getNoOfDaysBeforeEmiDate());
            smsAndAutoDistributionRulesEntity1.setUnpaidInstallmentNumber(smsAndAutoDistributionRulesEntity.getUnpaidInstallmentNumber());
            smsAndAutoDistributionRulesEntity1.setModifiedBy(user.getUsername());
            smsAndAutoDistributionRulesEntity1.setModifiedDate(new Date());
            smsAndAutoDistributionRulesRepository.save(smsAndAutoDistributionRulesEntity1);
        }
        return "redirect:/collection/settings/smsandautodistributionrules/list";

    }


    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("SMSDistributionList",smsAndAutoDistributionRulesService.SmsAndAutoDistributionfindAll());
        model.addAttribute("size",smsAndAutoDistributionRulesService.SmsAndAutoDistributionfindAll().size());
        return "collection/settings/smsAndAutoDistributionRules/list";
    }


    @GetMapping(value = "edit")
    public String editPage(Model model, @RequestParam(value = "id") Long id) {
        Gson gson = new Gson();

        SmsAndAutoDistributionRulesEntity smsAndAutoDistributionRulesEntity = smsAndAutoDistributionRulesService.getById(id);

        model.addAttribute("smsAndAutoDistributionRules", smsAndAutoDistributionRulesEntity);
        model.addAttribute("ruleType",smsAndAutoDistributionRulesEntity.getType());
        model.addAttribute("loanStatusList",gson.toJson(loanStatusRepository.findAll()));
        model.addAttribute("loanTypeList",gson.toJson(loanTypeRepository.findAll()));
        model.addAttribute("selectedLoanStatus",gson.toJson(smsAndAutoDistributionRulesEntity.getLoanStatusEntity()));
        model.addAttribute("selectedLoanTypes",gson.toJson(smsAndAutoDistributionRulesEntity.getLoanTypeEntities()));

        return "collection/settings/smsandautodistributionrules/edit";
    }

    @GetMapping("getByType")
    @ResponseBody
    public SmsAndAutoDistributionRulesEntity getByType(@RequestParam("type") String type){

        return smsAndAutoDistributionRulesService.getByType(type);
    }

}

