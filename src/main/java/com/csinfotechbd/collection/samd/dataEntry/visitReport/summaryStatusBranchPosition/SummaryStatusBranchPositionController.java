package com.csinfotechbd.collection.samd.dataEntry.visitReport.summaryStatusBranchPosition;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/collection/samd/dataEntry/visitReport/summaryStatusBranchPosition")
public class SummaryStatusBranchPositionController {

    @Autowired
    private SummaryStatusBranchPositionService summaryStatusBranchPositionService;


    @PostMapping("/save")
    @ResponseBody
    public SummaryStatusBranchPosition createSummaryBranchPosition(SummaryStatusBranchPosition position){
        SummaryStatusBranchPosition summaryStatusBranchPosition = summaryStatusBranchPositionService.save(position);
        return summaryStatusBranchPosition;
    }


    @GetMapping("/find")
    @ResponseBody
    public SummaryStatusBranchPosition findSummaryStatusBranchPositionByCustomerId(@RequestParam String customerId){
        SummaryStatusBranchPosition summaryStatusBranchPosition = summaryStatusBranchPositionService.findSummaryStatusBranchPositionByCustomerId(customerId);
        return summaryStatusBranchPosition;
    }
}
