package com.csinfotechbd.legal.setup.collateralSecurity;

import com.csinfotechbd.legal.setup.caseType.CaseTypeDto;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/setup/collateralsecurity/")
public class CollateralSecurityController {

    private final CaseTypeService caseTypeService;
    private final CollateralSecurityService collateralSecurityService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("collateralSecurityList", collateralSecurityService.findAll());
        return "legal/setup/collateralsecurity/list";
    }

    @GetMapping("create")
    public String save(Model model) {
        return prepareEditPageModel(new CollateralSecurity(), model);
    }

    @PostMapping("create")
    public String saveLitigation(@Valid CollateralSecurity collateralSecurity, BindingResult result, Model model) {
        if (result.hasErrors()) return "legal/setup/collateralsecurity/create";
        String output = collateralSecurityService.save(collateralSecurity);
        switch (output) {
            case "1":
                return "redirect:/legal/setup/collateralsecurity/list";
            default:
                model.addAttribute("error", output);
        }
        return prepareEditPageModel(collateralSecurity, model);
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        CollateralSecurity collateralSecurity = collateralSecurityService.findById(id);
        return prepareEditPageModel(collateralSecurity, model);
    }

    private String prepareEditPageModel(CollateralSecurity collateralSecurity, Model model) {
        List<CaseTypeDto> selectedCaseTypes = collateralSecurity.getCaseTypes()
                .stream().map(CaseTypeDto::new).collect(Collectors.toList());
        model.addAttribute("collateralSecurity", collateralSecurity);
        model.addAttribute("selectedCaseTypes", selectedCaseTypes);
        model.addAttribute("caseTypeOptions", caseTypeService.getEnabledCaseTypeDtoList());
        return "legal/setup/collateralsecurity/create";
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("collateralSecurity", collateralSecurityService.findById(id));
        return "legal/setup/collateralsecurity/view";
    }

//    @GetMapping("remove")
//    public String delete(@RequestParam("id") Long id) {
//        collateralSecurityService.deleteById(id);
//        return "redirect:/legal/setup/collateralsecurity/list";
//    }

//    @RequestMapping("exists")
//    @ResponseBody
//    public String view(@RequestParam("id") String name) {
//        boolean exists = collateralSecurityService.existsByName(name);
//        return String.valueOf(exists);
//    }


}
