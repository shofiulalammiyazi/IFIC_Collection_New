package com.csinfotechbd.collection.settings.designation;
/*
Created by Monirul Islam at 6/24/2019
*/

import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping(value = "/collection/designation/")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("desList", designationService.getAll());
        return "collection/settings/designation/designation";
    }

    @GetMapping(value = "create")
    public String viewAddPage(Model model) {
        model.addAttribute("designation", new DesignationEntity());
        return "collection/settings/designation/create";
    }

    @PostMapping(value = "create")
    public String createDes(DesignationEntity designation) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (designation.getId() == null) {
            designation.setCreatedBy(Long.toString(user.getId()));
            designation.setCreatedDate(new Date());
            designationService.saveNew(designation);
        } else {
            designation.setModifiedBy(Long.toString(user.getId()));
            designation.setModifiedDate(new Date());
            designationService.updateDes(designation);
        }
        return "redirect:/collection/designation/list";
    }

    @GetMapping(value = "edit")
    public String editPage(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("designation", designationService.getById(id));
        return "collection/settings/designation/create";
    }

    @GetMapping(value = "view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("designation", designationService.getById(id));
        return "collection/settings/designation/view";
    }

}
