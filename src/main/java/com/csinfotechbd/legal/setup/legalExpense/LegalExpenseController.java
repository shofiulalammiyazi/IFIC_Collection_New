package com.csinfotechbd.legal.setup.legalExpense;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/setup/legalexpense")
public class LegalExpenseController {

    private final LegalExpenseService legalExpenseService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("legalExpenseList", legalExpenseService.findByEnabled(true));
        return "legal/setup/legalExpense/list";
    }

    @GetMapping("/create")
    public String save(Model model) {
        prepareFormModal(model, new LegalExpenseEntity());
        return "legal/setup/legalExpense/create";
    }

    @PostMapping("create")
    public String save(@Valid LegalExpenseEntity legalExpenseEntity, BindingResult result, Model model) {
        if (result.hasErrors()) return "legal/setup/legalexpense/create";
        legalExpenseService.save(legalExpenseEntity);

        model.addAttribute("legalExpense", legalExpenseEntity);

        return "redirect:/legal/setup/legalexpense/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        prepareFormModal(model, legalExpenseService.findById(id));
        return "legal/setup/legalExpense/create";
    }

    @GetMapping("/view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("legalExpense", legalExpenseService.findById(id));
        return "legal/setup/legalExpense/view";
    }


    private void prepareFormModal(Model model, LegalExpenseEntity legalExpense) {
        model.addAttribute("legalExpense", legalExpense);

    }
}
