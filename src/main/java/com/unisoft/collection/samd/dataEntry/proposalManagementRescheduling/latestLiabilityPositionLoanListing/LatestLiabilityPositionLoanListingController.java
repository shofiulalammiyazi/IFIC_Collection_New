package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.latestLiabilityPositionLoanListing;


import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/data-entry/proposal-management-rescheduling/latest-liability-position-loan-listing")
public class LatestLiabilityPositionLoanListingController {


    @Autowired
    private LatestLiabilityPositionLoanListingService latestLiabilityPositionLoanListingService;

    @Autowired
    private AssetClassificationLoanService assetClassificationLoanService;


    @PostMapping("/save")
    @ResponseBody
    public LatestLiabilityPositionLoanListing crateLatestLiabilityPositionLoanListing(LatestLiabilityPositionLoanListing latestLiabilityPositionLoanListing){
        LatestLiabilityPositionLoanListing loanListing = latestLiabilityPositionLoanListingService.save(latestLiabilityPositionLoanListing);
        return loanListing;
    }



    @GetMapping("/list")
    @ResponseBody
    public List<LatestLiabilityPositionLoanListing> getLatestLiabilityPositionLoanListingList(@RequestParam String customerId){
        List<LatestLiabilityPositionLoanListing>loanListings = latestLiabilityPositionLoanListingService.findByCustomerId(customerId);
        return loanListings;
    }


    @GetMapping("/edit")
    @ResponseBody
    public LatestLiabilityPositionLoanListing getLatestLiabilityPositionLoanListing(@RequestParam Long id){
        LatestLiabilityPositionLoanListing loanListing = latestLiabilityPositionLoanListingService.findLatestLiabilityPositionLoanListingById(id);
        return loanListing;
    }

    @GetMapping("/cl-status")
    @ResponseBody
    public List<AssetClassificationLoanEntity> getCLStatus(){
        List<AssetClassificationLoanEntity> classificationLoanEntities = assetClassificationLoanService.getAll();
        return classificationLoanEntities;
    }
}
