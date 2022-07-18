package com.csinfotechbd.collection.samd.dataEntry.proposalManagementSettlement.proposalDetails;

import com.csinfotechbd.collection.samd.setup.approvalLevel.ApprovalLevel;
import com.csinfotechbd.collection.samd.setup.approvalLevel.ApprovalLevelService;
import com.csinfotechbd.collection.samd.setup.hrPosition.HrPosition;
import com.csinfotechbd.collection.samd.setup.hrPosition.HrPositionService;
import com.csinfotechbd.collection.samd.setup.proposalStatus.ProposalStatus;
import com.csinfotechbd.collection.samd.setup.proposalStatus.ProposalStatusService;
import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection/samd/data-entry/settlement-proposal-details")
public class SettlementProposalDetailsController {

    private final HrPositionService hrPositionService;
    private final ApprovalLevelService approvalLevelService;
    private final ProposalStatusService proposalStatusService;
    private final AssetClassificationLoanService assetClassificationLoanService;
    private final SettlementProposalDetailsService settlementProposalDetailsService;

//    @GetMapping("/find")
//    public SettlementProposalDetails find(String customerId){
//        SettlementProposalDetails proposalDetails = settlementProposalDetailsService.findProposalDetailsByCustomerId(customerId);
//        return proposalDetails;
//    }

    @GetMapping("/find")
    @ResponseBody
    public SettlementProposalDetailsDto getSettlementProposalDetailsByCustId(@RequestParam(value = "customerId") String customerId) {
        SettlementProposalDetailsDto settlementProposalDetailsDto = settlementProposalDetailsService.getByCustomerId(customerId);
        return settlementProposalDetailsDto;
    }


    @PostMapping("/save")
    public SettlementProposalDetails save(SettlementProposalDetails proposalDetails){
        proposalDetails = settlementProposalDetailsService.save(proposalDetails);
        return proposalDetails;
    }

    @GetMapping("/cl-status/list")
    public List<AssetClassificationLoanEntity> getClStatus(){
        return assetClassificationLoanService.getAll();
    }

    @GetMapping("/proposal-status/list")
    public List<ProposalStatus> getProposalStatus(){
        return proposalStatusService.getAllProposalStatus();
    }

    @GetMapping("/proposal-place-to/list")
    public List<HrPosition>getProposalPlaceTo(){
        return hrPositionService.findAllData();
    }

    @GetMapping("/approval-level/list")
    public List<ApprovalLevel> getApprovalLevel(){
        return approvalLevelService.findAllData();
    }

}
