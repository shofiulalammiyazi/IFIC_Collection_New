package com.csinfotechbd.collection.settings.ageAndClassificationRule;
/*
Created by Monirul Islam on 7/9/2019
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.producttypecard.ProductTypeCardService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping(value = "/collection/age_classification_rule/")
public class AgeAndClassificationController {

    @Autowired
    private AgeAndClassificationRuleService ageAndClassificationRuleService;

    @Autowired
    private AgeCodeService ageCodeService;
    
    @Autowired
    private ProductTypeCardService productTypeCardService;

    @GetMapping("list")
    private String viewAll(Model model) {
        model.addAttribute("ruleList", ageAndClassificationRuleService.getAll());

        return "collection/settings/ageAndClassificationRule/rules";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("ageCodeList", ageCodeService.getActiveList());
        model.addAttribute("productTypeCardList", productTypeCardService.getAllActive());
        model.addAttribute("rule", new AgeAndClassifiactionRuleEntity());
        return "collection/settings/ageAndClassificationRule/create";
    }

    @PostMapping(value = "create")
    public String create(AgeAndClassifiactionRuleEntity ageAndClassifiactionRule) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (ageAndClassifiactionRule.getId() == null) {
            ageAndClassifiactionRule.setCreatedBy(user.getUsername());
            ageAndClassifiactionRule.setCreatedDate(new Date());
            ageAndClassificationRuleService.saveNew(ageAndClassifiactionRule);
        } else {
            ageAndClassifiactionRule.setModifiedBy(user.getUsername());
            ageAndClassifiactionRule.setModifiedDate(new Date());
            ageAndClassificationRuleService.updateRule(ageAndClassifiactionRule);
        }
        return "redirect:/collection/age_classification_rule/list";
    }

    @GetMapping(value = "edit")
    public String updateRule(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("rule", ageAndClassificationRuleService.getById(id));
        model.addAttribute("ageCodeList", ageCodeService.getActiveList());
        model.addAttribute("productTypeCardList", productTypeCardService.getAllActive());
        return "collection/settings/ageAndClassificationRule/create";
    }
}
