package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails;


import com.unisoft.collection.samd.setup.approvalLevel.ApprovalLevel;
import com.unisoft.collection.samd.setup.approvalLevel.ApprovalLevelService;
import com.unisoft.collection.samd.setup.bbApprovalRequirement.BBApprovalRequirement;
import com.unisoft.collection.samd.setup.bbApprovalRequirement.BBApprovalRequirementService;
import com.unisoft.collection.samd.setup.hrPosition.HrPosition;
import com.unisoft.collection.samd.setup.hrPosition.HrPositionService;
import com.unisoft.collection.samd.setup.proposalStatus.ProposalStatus;
import com.unisoft.collection.samd.setup.proposalStatus.ProposalStatusService;
import com.unisoft.collection.samd.setup.proposedRescheduleTimes.ProposedRescheduleTimes;
import com.unisoft.collection.samd.setup.proposedRescheduleTimes.ProposedRescheduleTimesService;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/data-entry/proposal-management-rescheduling/proposal-details")
public class ProposalDetailsController {

    @Autowired
    private ProposalDetailsService proposalDetailsService;

    @Autowired
    private ProposalStatusService proposalStatusService;

    @Autowired
    private ProposedRescheduleTimesService proposedRescheduleTimesService;

    @Autowired
    private HrPositionService hrPositionService;

    @Autowired
    private ApprovalLevelService approvalLevelService;

    @Autowired
    private BBApprovalRequirementService bbApprovalRequirementService;

    @Autowired
    private AssetClassificationLoanService assetClassificationLoanService;



//    @GetMapping("/find")
//    @ResponseBody
//    public ProposalDetails getProposalDetailsByCustomerId(String customerId){
//        System.out.println(customerId);
//        ProposalDetails proposalDetails = proposalDetailsService.findProposalDetailsByCustomerId(customerId);
//        System.out.println("list");
//        return proposalDetails;
//    }

    @GetMapping("/find")
    @ResponseBody
    public ProposalDetailsDto getProposalDetailsByCustId(@RequestParam(value = "customerId") String customerId) {
        ProposalDetailsDto proposalDetailsDtoList  = proposalDetailsService.getByCustomerId(customerId);
        return proposalDetailsDtoList;
    }

    @PostMapping("/save")
    @ResponseBody
    public ProposalDetails createProposalDetails(ProposalDetails proposalDetails)throws NumberFormatException{
        ProposalDetails proposalDetails1 = proposalDetailsService.save(proposalDetails);
        System.out.println("test");
        return proposalDetails1;
    }


    @GetMapping("/cl-status/list")
    @ResponseBody
    public List<AssetClassificationLoanEntity> getClStatus(){
        return assetClassificationLoanService.getAll();
    }


    @GetMapping("/proposal-status/list")
    @ResponseBody
    public List<ProposalStatus> getProposalStatus(){
        return proposalStatusService.getAllProposalStatus();
    }


    @GetMapping("/proposed-reschedule-times/list")
    @ResponseBody
    public List<ProposedRescheduleTimes> getProposedRescheduleTimeList(){
        List<ProposedRescheduleTimes> proposedRescheduleTimesList = proposedRescheduleTimesService.getAll();
        System.out.println(" proposal reshedule");
        return proposedRescheduleTimesList;
    }

    @GetMapping("/proposal-place-to/list")
    @ResponseBody
    public List<HrPosition>getProposalPlaceTo(){
        return hrPositionService.findAllData();
    }

    @GetMapping("/approval-level/list")
    @ResponseBody
    public List<ApprovalLevel> getApprovalLevel(){
        return approvalLevelService.findAllData();
    }


    @GetMapping("/bb-approval-requirement/list")
    @ResponseBody
    public List<BBApprovalRequirement> getBBApprovalRequirement(){
        return bbApprovalRequirementService.findAll();
    }
}
