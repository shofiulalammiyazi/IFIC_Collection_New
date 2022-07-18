package com.csinfotechbd.collection.samd.dataEntry.proposalManagementSettlement.latestLiabilityPositionLoanListing;

import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection/samd/data-entry/settlement-latest-liability-loan-listing")
public class SettlementLatestLiablilityPositionLoanListingController {

    private final AssetClassificationLoanService assetClassificationLoanService;
    private final SettlementLatestLiablilityPositionLoanListingService settlementLatestLiablilityPositionLoanListingService;

    @PostMapping("/save")
    public SettlementLatestLiabilityPositionLoanListing save(SettlementLatestLiabilityPositionLoanListing latestLiabilityPositionLoanListing){
        SettlementLatestLiabilityPositionLoanListing loanListing = settlementLatestLiablilityPositionLoanListingService.save(latestLiabilityPositionLoanListing);
        return loanListing;
    }

    @GetMapping("/list")
    public List<SettlementLatestLiabilityPositionLoanListing> list(@RequestParam String customerId){
        List<SettlementLatestLiabilityPositionLoanListing>loanListings = settlementLatestLiablilityPositionLoanListingService.findByCustomerId(customerId);
        return loanListings;
    }

    @GetMapping("/edit")
    public SettlementLatestLiabilityPositionLoanListing edit(@RequestParam Long id){
        SettlementLatestLiabilityPositionLoanListing loanListing = settlementLatestLiablilityPositionLoanListingService.findById(id);
        return loanListing;
    }

    @GetMapping("/cl-status")
    public List<AssetClassificationLoanEntity> getCLStatus(){
        List<AssetClassificationLoanEntity> classificationLoanEntities = assetClassificationLoanService.getAll();
        return classificationLoanEntities;
    }

}
