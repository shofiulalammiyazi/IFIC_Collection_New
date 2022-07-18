package com.csinfotechbd.legal.report.bangladeshBankReports.cibWritPetition;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class CibWritPetitionDto {

    private String writPetitionNumber;
    private String defendantName;
    private String accountName;
    private String lawyerName;
    private String lawyerPhone;
    private String caseStatus;
    private String courseOfAction;
    private String remarks;

    public CibWritPetitionDto() {
    }

    public CibWritPetitionDto(Tuple data) {
        writPetitionNumber = Objects.toString(data.get("WRIT_PETITION_NUMBER"), "");
        defendantName = Objects.toString(data.get("DEFENDANT_NAME"), "");
        accountName = Objects.toString(data.get("ACCOUNT_NAME"), "");
        lawyerName = Objects.toString(data.get("LAWYER_NAME"), "");
        lawyerPhone = Objects.toString(data.get("LAWYER_PHONE"), "");
        caseStatus = Objects.toString(data.get("CASE_STATUS"), "");
        courseOfAction = Objects.toString(data.get("COURSE_OF_ACTION"), "");
        remarks = Objects.toString(data.get("REMARKS"), "");
    }
}
