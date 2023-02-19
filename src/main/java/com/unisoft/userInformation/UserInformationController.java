package com.unisoft.userInformation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/userInformation/")
public class UserInformationController {

    @GetMapping(value = "list")
    public String list(Model model){

        return "/card/contents/settings/userInformation/list";
    }

    @GetMapping(value = "create")
    public String create(Model model){

     return "/card/contents/settings/userInformation/create";
    }
}
