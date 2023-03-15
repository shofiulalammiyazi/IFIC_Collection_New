package com.unisoft.userInformation;

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.settings.branch.BranchService;
import com.unisoft.collection.settings.department.DepartmentService;
import com.unisoft.collection.settings.designation.DesignationService;
import com.unisoft.collection.settings.division.DivisionService;
import com.unisoft.collection.settings.employee.API.EmployeeDetails;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusService;
import com.unisoft.collection.settings.jobRole.JobRoleService;
import com.unisoft.collection.settings.loanType.LoanTypeEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.unit.UnitService;
import com.unisoft.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/userInformation/")
public class UserInformationController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private DesignationService designationService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private JobRoleService jobRoleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private EmployeeStatusService employeeStatusService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private EmployeeService employeeService;


    @GetMapping(value = "list")
    public String list(Model model){

        return "card/contents/settings/userInformation/list";
    }

    @GetMapping(value = "create")
    public String create(@ModelAttribute("entity") @Valid EmployeeInfoEntity employee,
                         BindingResult result, Model model){

        model.addAttribute("entity", new EmployeeInfoEntity());
        model.addAttribute("roles", roleService.getActiveList());
        model.addAttribute("desList", designationService.getActiveList());
        model.addAttribute("locationList", locationService.getActiveList());
        model.addAttribute("roleList", jobRoleService.getActiveList());
        model.addAttribute("deptList", departmentService.getActiveList());
        model.addAttribute("divList", divisionService.getActiveList());
        model.addAttribute("unitList", unitService.getActiveList());
        model.addAttribute("statusList", employeeStatusService.getAllActive());
        model.addAttribute("branches", branchService.getActiveList());

        return "card/contents/settings/userInformation/create";
    }

}
