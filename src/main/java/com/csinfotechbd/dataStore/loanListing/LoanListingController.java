package com.csinfotechbd.dataStore.loanListing;


import com.csinfotechbd.loanApi.model.LoanAccDetails;
import com.csinfotechbd.loanApi.service.RetailLoanUcbApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class LoanListingController {


    @Autowired
    private LoanListingService loanListingService;

    @Autowired
    private RetailLoanUcbApiService retailLoanUcbApiService;


    //    @GetMapping("/loan-listing-data-for-report")
//    @ResponseBody
//    public void schedularDataForReport() throws Exception {
//
//        String str="2022-03-01";
//        Date date= Date.valueOf(str);
//
//        List<LoanAccDetails> detailsList = retailLoanUcbApiService.getLoanListingForReportByDate("","", "",date);
//
//
//        loanListingService.storeDataFromApi(detailsList);
//    }
}

// fid acc = 016RAAS220230001

// not fid = 044RAAS193040001
