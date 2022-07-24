package com.unisoft.retail.loan.dataEntry.manualAccountWriteOff;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/retail/loan/data-entry/manual-account-write-off")
public class ManualAccountWriteOffController {

    @Autowired
    private ManualAccountWriteOffService manualAccountWriteOffService;

    @GetMapping("/list")
    public String manualAccountWriteOffList(Model model){
        model.addAttribute("manualAccountList", manualAccountWriteOffService.findCurrentMonthWriteOffAccount());
        return "retail/loan/manualAccountWriteOff/manual-account-write-off";
    }

}
