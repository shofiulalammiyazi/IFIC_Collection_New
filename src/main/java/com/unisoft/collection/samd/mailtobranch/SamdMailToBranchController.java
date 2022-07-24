package com.unisoft.collection.samd.mailtobranch;


import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.branch.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/samd/communication-by-mail")
public class SamdMailToBranchController {

    @Autowired
    private BranchService branchService;
    @Autowired
    private SamdMailService service;

    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("branches",branchService.getActiveList());
        model.addAttribute("accounts",service.getSamdMailAccountData());
        return "samd/mailtobranch/createmail";
    }

    @GetMapping(value = "/accounts")
    @ResponseBody
    public List<SamdMailAccountData> samdAccounts(){
        return service.getSamdMailAccountData();
    }

    @GetMapping(value = "/branches")
    @ResponseBody
    public List<Branch> getActiveList() {
        return branchService.getActiveList();
    }

    @PostMapping(value = "/send")
    @ResponseBody
    public boolean send(@RequestBody SamdMail mail){
        return service.sendMail(mail);
    }



}
