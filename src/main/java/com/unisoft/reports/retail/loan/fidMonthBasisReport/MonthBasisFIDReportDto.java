package com.unisoft.reports.retail.loan.fidMonthBasisReport;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class MonthBasisFIDReportDto {

    private String totalCount;
    private String reportFidCount;
    private String fidSum;
    private String salaried;
    private String landlord;
    private String businessman;

    public MonthBasisFIDReportDto(){

    }

    public MonthBasisFIDReportDto(Tuple data){
        totalCount  =   Objects.toString(data.get("TOTAL"), "0");
        reportFidCount =  Objects.toString(data.get("REPORT_FID"), "0");
        fidSum = Objects.toString(data.get("FID_SUM"), "0");
        salaried = Objects.toString(data.get("SALARIED"), "0");
        landlord = Objects.toString(data.get("LANDLORD"), "0");
        businessman = Objects.toString(data.get("BUSINESSMAN"), "0");
    }

}
