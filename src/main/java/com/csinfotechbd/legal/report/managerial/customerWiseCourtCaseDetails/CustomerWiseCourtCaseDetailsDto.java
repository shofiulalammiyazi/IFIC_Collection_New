package com.csinfotechbd.legal.report.managerial.customerWiseCourtCaseDetails;

import com.csinfotechbd.utillity.StringUtils;
import lombok.Data;

import javax.persistence.Tuple;
import java.sql.Clob;
import java.util.Objects;

@Data
public class CustomerWiseCourtCaseDetailsDto {

    private String accountName;
    private String defendentName;
    private String caseFiledType;
    private String branchName;
    private String caseType;
    private String court;
    private String caseNumber;
    private String dateOfFiling;
    private String caseAmount;
    private String lawyerName;
    private String lawyerPhone;
    private String marketValue;
    private String forcedSaleValue;
    private String legalExpense;
    private String totalRecovery;
    private CustomerWiseCourtCaseDetailsHistoryData[] revisions;

    public CustomerWiseCourtCaseDetailsDto() {
    }

    public CustomerWiseCourtCaseDetailsDto(Tuple data) {
        accountName = Objects.toString(data.get("ACCOUNT_NAME"), "-");
        defendentName = Objects.toString(data.get("DEFENDENT_NAME"), "-");
        caseFiledType = Objects.toString(data.get("CASE_FILED_TYPE"), "-");
        branchName = Objects.toString(data.get("BRANCH"), "-");
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        court = Objects.toString(data.get("COURT"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        caseAmount = Objects.toString(data.get("CASE_AMOUNT"), "-");
        lawyerName = Objects.toString(data.get("LAWYER_NAME"), "-");
        lawyerPhone = Objects.toString(data.get("LAWYER_PHONE"), "-");
        marketValue = Objects.toString(data.get("MARKET_VALUE"), "-");
        forcedSaleValue = Objects.toString(data.get("FORCED_SALE_VALUE"), "-");
        legalExpense = Objects.toString(data.get("LEGAL_EXPENSE"), "-");
        totalRecovery = Objects.toString(data.get("TOTAL_RECOVERY"), "-");

        Clob clobData = (Clob) data.get("MODIFICATION_HISTORY");
        String historyData = StringUtils.clobToString(clobData);
        CustomerWiseCourtCaseDetailsHistoryData[] allData = StringUtils.jsonStringToArray(historyData, CustomerWiseCourtCaseDetailsHistoryData[].class);
        int length = Math.min(allData.length, 5);
        revisions = new CustomerWiseCourtCaseDetailsHistoryData[length];
        System.arraycopy(allData, 0, revisions, 0, length);
    }

}

@Data
class CustomerWiseCourtCaseDetailsHistoryData {
    private String caseDate;
    private String courseOfAction;
    private String blaAttendance;

}