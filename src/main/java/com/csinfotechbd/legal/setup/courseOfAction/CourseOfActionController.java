package com.csinfotechbd.legal.setup.courseOfAction;

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
@RequestMapping("/legal/setup/courseofaction/")
public class CourseOfActionController {

    private final CourseOfActionService courseOfActionService;
    private final CaseTypeService caseTypeService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("courseOfActionlist", courseOfActionService.findAll());
        return "legal/setup/courseofaction/list";
    }

    @GetMapping("create")
    public String save(Model model) {
        return prepareEditPageModel(new CourseOfAction(), model);
    }

    @PostMapping("create")
    public String save(@Valid CourseOfAction courseOfAction, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            String output = courseOfActionService.save(courseOfAction);
            switch (output) {
                case "1":
                    return "redirect:/legal/setup/courseofaction/list";
                default:
                    model.addAttribute("error", output);
            }
        }
        return prepareEditPageModel(courseOfAction, model);
    }

    private String prepareEditPageModel(CourseOfAction courseOfAction, Model model) {
        List<CaseTypeDto> selectedCaseTypes = courseOfAction.getCaseTypes()
                .stream().map(CaseTypeDto::new).collect(Collectors.toList());
        model.addAttribute("courseOfAction", courseOfAction);
        model.addAttribute("selectedCaseTypes", selectedCaseTypes);
        model.addAttribute("caseTypeOptions", caseTypeService.getEnabledCaseTypeDtoList());
        return "legal/setup/courseofaction/create";
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        CourseOfAction courseOfAction = courseOfActionService.findById(id);
        return prepareEditPageModel(courseOfAction, model);
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("courseOfAction", courseOfActionService.findById(id));
        return "legal/setup/courseofaction/view";
    }

}