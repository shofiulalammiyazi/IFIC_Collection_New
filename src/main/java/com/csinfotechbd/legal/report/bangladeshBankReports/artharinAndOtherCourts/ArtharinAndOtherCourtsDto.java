package com.csinfotechbd.legal.report.bangladeshBankReports.artharinAndOtherCourts;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class ArtharinAndOtherCourtsDto {

    private String courtName;
    private int totalCases;
    private double totalCaseAmount;
    private int totalAdjustedCount;
    private double totalAdjustedAmount;
    private double totalAdjustedRecoveryAmount;
    private int totalPendingCount;
    private double totalPendingAmount;
    private double totalPendingRecoveryAmount;

    public void setValuesFromTuple(Tuple data) {
      /*  courtName = Objects.toString(data.get("COURT_NAME"), "");
        totalAdjustedCount += ((Number) data.get("TOTAL_ADJUSTED_COUNT")).intValue();
        totalAdjustedAmount += (((Number) data.get("TOTAL_ADJUSTED_AMOUNT")).doubleValue() / 100000);
        totalAdjustedRecoveryAmount += (((Number) data.get("TOTAL_ADJUSTED_RECOVERY_AMOUNT")).doubleValue() / 100000);
        totalPendingCount += ((Number) data.get("TOTAL_PENDING_COUNT")).intValue();
        totalPendingAmount += (((Number) data.get("TOTAL_PENDING_AMOUNT")).doubleValue() / 100000);
        totalPendingRecoveryAmount += (((Number) data.get("TOTAL_PENDING_RECOVERY_AMOUNT")).doubleValue() / 100000);
        totalCases = totalAdjustedCount + totalPendingCount;
        totalCaseAmount = totalAdjustedAmount + totalPendingAmount;*/

        courtName = Objects.toString(data.get("COURT_NAME"), "");
        totalAdjustedCount += ((Number) data.get("TOTAL_ADJUSTED_COUNT")).intValue();
        totalAdjustedAmount += (((Number) data.get("TOTAL_ADJUSTED_AMOUNT")).doubleValue() / 100000);
        totalAdjustedRecoveryAmount += (((Number) data.get("TOTAL_ADJUSTED_RECOVERY_AMOUNT")).doubleValue() / 100000);
        totalPendingCount += ((Number) data.get("TOTAL_PENDING_COUNT")).intValue();
        totalPendingAmount += (((Number) data.get("TOTAL_PENDING_AMOUNT")).doubleValue() / 100000);
        totalPendingRecoveryAmount += (((Number) data.get("TOTAL_PENDING_RECOVERY_AMOUNT")).doubleValue() / 100000);
        totalCases = totalAdjustedCount + totalPendingCount;
        totalCaseAmount = totalAdjustedAmount + totalPendingAmount;
    }
}
