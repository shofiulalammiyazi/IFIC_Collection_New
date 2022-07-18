package com.csinfotechbd.legal.setup.courts;

import com.csinfotechbd.legal.setup.caseType.CaseTypeDto;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/setup/courts/")
public class CourtsController {

    private final CaseTypeService caseTypeService;
    private final CourtsService courtsService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("courtslist", courtsService.findAll());
        return "legal/setup/courts/list";
    }

    @GetMapping("list-by-names")
    @ResponseBody
    public List<Courts> list(String[] names) {
        return courtsService.findByNames(Arrays.asList(names));
    }

    @GetMapping("create")
    public String save(Model model) {
        return prepareEditPageModel(new Courts(), model);
    }

    @PostMapping("create")
    public String saveLitigation(@Valid Courts courts, BindingResult result, Model model) {
        if (result.hasErrors()) return "legal/setup/courts/create";
        String output = courtsService.save(courts);
        switch (output) {
            case "1":
                return "redirect:/legal/setup/courts/list";
            default:
                model.addAttribute("error", output);
        }
        return prepareEditPageModel(courts, model);
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        return prepareEditPageModel(courtsService.findById(id), model);
    }

    private String prepareEditPageModel(Courts courts, Model model) {
        List<CaseTypeDto> selectedCaseTypes = courts.getCaseTypes()
                .stream().map(CaseTypeDto::new).collect(Collectors.toList());
        model.addAttribute("courts", courts);
        model.addAttribute("selectedCaseTypes", selectedCaseTypes);
        model.addAttribute("caseTypeOptions", caseTypeService.getEnabledCaseTypeDtoList());
        return "legal/setup/courts/create";
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("courts", courtsService.findById(id));
        return "legal/setup/courts/view";
    }

//    @GetMapping("remove")
//    public String delete(@RequestParam("id") Long id) {
//        courtsService.deleteById(id);
//        return "redirect:/legal/setup/courts/list";
//    }

//    @RequestMapping("exists")
//    @ResponseBody
//    public String view(@RequestParam("id") String name) {
//        boolean exists = courtsService.existsByName(name);
//        return String.valueOf(exists);
//    }


}
