package com.csinfotechbd.collection.settings.employeeProfile;
/*
Created by Monirul Islam at 7/23/2019 
*/

import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.jobRole.JobRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/employeeprofile/")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;
    private final JobRoleService jobRoleService;
    private final EmployeeService employeeService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("profilelist", employeeProfileService.getAll());
        return "collection/settings/employeeProfile/employeeprofile";
    }

    @GetMapping("create")
    public String addpage(Model model) {
        setModelAttributes(new EmployeeProfileInfo(), model);
        return "collection/settings/employeeProfile/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, EmployeeProfileInfo employeeProfileInfo) {
        String output = employeeProfileService.save(employeeProfileInfo);
        switch (output) {
            case "1":
                return "redirect:/collection/employeeprofile/list";
            default:
                model.addAttribute("error", output);
        }
        setModelAttributes(employeeProfileInfo, model);
        return "collection/settings/employeeprofile/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        EmployeeProfileInfo employeeProfile = employeeProfileService.getById(id);
        setModelAttributes(employeeProfile, model);
        return "collection/settings/employeeprofile/create";
    }

    private void setModelAttributes(EmployeeProfileInfo employeeProfile, Model model) {
        employeeProfile.setSelectedRoleFromJobRoleEntities();
        model.addAttribute("profile", employeeProfile);
        model.addAttribute("jobrole", jobRoleService.getActiveList());
        model.addAttribute("employeelist", employeeService.getAll());
        model.addAttribute("pin", employeeProfile.getEmployeePin());
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("profile", employeeProfileService.getById(id));
        return "collection/settings/employeeprofile/view";
    }
}
