package com.csinfotechbd.loanApi.service;

import com.csinfotechbd.collection.dashboard.AdvanceSearchPayload;
import com.csinfotechbd.loanApi.dao.FunctionDao;
import com.csinfotechbd.loanApi.model.*;
import com.csinfotechbd.loanApi.utils.ParameterManager;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class RetailLoanUcbApiService {

    private final ParameterManager parameterManager;
    private final DateUtils dateUtils;

    @Autowired
    @Qualifier("preparedStatementBasedDao")
    private FunctionDao preparedStatementBasedDao;
//    @Autowired
//    @Qualifier("simpleJdbcCallBasedDao")
//    private FunctionDao simpleJdbcCallBasedDao;

    public CustomerInfo getCustomerInfo(String customerNo) {
        Map<String, Object> params = parameterManager.getPersonalInfoParams(customerNo);
        String functionName = "RFN_GET_CUSTOMER_INFO";
        List<CustomerInfo> values = preparedStatementBasedDao.getDataFromServer2(functionName, params, CustomerInfo.class);
        return values.isEmpty() ? new CustomerInfo() : values.get(0);
    }

    public List<CustomerInfo> getCustomerInfo(String customerNo, String email, String tin, String nid,
                                              String dateOfBirth, String mobileNo, String fatherName,
                                              String motherName, String customerName) {
        Map<String, Object> params = parameterManager.getPersonalInfoParams(
                customerNo, email, tin, nid, dateOfBirth, mobileNo, fatherName, motherName, customerName);
        String functionName = "FXN_GET_CUSTOMER_INFO";
        return preparedStatementBasedDao.getDataFromServer1(functionName, params, CustomerInfo.class);
    }

    public List<TotalFacilityLimit> getTotalFacilityLimit(String customerNo) {
        Map<String, Object> params = parameterManager.getCustomerIdParams(customerNo);
        String functionName = "FXN_GET_LIABILITY_POSITION";
        List<TotalFacilityLimit> values = preparedStatementBasedDao.getDataFromServer1(functionName, params, TotalFacilityLimit.class);
        return values;
    }

    public List<CustAccList> getActualTotalFacilityLimit(String customerNo) {
        Map<String, Object> params = parameterManager.getCustomerIdParams(customerNo);
        String functionName = "FXN_GET_CUST_ACCOUNT_LIST";
        List<CustAccList> values = preparedStatementBasedDao.getDataFromServer1(functionName, params, CustAccList.class);
        return values;
    }

    public LoanAccInfo getLoanAccountInfo(String accountNo) {
        Map<String, Object> params = parameterManager.getAccountParams(accountNo);
        String functionName = "FXN_GET_LOAN_ACC_INFO";
        List<LoanAccInfo> values = preparedStatementBasedDao.getDataFromServer2(functionName, params, LoanAccInfo.class);
        log.info("search return " + values.size() + " accounts");
        return values.isEmpty() ? new LoanAccInfo() : values.get(0);
    }

    public List<LoanAccInfo> getLoanAccountInfo() {
        Map<String, Object> params = parameterManager.getAccountParams(null);
        String functionName = "FXN_GET_LOAN_ACC_INFO";
        return preparedStatementBasedDao.getDataFromServer2(functionName, params, LoanAccInfo.class);
//        return preparedStatementBasedDao.getDataFromServer1(functionName, params, LoanAccInfo.class);
    }

    public LoanAccDetails getLoanAccountDetails(String accountNo) {
        Map<String, Object> params = parameterManager.getLoanListingParams(accountNo);
        String functionName = "RFN_GET_LOAN_LISTING";
        List<LoanAccDetails> values = preparedStatementBasedDao.getDataFromServer2(functionName, params, LoanAccDetails.class);
        LoanAccDetails loanAccDetails = values.isEmpty() ? new LoanAccDetails() : values.get(0);
        String fid = checkFid(loanAccDetails);
        loanAccDetails.setFid(fid);
        return loanAccDetails;
    }

    public List<LoanAccDetails> getLoanAccountDetails() {
        Map<String, Object> params = parameterManager.getAccountParams(null);
        String functionName = "RFN_GET_LOAN_LISTING";
        return preparedStatementBasedDao.getDataFromServer2(functionName, params, LoanAccDetails.class);
    }

    public LoanInterestRate getInterestRate(String accountNo) {
        Map<String, Object> params = parameterManager.getAccountParams(accountNo);
        String functionName = "FXN_GET_LOAN_INTEREST_DETAILS";
        List<LoanInterestRate> values = preparedStatementBasedDao.getDataFromServer1(functionName, params, LoanInterestRate.class);
        return values.isEmpty() ? new LoanInterestRate() : values.get(0);
    }

    public List<LoanAccSchedule> getAccScheduleDetails(String accountNo) {
        Map<String, Object> params = parameterManager.getAccountParams(accountNo);
        String functionName = "FXN_GET_LOAN_ACC_SCHEDULE";
        return preparedStatementBasedDao.getDataFromServer1(functionName, params, LoanAccSchedule.class);
    }

    public List<LoanAccStatement> getLoanAccStatement(String accountNo, Date startDate, Date endDate) {
        Map<String, Object> params = parameterManager.getLoanAccStatementParams(accountNo, startDate, endDate);
        String functionName = "FXN_GET_LOAN_ACC_STATEMENT";
        List<LoanAccStatement> loanAccStatements =  preparedStatementBasedDao.getDataFromServer1(functionName, params, LoanAccStatement.class);
        return  loanAccStatements;
    }

    public List<LinkAccStatement> getLinkAccStatement(String accountNo, Date startDate, Date endDate) {
        Map<String, Object> params = parameterManager.getLinkAccStatementParams(accountNo, startDate, endDate);
        String functionName = "FXN_GET_CASA_OD_STMT";
        return preparedStatementBasedDao.getDataFromServer1(functionName, params, LinkAccStatement.class);
    }

    public LoanAccStatement getLoanAccStatement(String accountNo) {
        Date startDate = dateUtils.getFormattedDate("01/01/1971", "DD/MM/YYYY");
        Date endDate = new Date();
        Map<String, Object> params = parameterManager.getLoanAccStatementParams(accountNo, startDate, endDate);
        return preparedStatementBasedDao.getLatestAccountStatement(params);
    }

    public double getTotalLoanPayment(String accountNo, Date startDate, Date endDate) {
        List<LoanAccStatement> statements = getLoanAccStatement(accountNo, startDate, endDate);
        double totalPrinciplePaid = 0;

        for (LoanAccStatement statement : statements) {
            totalPrinciplePaid += statement.getInterestCredit() + statement.getPrincipalCredit();
        }

        return totalPrinciplePaid;
    }

    public double getTotalPrincipalPaid(String accountNo, Date startDate, Date endDate) {
        List<LoanAccStatement> statements = getLoanAccStatement(accountNo, startDate, endDate);
        return statements.stream().mapToDouble(LoanAccStatement::getPrincipalCredit).sum();
    }

    public double getTotalInterestPaid(String accountNo, Date startDate, Date endDate) {
        List<LoanAccStatement> statements = getLoanAccStatement(accountNo, startDate, endDate);
        return statements.stream().mapToDouble(LoanAccStatement::getInterestCredit).sum();
    }

    public BranchInfo getBranchInfo(String branchCode) {
        Map<String, Object> params = parameterManager.getBranchParams(branchCode);
        String functionName = "FXN_GET_BRANCH_INFO";
        List<BranchInfo> branchInfos = preparedStatementBasedDao.getDataFromServer1(functionName, params, BranchInfo.class);
        return branchInfos.isEmpty() ? new BranchInfo() : branchInfos.get(0);
    }

    public List<CasaAccInfo> getCasaAccountInfo(String branchCode, String accountNo) {
        Map<String, Object> params = parameterManager.getCasaOrTdrdAccountParams(branchCode, accountNo);
        String functionName = "FXN_GET_CASA_ACC_INFO";
        return preparedStatementBasedDao.getDataFromServer1(functionName, params, CasaAccInfo.class);
    }

    public List<TdrdAccInfo> getTdrdAccountInfo(String branchCode, String accountNo) {
        Map<String, Object> params = parameterManager.getCasaOrTdrdAccountParams(branchCode, accountNo);
        String functionName = "FXN_GET_TDRD_ACC_INFO";
        return preparedStatementBasedDao.getDataFromServer1(functionName, params, TdrdAccInfo.class);
    }

    public List<LienBlock> getLienBlock(String branchCode) {
        Map<String, Object> params = parameterManager.getBranchParams(branchCode);
        String functionName = "FXN_GET_LIEN_BLOCK_DETAILS";
        return preparedStatementBasedDao.getDataFromServer1(functionName, params, LienBlock.class);
    }

    public List<LienBlock> getAccountWiseLienBlock(String branchCode, String accountNo) {
        Map<String, Object> params = parameterManager.getLienBlockParams(branchCode,accountNo);
        String functionName = "FXN_GET_LIEN_BLOCK_DETAILS";
        return preparedStatementBasedDao.getDataFromServer1(functionName, params, LienBlock.class);
    }

    public List<OtherApplicants> getOtherApplicants(String accountNo) {
        Map<String, Object> params = parameterManager.getAccountParams(accountNo);
        String functionName = "FXN_GET_OTHER_APPLICANTS";
        return preparedStatementBasedDao.getDataFromServer1(functionName, params, OtherApplicants.class);
    }

    public List<AdvanceSearchDataModel> getAdvanceSearchData(AdvanceSearchPayload payload) {
        Map<String, Object> params = parameterManager.getAdvanceSearchParams(payload);
        return preparedStatementBasedDao.getAdvanceSearchData(params);
    }

//    public List test() {
//        return preparedStatementBasedDao.test();
//    }


    public List<LoanAccDetails> getRMInfoByRMCode(String accNo,String cif,String rmCode, Date date) {
        Map<String, Object> params = parameterManager.getRmInfoParams(accNo, cif,rmCode, date);
        String functionName = "RFN_GET_LOAN_LISTING";
        return preparedStatementBasedDao.getDataFromServer2(functionName, params, LoanAccDetails.class);
    }

    public List<LoanInteRateChanging> getInterestChangeHistory(String accNo) {
        Map<String, Object> params = parameterManager.getAccountParams(accNo);
        String functionName = "FXN_GET_LOAN_INT_RATE_HISTORY";
        List<LoanInteRateChanging> loanInteRateChangings = preparedStatementBasedDao.getDataFromServer1(functionName, params, LoanInteRateChanging.class);
        return loanInteRateChangings;
    }


    public LoanAccDetails getLoanListing(String accNo, String cif,  String rmCode, Date changingTime) {
        Map<String, Object> params = parameterManager.getRmInfoParams(accNo, cif ,rmCode, changingTime);
        String functionName = "RFN_GET_LOAN_LISTING";
        List<LoanAccDetails> loanAccDetails = preparedStatementBasedDao.getDataFromServer2(functionName, params, LoanAccDetails.class);
        return loanAccDetails.isEmpty() ? new LoanAccDetails() : loanAccDetails.get(0);
    }

    public List<LoanAccDetails> getLoanListingForReportByDate(String accNo, String cif,  String rmCode, Date changingTime) {
        Map<String, Object> params = parameterManager.getRmInfoParams(accNo, cif ,rmCode, changingTime);
        String functionName = "RFN_GET_LOAN_LISTING";
        List<LoanAccDetails> loanAccDetails = preparedStatementBasedDao.getDataFromServer2(functionName, params, LoanAccDetails.class);
        return loanAccDetails;
    }

    public LoanInteRateChanging getInterestRateHistory(String accNo, String rateType) {
        Map<String, Object> params = parameterManager.getInterestRateHistoryParameter(accNo, rateType);
        String functionName = "FXN_GET_LOAN_INT_RATE_HISTORY";
        List<LoanInteRateChanging> loanInteRateChangings = preparedStatementBasedDao.getDataFromServer1(functionName, params, LoanInteRateChanging.class);
        return loanInteRateChangings.isEmpty() ? new LoanInteRateChanging() : loanInteRateChangings.get(0);
    }




    private String checkFid(LoanAccDetails loanAccDetails) {
        LoanAccSchedule loanAccSchedule = getAccScheduleDetails(loanAccDetails.getAccountNumber()).stream().findFirst().orElse(null);
        Date fristPaymentDate = loanAccDetails.getFirstRepayDueDate();
        String scheduleDate = loanAccSchedule != null ? loanAccSchedule.getScheduleDueDate() : null;
        long differenceDate = differenceBetweenDates(scheduleDate, fristPaymentDate);
        if (differenceDate <= 0 && (Double.parseDouble(loanAccSchedule.getEmiAmount()) >= Double.parseDouble(loanAccSchedule.getAmountSattled()))){
            return "No";
        }
        return "Yes";
    }



    private long differenceBetweenDates(String expiryDate, Date newDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
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
}
