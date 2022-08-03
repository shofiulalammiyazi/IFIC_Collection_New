package com.unisoft.collection.settings.unit;
/*
Created by    on 15/11/2020
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/collection/settings/unit/")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @GetMapping("list")
    public String getAll(Model model) {
        model.addAttribute("list", unitService.getList());
        return "collection/settings/unit/list";
    }

    @GetMapping("create")
    public String addPage(Model model) {
        model.addAttribute("entity", new UnitEntity());
        return "collection/settings/unit/create";
    }

    @PostMapping("create")
    public String addNew(@Valid @ModelAttribute("entity") UnitEntity unit, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            String output = unitService.save(unit);
            switch (output) {
                case "1":
                    return "redirect:/collection/settings/unit/list";
                default:
                    model.addAttribute("error", output);
            }
        }
        model.addAttribute("entity", unit);
        return "collection/settings/unit/create";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("entity", unitService.getById(id));
        return "collection/settings/unit/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("entity", unitService.getById(id));
        return "collection/settings/unit/view";
    }


}
