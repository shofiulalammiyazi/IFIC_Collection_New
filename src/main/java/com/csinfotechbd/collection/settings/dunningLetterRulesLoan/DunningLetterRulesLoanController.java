package com.csinfotechbd.collection.settings.dunningLetterRulesLoan;

import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.manualLetterSetup.ManualLetterSetupService;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection/settings/dunningLetterRulesLoan/")
public class DunningLetterRulesLoanController {

    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private ManualLetterSetupService manualLetterSetupService;
    @Autowired
    private DPDBucketService dpdBucketService;
    @Autowired
    private DunningLetterRulesLoanService dunningLetterRulesLoanService;

    @GetMapping("list")
    public String viewDunningLetterRulesLoanList(Model model){

        model.addAttribute("dlrllist",dunningLetterRulesLoanService.getDunningLetterRulesLoanInfoList());
        return  "collection/settings/dunningletterrulesloan/dunningletterrulesloan";
    }

    @GetMapping("create")
    public String addNewPage(Model model)
    {
        Gson gson  = new Gson();
        model.addAttribute("productTypeLoanList", gson.toJson(productTypeRepository.findAll()));

        model.addAttribute("letterTypeList", gson.toJson(manualLetterSetupService.getManualLetterSetupList()));

        model.addAttribute("dpdBucketList",gson.toJson(dpdBucketService.getAll()));

        DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo = new DunningLetterRulesLoanInfo();

        model.addAttribute("dlrl",dunningLetterRulesLoanInfo);
        model.addAttribute("conditionTypes", ConditionTypeEnum.values());
        return "collection/settings/dunningletterrulesloan/create";
    }



//    @GetMapping("create")
//    public String addNewPage(Model model) {
//        model.addAttribute("productTypeLoanList", productTypeRepository.findAll());
//        model.addAttribute("letterTypeList", manualLetterSetupService.getManualLetterSetupList());
//        model.addAttribute("dpdBucketList", dpdBucketService.getAll());
//        model.addAttribute("dlrl", new DunningLetterRulesLoanInfo());
//        model.addAttribute("conditionTypes", ConditionTypeEnum.values());
//        return "collection/settings/dunningletterrulesloan/create";
//    }


    /*@PostMapping("create")
    public String saveDunningLetterRules(DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo, HttpServletRequest request, @RequestParam(name="letterTypesIdList",required=false) List<String> letterTypesIdList){
        List<ManualLetterSetupInfo> manualLetterSetupInfoList = new ArrayList<>();

        String[] name = request.getParameter("conditions[0].dpdBuckets0").split(",");

        manualLetterSetupInfoList = letterTypesIdList.stream().map(letterType->{

            return manualLetterSetupService.getById(Long.parseLong(letterType.toString()));

        }).collect(Collectors.toList());

        //dunningLetterRulesLoanInfo.setLetterTypes(manualLetterSetupInfoList);
        dunningLetterRulesLoanService.saveDunningLetterLoanInfo(dunningLetterRulesLoanInfo);

        return  "redirect:/collection/settings/dunningLetterRulesLoan/list";
    }*/

    @PostMapping("create")
    @ResponseBody
    public ResponseEntity save(@RequestBody DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo){
        System.out.println(dunningLetterRulesLoanInfo);
        boolean save = false;

        if(dunningLetterRulesLoanInfo.getId()==null)
           save = dunningLetterRulesLoanService.saveDunningLetterLoanInfo(dunningLetterRulesLoanInfo);
        else
            save =dunningLetterRulesLoanService.update(dunningLetterRulesLoanInfo);

        if(save)
            return ResponseEntity.ok("Saved successfully");
        else
            return ResponseEntity.ok("Not saved");
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long dlrId, Model model){

        DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo  = dunningLetterRulesLoanService.getDunningLetterInfo(dlrId);
        model.addAttribute("dlrl",dunningLetterRulesLoanInfo);

        Gson gson = new Gson();
        model.addAttribute("productTypeLoanList", gson.toJson(productTypeRepository.findAll()));

        model.addAttribute("letterTypeList", gson.toJson(manualLetterSetupService.getManualLetterSetupList()));

        model.addAttribute("dpdBucketList",gson.toJson(dpdBucketService.getAll()));


        model.addAttribute("selectedProductTypeLoanList",gson.toJson(dunningLetterRulesLoanInfo.getProductType()));
        model.addAttribute("selectedGuarantorDpdBucketType",gson.toJson(dunningLetterRulesLoanInfo.getGuarantorDpdBucket()));

        model.addAttribute("conditionTypes", ConditionTypeEnum.values());
        List<DunningLetterRulesLoanCondition> dunningLetterRulesConditionList = dunningLetterRulesLoanInfo.getConditions();
        List<DunningLetterRulesDpdLetterType> dunningLetterRulesDpdLetterTypeList = dunningLetterRulesConditionList.stream().flatMap(dlc->dlc.getDunningLetterRulesDpdLetterType().stream()).collect(Collectors.toList());

        //model.addAttribute("selectedDpdLetterTypeList",dunningLetterRulesConditionList);
        model.addAttribute("selectedDunningLetterConditionList",dunningLetterRulesConditionList);

        return "collection/settings/dunningletterrulesloan/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long dlrId,Model model){
        model.addAttribute("dlrl",dunningLetterRulesLoanService.getDunningLetterInfo(dlrId));
        return  "collection/settings/dunningletterrulesloan/view";
    }
}
