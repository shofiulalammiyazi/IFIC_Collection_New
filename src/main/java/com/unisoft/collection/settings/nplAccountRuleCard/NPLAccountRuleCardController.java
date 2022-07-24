package com.unisoft.collection.settings.nplAccountRuleCard;
/*
Created by   Islam on 7/14/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.user.UserPrincipal;
import org.hibernate.Hibernate;
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
@RequestMapping(value = "/collection/npl_acc_rule_card/")
public class NPLAccountRuleCardController {

    @Autowired
    private NPLAccountRuleCardService nplAccountRuleCardService;

    @Autowired
    private AgeCodeService ageCodeService;
    
    @Autowired
    private ProductTypeService productTypeService;
    
    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String list(Model model){
        model.addAttribute("nplRuleList",nplAccountRuleCardService.allActive());
        return "collection/settings/nplAccountRuleCard/list";
    }
    
    @GetMapping(value = "create")
    public String create(Model model){
        List<String> allowedAgeCodeIdList=new ArrayList<>();
        List<String> allowedProdIdList=new ArrayList<>();
        allowedAgeCodeIdList.add("");
        allowedProdIdList.add("");
        model.addAttribute("allowedAgeCodeIdList",allowedAgeCodeIdList);
        model.addAttribute("allowedProdIdList",allowedProdIdList);
        model.addAttribute("ageCodeList",ageCodeService.getActiveList());
        model.addAttribute("prodTypeList",productTypeService.getAllActive());
        model.addAttribute("nplRuleCard",new NPLAccountRuleCardEntity());
        return "collection/settings/nplAccountRuleCard/create";
    }
    
    @PostMapping(value = "create")
    public String create(NPLAccountRuleCardEntity nplAccountRuleCardEntity){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(nplAccountRuleCardEntity.getId()==null){
            nplAccountRuleCardEntity.setCreatedBy(String.valueOf(user.getId()));
            nplAccountRuleCardEntity.setCreatedDate(new Date());
            auditTrailService.saveCreatedData("NPL Account Rules - Card", nplAccountRuleCardEntity);
        }else {
            nplAccountRuleCardEntity.setModifiedBy(String.valueOf(user.getId()));
            nplAccountRuleCardEntity.setModifiedDate(new Date());
            NPLAccountRuleCardEntity previousEntity = nplAccountRuleCardService.findById(nplAccountRuleCardEntity.getId());
            auditTrailService.saveUpdatedData("NPL Account Rules - Card", Hibernate.unproxy(previousEntity), nplAccountRuleCardEntity);
        }
        nplAccountRuleCardService.save(nplAccountRuleCardEntity);
        return "redirect:/collection/npl_acc_rule_card/list";
    }
    
    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model)
    {
        NPLAccountRuleCardEntity nplAccountRuleCardEntity=nplAccountRuleCardService.findById(id);
        
        List<String> allowedAgeCodeIdList=new ArrayList<>();
        List<String> allowedProdIdList=new ArrayList<>();
        
        //System.err.println("nplAccountRule "+nplAccountRule);
        int BucketListSize = nplAccountRuleCardEntity.getAgeCodeList().size();
        for(int i=0;i < BucketListSize;i++)
        {
            allowedAgeCodeIdList.add(nplAccountRuleCardEntity.getAgeCodeList().get(i).getId().toString());
        }
        
        for(ProductTypeEntity productType : nplAccountRuleCardEntity.getProductTypeList())
        {
            allowedProdIdList.add(productType.getId().toString());
        }
        
        model.addAttribute("ageCodeList",ageCodeService.getActiveList());
        model.addAttribute("prodTypeList",productTypeService.getAllActive());
        
        model.addAttribute("allowedAgeCodeIdList",allowedAgeCodeIdList);
        model.addAttribute("allowedProdIdList",allowedProdIdList);
        model.addAttribute("nplRuleCard",nplAccountRuleCardEntity);
        return "collection/settings/nplAccountRuleCard/create";
    }
}
