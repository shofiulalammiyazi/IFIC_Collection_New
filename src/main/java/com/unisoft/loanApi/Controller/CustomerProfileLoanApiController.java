package com.unisoft.loanApi.Controller;

import com.unisoft.loanApi.model.*;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/loan/api/")
public class CustomerProfileLoanApiController {

    private final RetailLoanUcbApiService dbService;
    private final DateUtils dateUtils;


    @GetMapping("find-rm-listing")
    public List<LoanAccDetails> getRMInfoByRMCode(String rmCode){
        String accNo = "";
        Date date = null;
        String cif = "";
        List<LoanAccDetails> loanAccDetails = dbService.getRMInfoByRMCode(accNo,cif , rmCode, date);
        return loanAccDetails;
    }

    @GetMapping("find-loan-listing")
    public LoanAccDetails getLoanListing(String accNo, String cif, String rmCode, Date pDate){
        Date date = null;
        LoanAccDetails loanAccDetails = dbService.getLoanListing(accNo, cif, rmCode, date);
        return loanAccDetails;
    }

    @GetMapping("customer-info")
    public CustomerInfo getCustomerInfo(String customerNo) {
        return dbService.getCustomerInfo(customerNo);
    }

    @GetMapping("total-facility-limit")
    public List<TotalFacilityLimit> getTotalFacilityLimit(String customerNo) {
        return dbService.getTotalFacilityLimit(customerNo);
    }

    @GetMapping("facility-limit/list")
    public List<CustAccList> getActualFacilityLimmit(String customerNo){
        List<CustAccList> custAccLists = dbService.getActualTotalFacilityLimit(customerNo);
        return custAccLists;
    }

    @GetMapping("account-info")
    public LoanAccInfo getAccountInfo(String accountNo) {
        return dbService.getLoanAccountInfo(accountNo);
    }

    @GetMapping("account-details")
    public LoanAccDetails getAccountDetails(String accountNo) {
        return dbService.getLoanAccountDetails(accountNo);
    }

    @GetMapping("account-schedule")
    public LoanAccSchedule getAccountSchedule(String accountNo) {
        List<LoanAccSchedule> accScheduleList = new ArrayList<>();

        List<LoanAccSchedule> accSchedules = dbService.getAccScheduleDetails(accountNo);
        Double penalCharge = 0d;
        double penalForcast = 0.0;
        for (LoanAccSchedule schedule : accSchedules) {
            penalCharge += schedule.getOverdueInt();
            double amountSattled = Double.parseDouble(schedule.getAmountSattled());
            if ((amountSattled == 0) && (schedule.getOverdueInt() > 0)){
                long differenceDate = differenceBetweenTwoDates(schedule.getScheduleDueDate(), new Date());
                double pint = ((Double.parseDouble(schedule.getPrincipal()) * 0.11)/360)*differenceDate;
                double oint = ((schedule.getOverdueInt()* 0.11)/360) * differenceDate;
                penalForcast += (pint+oint);
            }
        }
        LoanAccSchedule accountSchedule = accSchedules.isEmpty() ? new LoanAccSchedule() : accSchedules.get(0);
        accountSchedule.setPenalCharge(penalCharge);
        accountSchedule.setPenalForcast(Double.parseDouble(String.format("%.2f",penalForcast)));
        return accountSchedule;
    }


    @GetMapping("account-schedule-first")
    public LoanAccSchedule getFirstAccountSchedule(String accountNo) {
        List<LoanAccSchedule> accSchedules = dbService.getAccScheduleDetails(accountNo);
        Double penalCharge = 0d;
        for (LoanAccSchedule schedule : accSchedules) {
            penalCharge += schedule.getOverdueInt();
        }
        LoanAccSchedule accountSchedule = accSchedules.isEmpty() ? new LoanAccSchedule() : accSchedules.get(0);
        accountSchedule.setPenalCharge(penalCharge);
        return accountSchedule;
    }

    @GetMapping("interest-rate")
    public LoanInterestRate getInterestRate(String accountNo) {
        return dbService.getInterestRate(accountNo);
    }

    @GetMapping("loan-account-statement/history")
    public List<LoanAccStatement> getLoanAccountStatements(
            String accountNo,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<LoanAccStatement> accountStatements = dbService.getLoanAccStatement(accountNo, startDate, endDate);
        return accountStatements;
    }

    @GetMapping("link-account-statement/history")
    public List<LinkAccStatement> getLinkAccountStatements(
            String accountNo,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<LinkAccStatement> accountStatements = dbService.getLinkAccStatement(accountNo, startDate, endDate);
        return accountStatements;
    }

    @GetMapping("loan-account-statement/latest")
    public LoanAccStatement getLatestAccountStatement(String accountNo) {
        return dbService.getLoanAccStatement(accountNo);

    }

    @GetMapping("branch-info")
    public BranchInfo getBranchInfo(String branchCode) {
        BranchInfo branchInfo = dbService.getBranchInfo(branchCode);
        return branchInfo;
    }

    @GetMapping("lien-block")
    public List<LienBlock> getLienBlock(String branchCode) {
        List<LienBlock> lienBlock = dbService.getLienBlock(branchCode);
        return lienBlock;
    }

    @GetMapping("account-wise-lien-block")
    public List<LienBlock> getAccountWiseLienBlock(String branchCode, String accountNo) {
        List<LienBlock> lienBlock = dbService.getAccountWiseLienBlock(branchCode, accountNo);
        return lienBlock;
    }

    @GetMapping("other-applicants")
    public List<OtherApplicants> getOtherApplicants(String accountNo) {
        List<OtherApplicants> otherApplicants = dbService.getOtherApplicants(accountNo);
        return otherApplicants;
    }

    @GetMapping("casa-account-info")
    public CasaAccInfo getCasaAccountInfo(String branchCode, String accountNo) {
        List<CasaAccInfo> casaAccInfos = dbService.getCasaAccountInfo(branchCode, accountNo);
        return casaAccInfos.isEmpty() ? new CasaAccInfo() : casaAccInfos.get(casaAccInfos.size() - 1);
    }


    @GetMapping("interest_rate_history")
    public LoanInteRateChanging getInterestRateHistory(String accNo, String rateType){
        LoanInteRateChanging loanInteRateChanging = dbService.getInterestRateHistory(accNo, rateType);
        return loanInteRateChanging;
    }

//    @GetMapping("test")
//    public List test() {
//        return dbService.test();
//    }


    public long differenceBetweenTwoDates(String date1, Date date2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            if (date1 != null){
                Date date = simpleDateFormat.parse(date1);
                if (date.compareTo(date2) < 0) {
                    return ChronoUnit.DAYS.between(date.toInstant(), date2.toInstant());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
