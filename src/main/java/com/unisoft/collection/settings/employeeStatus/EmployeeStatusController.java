package com.unisoft.collection.settings.employeeStatus;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/collection/employee_status/")
public class EmployeeStatusController {

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private EmployeeStatusService employeeStatusService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("statList", employeeStatusService.getAllStatus());
        return "collection/settings/employeeStatus/employee_status";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("status",new EmployeeStatusEntity());
        return "collection/settings/employeeStatus/create";
    }

    @PostMapping(value = "create")
    public String createStatus(EmployeeStatusEntity currentStatus)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentStatus.getId() == null)
        {
            currentStatus.setCreatedBy(user.getUsername());
            currentStatus.setCreatedDate(new Date());
            boolean save= employeeStatusService.saveStat(currentStatus);
            auditTrailService.saveCreatedData("Employee Status", currentStatus);
        }else {
            EmployeeStatusEntity oldStatus = employeeStatusService.getById(currentStatus.getId());
            EmployeeStatusEntity previousEntity = new EmployeeStatusEntity();
            BeanUtils.copyProperties(oldStatus, previousEntity);

            currentStatus.setModifiedBy(user.getUsername());
            currentStatus.setModifiedDate(new Date());
            boolean update= employeeStatusService.updateStat(currentStatus);
            auditTrailService.saveUpdatedData("Employee Status", previousEntity, currentStatus);
        }

        return "redirect:/collection/employee_status/list";
    }

    @GetMapping(value = "view")
    public String viewStatus(@RequestParam(value = "id")Long Id,Model model)
    {
        model.addAttribute("status", employeeStatusService.getById(Id));
        return "collection/settings/employeeStatus/view";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long Id,Model model)
    {
        model.addAttribute("status",employeeStatusService.getById(Id));
        return "collection/settings/employeeStatus/create";
    }
}
