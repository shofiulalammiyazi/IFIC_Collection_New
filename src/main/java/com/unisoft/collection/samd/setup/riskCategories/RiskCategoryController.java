package com.unisoft.collection.samd.setup.riskCategories;


import com.unisoft.collection.settings.ageCode.AgeCodeEntityDto;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntityDto;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.unit.UnitEntity;
import com.unisoft.collection.settings.unit.UnitService;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/samd/setup/riskCategories")
public class RiskCategoryController {

    private final DPDBucketService dpdBucketService;

    private final AgeCodeService ageCodeService;

    private final UnitService unitService;

    private final RiskCategoryService riskCategoryService;


    @GetMapping("/list")
    public String riskCategoryList(Model model){
        model.addAttribute("riskCategoryList", riskCategoryService.findAll());
        return "samd/setup/riskCategories/list";
    }

    @GetMapping("/create")
    public String riskCategoryCreateView(Model model){
        return prepareEditPageModel(new RiskCategory(),model);
    }


    @PostMapping("/create")
    public String creatRiskCategory(Model model, @Valid RiskCategory riskCategory, BindingResult result){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!result.hasErrors()){
            riskCategoryService.saveRiskCategory(riskCategory);
            model.addAttribute("success", "success");
            return "redirect:/collection/samd/setup/riskCategories/list";
        }else {
            System.out.println("error!!!!!");
        }
        return prepareEditPageModel(riskCategory,model);
    }


    @GetMapping("/edit")
    public String editRiskCatetory(@RequestParam(value = "id") Long id, Model model){
        RiskCategory riskCategory = riskCategoryService.findRiskCategoryById(id);
        return prepareEditPageModel(riskCategory, model);
    }



    private String prepareEditPageModel(RiskCategory riskCategory, Model model){

        List<DPDBucketEntityDto>selectedDpdBucket = riskCategory.getDpdBucketEntities().stream().map(DPDBucketEntityDto::new).collect(Collectors.toList());
        List<AgeCodeEntityDto>selectedAgeCode = riskCategory.getAgeCodeEntities().stream().map(AgeCodeEntityDto::new).collect(Collectors.toList());

        UnitEntity selectedUnit = riskCategory.getUnit();

        List<UnitEntity> unitEntityList = unitService.getList();

        model.addAttribute("unitList", unitEntityList);
        model.addAttribute("riskCategory", riskCategory);
        model.addAttribute("dpdList", dpdBucketService.getAll());
        model.addAttribute("ageCodeList", ageCodeService.getAll());
        model.addAttribute("selectedDpdBucket", selectedDpdBucket);
        model.addAttribute("selectedAgeCode", selectedAgeCode);
        model.addAttribute("selectedUnit", selectedUnit);
        return "samd/setup/riskCategories/create";
    }






}
