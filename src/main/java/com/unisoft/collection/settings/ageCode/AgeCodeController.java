package com.unisoft.collection.settings.ageCode;
/*
Created by   Islam on 7/9/2019
*/

import com.unisoft.user.UserPrincipal;
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
@RequestMapping(value = "/collection/ageCode/")
public class AgeCodeController {

    @Autowired
    private AgeCodeService ageCodeService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("ageList", ageCodeService.getAll());
        return "collection/settings/agecode/agecode";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("age", new AgeCodeEntity());
        return "collection/settings/agecode/create";
    }

    @PostMapping("create")
    public String create(AgeCodeEntity ageCode) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (ageCode.getId() == null) {
            ageCode.setCreatedBy(user.getUsername());
            ageCode.setCreatedDate(new Date());
            ageCodeService.saveAgeCode(ageCode);
        } else {
            ageCode.setModifiedBy(user.getUsername());
            ageCode.setModifiedDate(new Date());
            ageCodeService.updateAgeCode(ageCode);
        }
        return "redirect:/collection/ageCode/list";
    }

    @GetMapping(value = "edit")
    public String viewPage(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("age", ageCodeService.getById(Id));
        return "collection/settings/agecode/create";
    }
}
