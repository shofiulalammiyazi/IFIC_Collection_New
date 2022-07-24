package com.unisoft.collection.settings.employeeStatusManagement;
/*
Created by   Islam on 7/11/2019
*/

import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusService;
import com.unisoft.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/collection/employee_status_manager/")
public class EmployeeStatusManagerController {

    @Autowired
    private EmployeeStatusmanagerService  employeeStatusmanagerService;

    @Autowired
    private EmployeeStatusService employeeStatusService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "list")
    public String viewAll(Model model)throws Exception
    {
        //System.err.println("EmpLastStat:ist :\n"+employeeStatusmanagerService.getEmpLastStatList(employeeService.getIdList()));
        model.addAttribute("empList",employeeStatusmanagerService.getEmpLastStatList(employeeService.getIdList()));
        return "collection/settings/employeeStatusManagement/empStatMgr";
    }

    @PostMapping(value = "create")
    public String addPage(EmployeeStatusManagerEntity statusManager)
    {
        if(statusManager.getId() == null)
        {
            boolean save= employeeStatusmanagerService.saveNew(statusManager);
        }else {

            boolean update=employeeStatusmanagerService.updateSts(statusManager);
        }

        return "redirect:/collection/employee_status_manager/list";

    }

//    @GetMapping(value = "create")
//    public String addPage(Model model)
//    {
//        model.addAttribute("empInfoList",employeeService.getAll());
//        model.addAttribute("statusList",employeeStatusService.getAllActive());
//
//        return "collection/settings/employeeStatusManagement/create";
//    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id,Model model)
    {

        model.addAttribute("statusList",employeeStatusService.getAllActive());
        model.addAttribute("status",employeeStatusmanagerService.getById(id));
        //System.err.println("user :"+userService.findById(employeeStatusmanagerService.getById(id).getUser().getUserId()));
        return "collection/settings/employeeStatusManagement/create";
    }

}
