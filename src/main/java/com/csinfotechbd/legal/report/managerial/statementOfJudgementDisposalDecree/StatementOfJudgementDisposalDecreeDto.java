package com.csinfotechbd.legal.report.managerial.statementOfJudgementDisposalDecree;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class StatementOfJudgementDisposalDecreeDto {

    private String caseType;
    private String branchName;
    private String accountNumber;
    private String caseNumber;
    private String dateOfFiling;
    private String orderDate;
    private String action;

    public StatementOfJudgementDisposalDecreeDto() {
    }

    public StatementOfJudgementDisposalDecreeDto(Tuple data) {
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        accountNumber = Objects.toString(data.get("ACCOUNT_NUMBER"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        orderDate = Objects.toString(data.get("ORDER_DATE"), "-");
        action = Objects.toString(data.get("ACTION"), "-");
    }
}
