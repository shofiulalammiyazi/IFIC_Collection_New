package com.unisoft.reports.retail.loan.listingReport;

import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.settings.previousaccount.PreviousAccountService;
import com.unisoft.loanApi.model.*;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ListingReportService {

    @Autowired
    private LoanAccountDistributionRepository loanAccountDistributionRepository;
    @Autowired
    private RetailLoanUcbApiService retailLoanUcbApiService;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private PreviousAccountService previousAccountService;


    public List<ListingReportDto> getListingReportData(){

        List<ListingReportDto> listingReportDtoList = new ArrayList<>();
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<String> stringList = loanAccountDistributionRepository.getCurrentMonthDistributionAccNo(startDate, endDate);
        for (String accNo: stringList){
            ListingReportDto listingReportDto = new ListingReportDto();
            LoanAccInfo loanAccInfo = retailLoanUcbApiService.getLoanAccountInfo(accNo);
            LoanAccDetails loanAccDetails = retailLoanUcbApiService.getLoanAccountDetails(accNo);
            CustomerInfo customerInfo = retailLoanUcbApiService.getCustomerInfo(loanAccInfo.getCustomerId());
            LoanAccSchedule loanAccSchedule = retailLoanUcbApiService.getAccScheduleDetails(accNo).stream().findFirst().orElse(null);
            BranchInfo branchInfo = retailLoanUcbApiService.getBranchInfo(loanAccDetails.getBranchCode());

            String previousLoanAccNo = loanAccDetails.getPreviousAccountNo() != null ? loanAccDetails.getPreviousAccountNo() : loanAccDetails.getAltAccNo() != null ? loanAccDetails.getAltAccNo() : previousAccountService.findPreviousAcccountByLoanAccountNo(accNo) != null ? previousAccountService.findPreviousAcccountByLoanAccountNo(accNo).getPreviousAccountNo() : "-";

            listingReportDto.setLoanAccountNo(accNo);
            listingReportDto.setOldAccountNo(previousLoanAccNo == null ? "-" : previousLoanAccNo);
            listingReportDto.setCustomerID(loanAccInfo.getCustomerId());
            listingReportDto.setBookingChannel("-");
            listingReportDto.setSourceID("-");
            listingReportDto.setSourceName("-");
            listingReportDto.setCustomerName(customerInfo.getCustomerName());
            listingReportDto.setCustomerPhone(customerInfo.getHomePhone());
            listingReportDto.setSettlementAC(loanAccDetails.getSattlementLoanAc());
            listingReportDto.setSettlementAcCustomerName("-");
            listingReportDto.setBalanceSettlementAmount(loanAccDetails.getSattlementAcBal());
            listingReportDto.setLoanlimit(loanAccDetails.getLimitAmount());
            listingReportDto.setLoanOutstanding(loanAccDetails.getOutStandingLocalCurrency());
            listingReportDto.setPrincipalOutstanding("-");
            listingReportDto.setInterestOutstanding("-");
            listingReportDto.setOverdueAmount(loanAccDetails.getOverdueAmount());
            listingReportDto.setInterestSuspense(loanAccDetails.getInterestSuspence());
            listingReportDto.setEmi(loanAccDetails.getEmiAmount());
            listingReportDto.setTotalRecovaryDuringMonth(loanAccInfo.getTotalRecoveryDuringMonth());
            listingReportDto.setClStatus(loanAccDetails.getStatus1());
            listingReportDto.setMainInterestRate(loanAccDetails.getInterestRate());
            listingReportDto.setDisburseDate(loanAccDetails.getDisbursementDate());
            listingReportDto.setDownloadDate("-");
            listingReportDto.setNextSchDate(loanAccDetails.getScheduleNextDate());
            listingReportDto.setExpiryDate(loanAccDetails.getExpiryDate());
            listingReportDto.setLoanStatus(loanAccInfo.getAccountStatus());
            listingReportDto.setProductName(loanAccDetails.getProductDesc());
            listingReportDto.setProductCode(loanAccDetails.getProductCode());
            listingReportDto.setOptimaID(loanAccInfo.getOptimaId());
            listingReportDto.setBranchCode(loanAccInfo.getBranchCode());
            listingReportDto.setBranchName(branchInfo.getBranchName());
            listingReportDto.setRegion(customerInfo.getLocation());
            listingReportDto.setPenalInterestRate(loanAccSchedule == null ? "-": loanAccSchedule.getPenalCharge());


            listingReportDtoList.add(listingReportDto);
        }

        return listingReportDtoList;
    }

}
