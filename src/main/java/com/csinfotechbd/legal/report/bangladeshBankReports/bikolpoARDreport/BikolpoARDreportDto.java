package com.csinfotechbd.legal.report.bangladeshBankReports.bikolpoARDreport;

import lombok.Data;

import javax.persistence.Tuple;

@Data
public class BikolpoARDreportDto {

    private int totalPendingCases;
    private double totalAmountOfPendingCases;
    private int totalAdjustedCases;
    private double totalAmountOfAdjustedCases;
    private double totalRecoveryAfterCaseFromAdjustedCases;

    public BikolpoARDreportDto() {
    }

    public BikolpoARDreportDto(Tuple data) {
        totalPendingCases += ((Number) data.get("TOTAL_PENDING")).intValue();
        totalAmountOfPendingCases += ((Number) data.get("TOTAL_CASE_AMOUNT_PENDING")).doubleValue();
        totalAdjustedCases += ((Number) data.get("TOTAL_ADJUSTED")).intValue();
        totalAmountOfAdjustedCases += ((Number) data.get("TOTAL_CASE_AMOUNT_ADJUSTED")).doubleValue();
        totalRecoveryAfterCaseFromAdjustedCases += ((Number) data.get("TOTAL_RECOVERY_AFTER_CASE")).doubleValue();
    }
}
