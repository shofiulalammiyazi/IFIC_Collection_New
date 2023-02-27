package com.unisoft.collection.settings.loanType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/collection/loantype/")
public class LoanTypeController {

    @Autowired
    private LoanTypeService loanTypeService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("loanType", loanTypeService.getAll());
        return "collection/settings/loanType/list";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("loanType", new LoanTypeEntity());
        return "collection/settings/loanType/create";
    }

    @PostMapping(value = "create")
    public String save(LoanTypeEntity loanTypeEntity) {
        loanTypeService.saveNew(loanTypeEntity);
        return "redirect:/collection/loantype/list?id="+loanTypeEntity.getId();
    }

    @GetMapping("edit")
    public String edit(Model model, Long id) {
        LoanTypeEntity loanTypeEntity = loanTypeService.geByLoanTypeId(id);
        model.addAttribute("loanType", loanTypeEntity);
        return "collection/settings/loanType/create";
    }
}
