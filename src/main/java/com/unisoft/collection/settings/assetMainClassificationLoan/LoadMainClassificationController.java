package com.unisoft.collection.settings.assetMainClassificationLoan;
/*
Created by   Islam at 7/16/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/collection/mainclassification/")
public class LoadMainClassificationController {
    @Autowired
    private LoanMainClassificationService loanMainClassificationService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("mainclassificationlist", loanMainClassificationService.getAll());
        return "collection/settings/loanMainClassification/mainclassification";
    }

    @GetMapping("create")
    public String addpage(Model model) {
        model.addAttribute("mainclassification", new LoanMainClassification());
        return "collection/settings/loanMainClassification/create";
    }

    @PostMapping(value = "create")
    public String create(@Valid @ModelAttribute("mainclassification") LoanMainClassification mainclassification, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            String output = loanMainClassificationService.save(mainclassification);
            switch (output) {
                case "1":
                    return "redirect:/collection/mainclassification/list";
                default:
                    model.addAttribute("error", output);
            }
        }
        model.addAttribute("mainclassification", mainclassification);
        return "collection/settings/loanMainClassification/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("mainclassification", loanMainClassificationService.getById(id));
        return "collection/settings/loanMainClassification/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("mainclassification", loanMainClassificationService.getById(id));
        return "collection/settings/loanMainClassification/view";
    }
}
