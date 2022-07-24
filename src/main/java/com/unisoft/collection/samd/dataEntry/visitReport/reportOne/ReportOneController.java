package com.unisoft.collection.samd.dataEntry.visitReport.reportOne;


import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.branch.BranchService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/dataEntry/visitReport/reportOne")
public class ReportOneController {




    @Autowired
    private ReportOneService reportOneService;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BranchService branchService;



    @PostMapping("/save")
    @ResponseBody
    public ReportOne createReportOne(ReportOne reportOne){
        ReportOne reportOne1 = reportOneService.saveReportOne(reportOne);
        System.out.println("test");
        return reportOne1;
    }

    @GetMapping("/get-employee-list")
    @ResponseBody
    public List<EmployeeInfoEntity>employeeInfoEntityList(){
        List<EmployeeInfoEntity> employeeInfoEntityList = employeeService.getActiveList();
        return employeeInfoEntityList;
    }


    @GetMapping("/get-report")
    @ResponseBody
    public ReportOne getReportOneData(@RequestParam String customerId){
        ReportOne reportOne = reportOneService.findReportOneByCustomerId(customerId);
        return reportOne;
    }


    @GetMapping("/branch-list")
    @ResponseBody
    public List<Branch> getBranchList(){
        return branchService.getActiveList();
    }


}
