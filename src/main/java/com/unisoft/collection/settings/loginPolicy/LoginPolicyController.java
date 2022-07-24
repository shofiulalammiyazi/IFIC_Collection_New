package com.unisoft.collection.settings.loginPolicy;
/*
Created by   Islam at 7/3/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/collection/login_policy/")
public class LoginPolicyController {

    @Autowired
    private LoginPolicyService loginPolicyService;

    @GetMapping(value = "create")
    public String addpage(Model model) {
        LoginPolicyEntity policy = loginPolicyService.getPolicy();
        model.addAttribute("entity", policy);
        return "collection/settings/loginPolicy/create";
    }

    @PostMapping(value = "create")
    public String saveNew(LoginPolicyEntity entity, Model model) {
        String output = loginPolicyService.save(entity);
        switch (output) {
            case "1":
                model.addAttribute("message", "Successfully saved");
                break;
            default:
                model.addAttribute("error", output);

        }
        model.addAttribute("entity", entity);
        return "collection/settings/loginPolicy/create";
    }

}
