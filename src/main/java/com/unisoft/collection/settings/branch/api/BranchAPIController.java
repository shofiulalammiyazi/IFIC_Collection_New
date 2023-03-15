package com.unisoft.collection.settings.branch.api;

import com.unisoft.collection.settings.employee.API.EmployeeAPIService;
import com.unisoft.collection.settings.employee.API.EmployeeApiPayload;
import com.unisoft.collection.settings.employee.API.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch/api/")
public class BranchAPIController {

    @Autowired
    private BranchAPIService branchAPIService;

    @GetMapping("getBranches")
    public BranchAPIResponse callApi(){
        return branchAPIService.getBranchInfo();
    }
}
