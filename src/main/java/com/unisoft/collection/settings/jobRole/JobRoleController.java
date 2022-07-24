package com.unisoft.collection.settings.jobRole;
/*
Created by   Islam at 6/25/2019

*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/collection/job_role/")
public class JobRoleController {


    @Autowired
    private JobRoleService jobRoleService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("roleList", jobRoleService.getActiveList());
        return "collection/settings/jobRole/job_role";
    }

    @GetMapping(value = "create")
    public String viewAddPage(Model model) {
        model.addAttribute("jobRole", new JobRoleEntity());
        return "collection/settings/jobRole/create";
    }

    @PostMapping(value = "create")
    public String create(JobRoleEntity jobRole, Model model) {
        String output = jobRoleService.save(jobRole);
        switch (output) {
            case "1":
                return "redirect:/collection/job_role/list";
            default:
                model.addAttribute("error", output);

        }
        model.addAttribute("jobRole", jobRole);
        return "collection/settings/jobRole/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("jobRole", jobRoleService.getById(Id));
        return "collection/settings/jobRole/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("jobRole", jobRoleService.getById(Id));
        return "collection/settings/jobRole/view";
    }
}
