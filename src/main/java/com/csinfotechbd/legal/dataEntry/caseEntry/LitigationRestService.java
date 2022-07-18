package com.csinfotechbd.legal.dataEntry.caseEntry;


// Created by Yasir Araphat on 15 March, 2021

import com.csinfotechbd.loanApi.model.BranchInfo;
import com.csinfotechbd.loanApi.model.LoanAccDetails;
import com.csinfotechbd.loanApi.service.RetailLoanUcbApiService;
import com.csinfotechbd.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LitigationRestService {

    private final RetailLoanUcbApiService retailLoanUcbApiService;


    public LitigationCaseInfoDto getAccountInfoFromApi(String[] accountNumbers) {
        Set<String> accountSet = Stream.of(accountNumbers).collect(Collectors.toSet());

        LitigationCaseInfoDto caseInfoDto = new LitigationCaseInfoDto();
        try {
            for (String accountNo : accountSet) {
                LoanAccDetails accDetails = retailLoanUcbApiService.getLoanAccountDetails(accountNo);
                BranchInfo branchInfo = retailLoanUcbApiService.getBranchInfo(accDetails.getBranchCode());

                if (StringUtils.hasText(caseInfoDto.getBranchName()) && !caseInfoDto.getBranchName().contains(branchInfo.getBranchName())) {
                    String branchName = caseInfoDto.getBranchName() + "," + branchInfo.getBranchName();
                    String branchCode = caseInfoDto.getBranchCode() + "," + accDetails.getBranchCode();
                    caseInfoDto.setBranchName(branchName);
                    caseInfoDto.setBranchCode(branchCode);
                }
                String accountName = !StringUtils.hasText(caseInfoDto.getNameOfAcc()) ? accDetails.getAccountName() : caseInfoDto.getNameOfAcc() + "," + accDetails.getAccountName();
                String cif = !StringUtils.hasText(caseInfoDto.getCustomerCifNo()) ? accDetails.getCustomerCifNumber() : caseInfoDto.getCustomerCifNo() + "," + accDetails.getCustomerCifNumber();
                Double outstanding = caseInfoDto.getOutstanding() == null ? accDetails.getOutStandingLocalCurrency() : caseInfoDto.getOutstanding() + accDetails.getOutStandingLocalCurrency();
                caseInfoDto.setNameOfAcc(accountName);
                caseInfoDto.setCustomerCifNo(cif);
                caseInfoDto.setOutstanding(outstanding);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caseInfoDto;
    }

}
