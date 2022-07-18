package com.csinfotechbd.collection.settings.officeTimeSetup;


import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/collection/settings/officeTimeSetup/")
public class OfficeTimeSetupController {

    @Autowired
    private OfficeTimeSetupService officeTimeSetupService;

    @GetMapping(value = "create")
    public String addPage(Model model) {

        OfficeTimeSetupInfo timeSetupInfo = officeTimeSetupService.getOfficeTimeSetup();
        if (timeSetupInfo == null) model.addAttribute("officetimeset", new OfficeTimeSetupInfo());
        else model.addAttribute("officetimeset", timeSetupInfo);
        return "collection/settings/officetimesetup/officetimesetup";
    }

    @PostMapping(value = "create", consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String addNewDiv(OfficeTimeSetupInfo officeTimeSetupInfo, Model model) {
        officeTimeSetupService.save(officeTimeSetupInfo);
        return "redirect:/collection/settings/officeTimeSetup/create";
    }
}
