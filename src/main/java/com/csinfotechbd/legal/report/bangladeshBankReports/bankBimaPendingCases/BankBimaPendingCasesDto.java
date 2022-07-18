package com.csinfotechbd.legal.report.bangladeshBankReports.bankBimaPendingCases;

import com.csinfotechbd.utillity.NumberUtils;
import lombok.Data;

import javax.persistence.Tuple;

@Data
public class BankBimaPendingCasesDto {

    private String totalWritCount;
    private String totalWritCaseAmount;
    private String totalArtharinCount;
    private String totalArtharinCaseAmount;
    private String totalBankrupcyCount;
    private String totalBankrupcyCaseAmount;
    private String totalCount;
    private String totalCaseAmount;
    private String dateOfReportGeneration;

    private String othersCase;
    private String othersCaseAmount;

    public BankBimaPendingCasesDto() {
    }

    public BankBimaPendingCasesDto(Tuple data) {
        NumberUtils numberUtils = new NumberUtils();

        int totalWritCount = ((Number) data.get("TOTAL_WRIT_COUNT")).intValue();
        double totalWritCaseAmount = ((Number) data.get("TOTAL_WRIT_CASE_AMOUNT")).doubleValue();
        int totalArtharinCount = ((Number) data.get("TOTAL_ARTHARIN_COUNT")).intValue();
        double totalArtharinCaseAmount = ((Number) data.get("TOTAL_ARTHARIN_CASE_AMOUNT")).doubleValue();
        int totalBankrupcyCount = ((Number) data.get("TOTAL_BANKRUPCY_COUNT")).intValue();
        double totalBankrupcyCaseAmount = ((Number) data.get("TOTAL_BANKRUPCY_CASE_AMOUNT")).doubleValue();
        int totalCount = totalWritCount + totalArtharinCount + totalBankrupcyCount;
        double totalCaseAmount = totalWritCaseAmount + totalArtharinCaseAmount + totalBankrupcyCaseAmount;

        this.totalWritCount = numberUtils.convertToBanglaNumber(totalWritCount);
        this.totalWritCaseAmount = numberUtils.convertToBanglaNumber(totalWritCaseAmount);
        this.totalArtharinCount = numberUtils.convertToBanglaNumber(totalArtharinCount);
        this.totalArtharinCaseAmount = numberUtils.convertToBanglaNumber(totalArtharinCaseAmount);
        this.totalBankrupcyCount = numberUtils.convertToBanglaNumber(totalBankrupcyCount);
        this.totalBankrupcyCaseAmount = numberUtils.formatBdtInEnglish(totalBankrupcyCaseAmount);
        this.totalCount = numberUtils.formatBdtInEnglish(totalCount);
        this.totalCaseAmount = numberUtils.formatBdtInEnglish(totalCaseAmount);

    }

}
