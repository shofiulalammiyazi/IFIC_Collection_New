package com.csinfotechbd.dataStore.loanListing;

import com.csinfotechbd.collection.distribution.loan.LoanAccountDistributionRepository;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanService;
import com.csinfotechbd.loanApi.model.*;
import com.csinfotechbd.loanApi.service.RetailLoanUcbApiService;
import com.csinfotechbd.reports.retail.loan.clReportMonthBasis.MonthBasisCLReportDto;
import com.csinfotechbd.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanListingService {

    @Autowired
    private LoanListingRepository loanListingRepository;
    @Autowired
    private LoanAccountDistributionRepository loanAccountDistributionRepository;
    @Autowired
    private RetailLoanUcbApiService retailLoanUcbApiService;
    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private PARaccountRuleLoanService paRaccountRuleLoanService;



    public void storeDataFromApi() {


//        Date startDate = dateUtils.getDateOfCurrentMonth(1);
//        Date endDate = dateUtils.getLocalMonthEndDate();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String pdate = "01-03-2022";
//        Date tdate = simpleDateFormat.parse(pdate);
//        List<String> accList = loanAccountDistributionRepository.getCurrentMonthDistributionAccNo(startDate, endDate);
//        List<LoanAccDetails> detailsList = retailLoanUcbApiService.getLoanListingForReportByDate("056RAAS190580004","", "",tdate);

        List<LoanAccDetails> detailsList = retailLoanUcbApiService.getLoanAccountDetails();
        try{
            for (LoanAccDetails loanAccDetail: detailsList) {
                System.out.println(loanAccDetail.getOverdue());
                LoanListing loanListing = new LoanListing();
                LoanAccInfo loanAccInfo = retailLoanUcbApiService.getLoanAccountInfo(loanAccDetail.getAccountNumber());
//            LoanAccDetails loanAccDetails = retailLoanUcbApiService.getLoanAccountDetails(loanAccDetail.getAccountNumber());
                CustomerInfo customerInfo = retailLoanUcbApiService.getCustomerInfo(loanAccInfo.getCustomerId());
                LoanAccSchedule loanAccSchedule = retailLoanUcbApiService.getAccScheduleDetails(loanAccDetail.getAccountNumber()).stream().findFirst().orElse(null);
                BranchInfo branchInfo = retailLoanUcbApiService.getBranchInfo(loanAccDetail.getBranchCode());

                String fid = checkFid(loanAccDetail, loanAccSchedule);

                loanListing.setAccNo(loanAccInfo.getLoanAccountNo());
                loanListing.setClStatus(loanAccInfo.getUserDefineStatus());
                loanListing.setClStatusDate(new Date());
                loanListing.setOutstanding(loanAccDetail.getOutStandingLocalCurrency());
                loanListing.setOverdueAmt(loanAccDetail.getOverdue());
                loanListing.setBucket(loanAccDetail.getDpdBucket());
                loanListing.setProductName(loanAccDetail.getProddes());
                loanListing.setSegment(customerInfo.getSecDesc());
                loanListing.setBranchName(branchInfo.getBranchName());
                loanListing.setBranchCode(loanAccDetail.getBranchCode());
                loanListing.setFid(fid);
                loanListing.setFirstDisbursementDate(loanAccDetail.getDisbursementDate());
                loanListing.setSourceChannel(loanAccInfo.getSource());
                loanListing.setListingDownDate(loanAccDetail.getGenerationDate());
                loanListing.setLoanAccPar(checkPar(loanListing.getBucket()));
                loanListing.setProductCode(loanAccDetail.getProductCode());

                loanListingRepository.save(loanListing);

            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private String checkFid(LoanAccDetails loanAccDetails, LoanAccSchedule loanAccSchedule) {
        Date fristPaymentDate = loanAccDetails.getFirstRepayDueDate();
        String scheduleDate = loanAccSchedule != null ? loanAccSchedule.getScheduleDueDate() : null;
        long differenceDate = differenceBetweenDates(scheduleDate, fristPaymentDate);
        if (differenceDate <= 0 && (Double.parseDouble(loanAccSchedule.getEmiAmount()) >= Double.parseDouble(loanAccSchedule.getAmountSattled()))){
            return "No";
        }
        return "Yes";
    }


    private long differenceBetweenDates(String expiryDate, Date newDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (expiryDate != null){
                Date formatedExpiryDate = simpleDateFormat.parse(expiryDate);
                if (formatedExpiryDate.compareTo(newDate) < 0) {
                    return ChronoUnit.DAYS.between(formatedExpiryDate.toInstant(), newDate.toInstant());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<MonthBasisCLReportDto> findCurrentMonthBasisReportData(String month) {
        List<Tuple> tuples = loanListingRepository.findDataForMonthBasisClReport(month);
        return tuples.stream().map(MonthBasisCLReportDto::new).collect(Collectors.toList());
    }

    public List<MonthBasisCLReportDto> findPreviousMonthBasisReportData(String previousMonth) {
        List<Tuple> tuples = loanListingRepository.findDataForMonthBasisClReport(previousMonth);
        return tuples.stream().map(MonthBasisCLReportDto::new).collect(Collectors.toList());
    }

    public List<Tuple> findCurrentMonth(String date){
        return loanListingRepository.findDataForMonthBasisClReport(date);

    }


    public String checkPar(double bucket){

        double testBucket = paRaccountRuleLoanService.getMaxBucket();
        if (bucket >= testBucket){
            return "Yes";
        }
        return "No";

    }
}
