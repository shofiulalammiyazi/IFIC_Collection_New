package com.csinfotechbd.legal.report.datasheets.pendingCourtCasesMonitoring;

import lombok.Data;

@Data
public class CaseMonitoringPendingCourtCasesReportDto {

    private Integer serial;
    private Object branchCode;
    private Object branchName;

    private Object nosOfARS;
    private Object artharinRelatedSuitValue;

    private Object nosOfExCase;
    private Object exCaseRelatedSuitValue;

    private Object totalNumberOfArtharinExecutionSuit;
    private Object totalSuitValue;

    private Object nosOfNIAct;
    private Object chequeAmount;

    private Object nosOfMoneySuitAgainstBank;
    private Object amountAgainstBank;

    private Object numberOfWritPetition;
    private Object amount;
    private Object remarks;

}
