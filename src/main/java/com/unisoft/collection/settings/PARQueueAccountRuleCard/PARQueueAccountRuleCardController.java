package com.unisoft.collection.settings.PARQueueAccountRuleCard;

import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/par/queue/acc/card")
public class PARQueueAccountRuleCardController {
    
    @Autowired
    private AgeCodeService ageCodeService;
    @Autowired
    private ProductTypeCardService productTypeCardService;
    
    @Autowired
    private PARQueueAccountRuleCardService parQueueAccountRuleCardService;
    
    @GetMapping(value = "/list")
    public String list(Model model){
        model.addAttribute("parQueueCardList",parQueueAccountRuleCardService.getAllActive());
        return "collection/settings/parQueueAccountRuleCard/list";
    }
    
    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("ageCodes",ageCodeService.getAll());
        model.addAttribute("productTypes",productTypeCardService.getAllActive());
        return "collection/settings/parQueueAccountRuleCard/create";
    }
    
    @PostMapping(value = "/save")
    public String save(@RequestBody PARQueueAccountRuleCard parQueueAccountRuleCard){
        parQueueAccountRuleCardService.save(parQueueAccountRuleCard);
        return "redirect:/par/queue/acc/card/list";
    }
}
