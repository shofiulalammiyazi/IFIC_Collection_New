package com.csinfotechbd.legal.report.datasheets.accountWiseCaseReportUnderAraEx;

import lombok.Data;

@Data
public class AccountWiseCaseReportUnderAraExDto {

    private Integer serialNo;
    private Object branchCode;
    private Object branchName;
    private Object businessSegment;
    private Object customerCifNo;
    private Object accountNo;
    private Object nameOfAcc;
    private Object plaintiffName;
    private Object plaintiffDesignation;
    private Object plaintiffPhone;
    private Object accusedName;
    private Object accusedMobile;
    private Object filingDate;
    private Object caseNumber;
    private double caseClaimAmount;
    private Object court;
    private Object previousDate;
    private Object caseStatusOfPreviousDate;
    private Object nextDate;
    private Object caseStatusOfNextPreviousDate;
    private Object lawyers;
    private Object lawyersPhone;
    private Object writPetitionStatus;
    private double totalRecoveryFiveYearsEarlier;
    private double totalRecoveryFourYearsEarlier;
    private double totalRecoveryThreeYearsEarlier;
    private double totalRecoveryTwoYearsEarlier;
    private double totalRecoveryCurrentYear;
    private Object remarks;

    public AccountWiseCaseReportUnderAraExDto(){}


    public AccountWiseCaseReportUnderAraExDto(Object[] data, int serialNo) {
        this.serialNo = serialNo;
        this.branchCode = data[0];
        this.branchName = data[1];
        this.businessSegment = data[2];
        this.customerCifNo = data[3];
        this.accountNo = data[4];
        this.nameOfAcc = data[5];
        this.plaintiffName = data[6];
        this.plaintiffDesignation = data[7];
        this.plaintiffPhone = data[8];
        this.accusedName = data[9];
        this.accusedMobile = data[10];
        this.filingDate = data[11];
        this.caseNumber = data[12];
        this.caseClaimAmount = ((Number) data[13]).doubleValue();
        this.court = data[14];
        this.previousDate = data[15];
        this.caseStatusOfPreviousDate = data[16];
        this.nextDate = data[17];
        this.caseStatusOfNextPreviousDate = data[18];
        this.lawyers = data[19];
        this.lawyersPhone = data[20];
        this.writPetitionStatus = data[21];
        this.totalRecoveryFiveYearsEarlier = ((Number) data[22]).doubleValue();
        this.totalRecoveryFourYearsEarlier = ((Number) data[23]).doubleValue();
        this.totalRecoveryThreeYearsEarlier = ((Number) data[24]).doubleValue();
        this.totalRecoveryTwoYearsEarlier = ((Number) data[25]).doubleValue();
        this.totalRecoveryCurrentYear = ((Number) data[26]).doubleValue();
        this.remarks = data[27];
    }
}
