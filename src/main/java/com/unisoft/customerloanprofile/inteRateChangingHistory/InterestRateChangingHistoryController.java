package com.unisoft.customerloanprofile.inteRateChangingHistory;

import com.unisoft.loanApi.model.LoanAccDetails;
import com.unisoft.loanApi.model.LoanInteRateChanging;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer-loan-profile/inte-rate-changing-history")
@RestController
public class InterestRateChangingHistoryController {


    @Autowired
    private RetailLoanUcbApiService dbService;



    @GetMapping("/find")
    public List<InteRateChangingHistoryDto> getInterestRateChangeHistory(String accNo){
        List<LoanInteRateChanging> loanInteRateChanging = dbService.getInterestChangeHistory(accNo);
        System.out.println("interest rate changing history   "+loanInteRateChanging.size());
        List<InteRateChangingHistoryDto> list = new ArrayList<>();

        for(LoanInteRateChanging loanInteRateChanging1: loanInteRateChanging) {

            LoanAccDetails loanAccDetails = dbService.getLoanListing(loanInteRateChanging1.getAccNo(), "", "", loanInteRateChanging1.getChangingTime());
            InteRateChangingHistoryDto inteRateChangingHistoryDto = new InteRateChangingHistoryDto();

            inteRateChangingHistoryDto.setChangingHistory(loanInteRateChanging1.getChangingTime());
            inteRateChangingHistoryDto.setOutstanding(loanAccDetails.getOutStandingLocalCurrency());
            inteRateChangingHistoryDto.setOverdue(loanAccDetails.getOverdue());
            inteRateChangingHistoryDto.setStatus(loanAccDetails.getStatus1());
            inteRateChangingHistoryDto.setInterestRate(loanInteRateChanging1.getUdeValue());

            list.add(inteRateChangingHistoryDto);
        }

        return list;
    }
}
