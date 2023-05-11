package com.unisoft.collection.settings.branch.api;

import com.unisoft.collection.settings.branch.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/branch/api/")
public class BranchAPIController {

    @Autowired
    private BranchAPIService branchAPIService;

    @Autowired
    private BranchService branchService;

    @GetMapping("getBranches")
    public BranchAPIResponse callApi(){
        return branchAPIService.getBranchInfo();
    }

    @GetMapping("getBr")
    @Scheduled(cron = "0 0 8 * * *")
    public String BranchScheduker(){
        Map<String, BranchDetails> branch = branchAPIService.getBranchInfo().getBranch();
        String status = "false";
        if(branch != null) {
            branchService.saveBrFromAPI(branch);
            status = "true";
        }
        return status;
    }
}
