package com.unisoft.collection.settings.department;

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

/*
Created by   Islam at 6/24/2019
*/
@Controller
@RequestMapping(value = "/collection/department/")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("deptList",departmentService.getAll());
        return "collection/settings/department/department";
    }

    @GetMapping(value = "create")
    public String viewAddPage(Model model)
    {
        model.addAttribute("department",new DepartmentEntity());
        return "collection/settings/department/create";
    }

    @PostMapping(value = "create")
    public String createDept(DepartmentEntity department)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(department.getId() == null)
        {
            department.setCreatedBy(user.getUsername());
            department.setCreatedDate(new Date());
            boolean save=departmentService.save(department);
            auditTrailService.saveCreatedData("Department", department);
        }else {
            DepartmentEntity oldEntity = departmentService.getById(department.getId());
            DepartmentEntity previousEntity = new DepartmentEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            department.setModifiedBy(user.getUsername());
            department.setModifiedDate(new Date());
            boolean update=departmentService.updateDept(department);
            auditTrailService.saveUpdatedData("Department", previousEntity, department);
        }
        return "redirect:/collection/department/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("department",departmentService.getById(id));
        return "collection/settings/department/create";
    }


    @GetMapping(value = "view")
    public String view(Model model, @RequestParam (value = "id")Long deptId)
    {
        model.addAttribute("department",departmentService.getById(deptId));
        return "collection/settings/department/view";
    }
}
