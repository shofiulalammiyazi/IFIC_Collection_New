package com.csinfotechbd.legal.report.managerial.pendingCourtCasesMonitoring;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class PendingCourtCasesMonitoringDto {

    private String branchName;
    private String nosOfArsName;
    private Double artharinRelatedSuitValue;
    private String nosOfExCaseName;
    private Double exCaseRelatedSuitValue;
    private String totalNumberOfArtharinExecutionSuit;
    private Double totalSuitValue;
    private String nosOfNiAct;
    private Double chequeAmount;
    private String nosOfMonoyAgainstBank;

    private String writPetitionNum;


    public PendingCourtCasesMonitoringDto(){

    }

    public PendingCourtCasesMonitoringDto(Tuple data){
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        nosOfArsName = Objects.toString(data.get("NOS_OF_ARS_NAME"), "-");
        artharinRelatedSuitValue = ((Number) Optional.ofNullable(data.get("ARTHARIN_RELATED_SUIT_VALUE")).orElse(0)).doubleValue();
        nosOfExCaseName = Objects.toString(data.get("NOS_OF_EX_CASE_NAME"), "-");
        exCaseRelatedSuitValue = ((Number) Optional.ofNullable(data.get("EX_CASE_RELATED_SUIT_VALUE")).orElse(0)).doubleValue();
        totalNumberOfArtharinExecutionSuit = Objects.toString(data.get("TOTAL_NUMBER_OF_ARTHARIN_EXECUTION_SUIT"), "-");
        totalSuitValue = ((Number) Optional.ofNullable(data.get("TOTAL_SUIT_VALUE")).orElse(0)).doubleValue();
        nosOfNiAct = Objects.toString(data.get("NOS_OF_NI_ACT"), "-");
        chequeAmount = ((Number) Optional.ofNullable(data.get("CHEQUE_AMOUNT")).orElse(0)).doubleValue();
        nosOfMonoyAgainstBank = Objects.toString(data.get("NOS_OF_MONEY_SUIT_AGAINST_BANK"), "-");
        writPetitionNum = Objects.toString(data.get("WRIT_PETITION_NUM"), "-");

    }

}
