package com.csinfotechbd.legal.report.bangladeshBankReports.statementOfSuitsFiledByAgainstTheBank;

import lombok.Data;

import javax.persistence.Tuple;
import java.math.BigDecimal;

@Data
public class StatementOfSuitsFiledByAgainstTheBankDto {

    private int totalArtharinCountWithinFiveYear;
    private int totalArtharinCountBeforeFiveYear;
    private BigDecimal totalArtharinCaseAmountWithinFiveYear;
    private BigDecimal totalArtharinCaseAmountBforeFiveYear;
    private int totalMoneySuitCount;
    private BigDecimal totalMoneySuitCaseAmount;

    public StatementOfSuitsFiledByAgainstTheBankDto() {
    }

    public StatementOfSuitsFiledByAgainstTheBankDto(Tuple data) {
        totalArtharinCountWithinFiveYear = ((Number) data.get("TOTAL_ARTHARIN_COUNT_WITHIN_FIVE_YEAR")).intValue();
        totalArtharinCountBeforeFiveYear = ((Number) data.get("TOTAL_ARTHARIN_COUNT_BEFORE_FIVE_YEAR")).intValue();
        totalArtharinCaseAmountWithinFiveYear = data.get("TOTAL_ARTHARIN_CASE_AMOUNT_WITHIN_FIVE_YEAR", BigDecimal.class);
        totalArtharinCaseAmountBforeFiveYear = data.get("TOTAL_ARTHARIN_CASE_AMOUNT_BFORE_FIVE_YEAR", BigDecimal.class);
        totalMoneySuitCount = ((Number) data.get("TOTAL_MONEY_SUIT_COUNT")).intValue();
        totalMoneySuitCaseAmount = data.get("TOTAL_MONEY_SUIT_CASE_AMOUNT", BigDecimal.class);
    }
}
