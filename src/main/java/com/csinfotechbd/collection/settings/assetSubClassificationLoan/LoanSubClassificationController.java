package com.csinfotechbd.collection.settings.assetSubClassificationLoan;
/*
Created by Monirul Islam at 7/16/2019 
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/collection/subclassification/")
public class LoanSubClassificationController {

    @Autowired
    private LoanSubClassificationService loanSubClassificationService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("subclassificationlist", loanSubClassificationService.getAll());
        return "collection/settings/loanSubClassification/subclassification";
    }

    @GetMapping("create")
    public String addpage(Model model) {
        model.addAttribute("subclassification", new LoanSubClassification());
        return "collection/settings/loanSubClassification/create";
    }

    @PostMapping(value = "create")
    public String create(@Valid @ModelAttribute("subclassification") LoanSubClassification subclassification, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            String output = loanSubClassificationService.save(subclassification);
            switch (output) {
                case "1":
                    return "redirect:/collection/subclassification/list";
                default:
                    model.addAttribute("error", output);
            }
        }
        model.addAttribute("subclassification", subclassification);
        return "collection/settings/loanSubClassification/create";

    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("subclassification", loanSubClassificationService.getById(id));
        return "collection/settings/loanSubClassification/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("subclassification", loanSubClassificationService.getById(id));
        return "collection/settings/loanSubClassification/view";
    }
}
