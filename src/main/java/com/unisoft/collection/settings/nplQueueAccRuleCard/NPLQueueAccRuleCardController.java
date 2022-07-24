package com.unisoft.collection.settings.nplQueueAccRuleCard;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardEntity;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardService;
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
@RequestMapping("/collection/npl_queue_acc_rule_card")
public class NPLQueueAccRuleCardController {
    
    @Autowired
    private NPLQueueAccRuleCardService nplQueueAccRuleCardService;
    
    @Autowired
    private ProductTypeCardService productTypeService;
    
    @Autowired
    private AuditTrailService auditTrailService;
    
    @Autowired
    private AgeCodeService ageCodeService;
    
    @GetMapping(value = "/list")
    public String list(Model model){
        model.addAttribute("nplQueueList",nplQueueAccRuleCardService.getAllActive());
        return "collection/settings/nplQueueAccRuleCard/list";
    }
    
    @GetMapping(value = "/create")
    public String create(Model model){
        List<String> allowedAgeCodeIdList=new ArrayList<>();
        List<String> allowedProdIdList=new ArrayList<>();
        allowedAgeCodeIdList.add("");
        allowedProdIdList.add("");
        model.addAttribute("allowedAgeCodeIdList",allowedAgeCodeIdList);
        model.addAttribute("allowedProdIdList",allowedProdIdList);
        model.addAttribute("ageCodeList",ageCodeService.getActiveList());
        model.addAttribute("prodTypeList",productTypeService.getAllActive());
        model.addAttribute("nplQueue",new NPLQueueAccRuleCardEntity());
        return "collection/settings/nplQueueAccRuleCard/create";
    }
    
    @PostMapping(value = "/save")
    public String save(NPLQueueAccRuleCardEntity nplQueueAccRuleCardEntity){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(nplQueueAccRuleCardEntity.getId() == null){
            nplQueueAccRuleCardEntity.setCreatedBy(String.valueOf(user.getId()));
            nplQueueAccRuleCardEntity.setCreatedDate(new Date());
            auditTrailService.saveCreatedData("NPL Queue Account Rule - Card", nplQueueAccRuleCardEntity);
        }else{
            nplQueueAccRuleCardEntity.setModifiedBy(String.valueOf(user.getId()));
            nplQueueAccRuleCardEntity.setModifiedDate(new Date());
            NPLQueueAccRuleCardEntity previousEntity = nplQueueAccRuleCardService.findById(nplQueueAccRuleCardEntity.getId());
            auditTrailService.saveUpdatedData("NPL Queue Account Rule - Card", Hibernate.unproxy(previousEntity), nplQueueAccRuleCardEntity);
        }
        nplQueueAccRuleCardService.save(nplQueueAccRuleCardEntity);
        return "redirect:/collection/npl_queue_acc_rule_card/list";
    }
    
    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model)
    {
        NPLQueueAccRuleCardEntity nplAccountRule=nplQueueAccRuleCardService.findById(id);
        
        List<String> allowedAgeCodeIdList=new ArrayList<>();
        List<String> allowedProdIdList=new ArrayList<>();
        
        //System.err.println("nplAccountRule "+nplAccountRule);
        int sz = nplAccountRule.getAgeCodeList().size();
        for(int i=0;i < sz;i++)
        {
            allowedAgeCodeIdList.add(nplAccountRule.getAgeCodeList().get(i).getId().toString());
        }
        
        for(ProductTypeCardEntity productType : nplAccountRule.getProductTypeList())
        {
            allowedProdIdList.add(productType.getId().toString());
        }
        
        model.addAttribute("ageCodeList",ageCodeService.getActiveList());
        model.addAttribute("prodTypeList",productTypeService.getAllActive());
        
        model.addAttribute("allowedAgeCodeIdList",allowedAgeCodeIdList);
        model.addAttribute("allowedProdIdList",allowedProdIdList);
        model.addAttribute("nplQueue",nplQueueAccRuleCardService.findById(id));
        return "collection/settings/nplQueueAccRuleCard/create";
    }
}
