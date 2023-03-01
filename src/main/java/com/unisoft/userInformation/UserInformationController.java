package com.unisoft.userInformation;

import com.unisoft.collection.settings.employee.API.EmployeeDetails;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/userInformation/")
public class UserInformationController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "list")
    public String list(Model model){

        return "/card/contents/settings/userInformation/list";
    }

    @GetMapping(value = "create")
    public String create(Model model){

        model.addAttribute("entity", new EmployeeDetails());
        model.addAttribute("roles", roleService.getActiveList());

     return "/card/contents/settings/userInformation/create";
    }
}
