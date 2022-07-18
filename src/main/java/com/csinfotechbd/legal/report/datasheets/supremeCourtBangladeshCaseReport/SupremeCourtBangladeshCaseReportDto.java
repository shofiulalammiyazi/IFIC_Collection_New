package com.csinfotechbd.legal.report.datasheets.supremeCourtBangladeshCaseReport;

import lombok.Data;

@Data
public class SupremeCourtBangladeshCaseReportDto {

    private Object caseLabel;
    private Object courseOfActionOthers;
    private Object nameOfReport;

    private Object totalAc = 0;
    private Object totalWOffAmt = 0;
    private Object totalRecovery = 0;
    private Object totalFillingAmt = 0;
    private Object totalSettledAmt = 0;
    private Object totalOutstanding = 0;

    private Object cbdAc = 0;
    private Object cbdWOffAmt = 0;
    private Object cbdTotalRecovery = 0;
    private Object cbdFillingAmt = 0;
    private Object cbdSettledAmt = 0;
    private Object cbdOutstanding = 0;

    private Object smeAc = 0;
    private Object smeWOffAmt = 0;
    private Object smeTotalRecovery = 0;
    private Object smeFillingAmt = 0;
    private Object smeSettledAmt = 0;
    private Object smeOutstanding = 0;

    private Object rbdAc = 0;
    private Object rbdWOffAmt = 0;
    private Object rbdTotalRecovery = 0;
    private Object rbdFillingAmt = 0;
    private Object rbdSettledAmt = 0;
    private Object rbdOutstanding = 0;

    private Object cardAc = 0;
    private Object cardWOffAmt = 0;
    private Object cardTotalRecovery = 0;
    private Object cardFillingAmt = 0;
    private Object cardSettledAmt = 0;
    private Object cardOutstanding = 0;

    SupremeCourtBangladeshCaseReportDto() {

    }

    SupremeCourtBangladeshCaseReportDto(String nameOfReport) {
        this.nameOfReport = nameOfReport;
    }
}
