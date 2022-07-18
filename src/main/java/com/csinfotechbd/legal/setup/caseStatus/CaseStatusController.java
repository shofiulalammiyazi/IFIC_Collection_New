package com.csinfotechbd.legal.setup.caseStatus;

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
@RequestMapping("/legal/setup/casestatus/")
public class CaseStatusController {

    private final CaseStatusService caseStatusService;
    private final CaseTypeService caseTypeService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("caseStatusList", caseStatusService.findAll());
        return "legal/setup/casestatus/list";
    }

    @GetMapping("create")
    public String save(Model model) {
        return prepareEditPageModel(new CaseStatus(), model);
    }

    @PostMapping("create")
    public String save(@Valid CaseStatus caseStatus, BindingResult result, Model model) {
        if (result.hasErrors()) return "legal/setup/casestatus/create";
        String output = caseStatusService.save(caseStatus);
        switch (output) {
            case "1":
                return "redirect:/legal/setup/casestatus/list";
            default:
                model.addAttribute("error", output);

        }
        return prepareEditPageModel(caseStatus, model);
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        CaseStatus courseOfAction = caseStatusService.findById(id);
        return prepareEditPageModel(courseOfAction, model);
    }

    private String prepareEditPageModel(CaseStatus caseStatus, Model model) {
        List<CaseTypeDto> selectedCaseTypes = caseStatus.getCaseTypes()
                .stream().map(CaseTypeDto::new).collect(Collectors.toList());
        model.addAttribute("caseStatus", caseStatus);
        model.addAttribute("selectedCaseTypes", selectedCaseTypes);
        model.addAttribute("caseTypeOptions", caseTypeService.getEnabledCaseTypeDtoList());
        return "legal/setup/casestatus/create";
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("caseStatus", caseStatusService.findById(id));
        return "legal/setup/casestatus/view";
    }

//    @GetMapping("remove")
//    public String delete(@RequestParam("id") Long id) {
//        statusService.deleteById(id);
//        return "redirect:/legal/setup/caseStatus/list";
//    }

//    @RequestMapping("exists")
//    @ResponseBody
//    public String view(@RequestParam("id") String name) {
//        boolean exists = statusService.existsByName(name);
//        return String.valueOf(exists);
//    }


}
