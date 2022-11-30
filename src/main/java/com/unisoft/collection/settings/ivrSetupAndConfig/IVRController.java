package com.unisoft.collection.settings.ivrSetupAndConfig;


import com.unisoft.collection.settings.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection/settings/ivr-setup-Config")
public class IVRController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/list")
    public String showList(Model model) {
        return "collection/settings/ivrSetup/list";
    }


    @GetMapping("/create")
    public String createPage(Model model) {
        return createUpdateIvr(new IvrEntity(), model);
    }

    private String createUpdateIvr(IvrEntity ivrEntity, Model model) {
        List<String> dealerPinList = employeeService.getDealerList().stream().map(t -> t.getPin()).collect(Collectors.toList());
        model.addAttribute("ivrEntity", ivrEntity);
        model.addAttribute("dealerPinList",dealerPinList);
        return "collection/settings/ivrSetup/create";
    }
}
