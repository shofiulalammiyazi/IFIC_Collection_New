package com.csinfotechbd.legal.report.datasheets.accountWiseCaseReportUnderSupremeCourt;

import lombok.Data;

@Data
public class AccountWiseCaseReportUnderSupremeCourtDto {

    private int serialNo;
    private Object ldNo;
    private Object branchCode;
    private Object branchName;
    private Object customerCifNo;
    private Object accountNoIf;
    private Object nameOfAcc;
    private Object accusedName;
    private Object accusedMobile;
    private Object businessSegment;
    private Object caseType;
    private Object petitionerName;
    private Object oppositePartyName;
    private Object district;
    private Object caseNumber;
    private Object dateOfFiling;
    private Object byWhomFiledByBank;
    private Object byWhomFiled;
    private Object subjectMatterOfCase;
    private Object firstOrderDate;
    private Object hearingDate;
    private Object courseOfAction;
    private Object blaAttendance;
    private Object amountInvolved;
    private Object legalExpense;
    private Object lawyers;
    private Object lawyersPhone;
    private Object lawyersChange;
    private Object artharinSuitNo;
    private Object niActCaseNo;
    private Object otherSuitCase;
    private Object status;
    private Object caseStatus;
    private Object remarks;

    public AccountWiseCaseReportUnderSupremeCourtDto() {
    }
//
//    public AccountWiseCaseReportUnderSupremeCourtDto(LitigationCaseInfo caseInfo, User user, @NonNull CustomerBasicInfoEntity customer) {
//        this.ldNo = caseInfo.getLdNo();
//        this.branchCode = caseInfo.getBranchCode();
//        this.branchName = caseInfo.getBranchName();
//        this.customerCifNo = caseInfo.getCustomerCifNo();
//        this.accountNoIf = caseInfo.getCustomerAccNum();
//        this.nameOfAcc = caseInfo.getNameOfAcc();
//        this.accusedName = caseInfo.getAccusedName();
//        this.accusedMobile = customer.getMobileNumber();
//        this.businessSegment = caseInfo.getBusinessSegment();
//        this.caseType = caseInfo.getCaseType().getName();
//        this.petitionerName = caseInfo.getPetitionerName();
//        this.oppositePartyName = caseInfo.getOppositePartyName();
//        this.district = caseInfo.getDistrict();
//        this.caseNumber = caseInfo.getCaseNumber();
//        this.dateOfFiling = caseInfo.getDateOfFiling();
//        this.byWhomFiledByBank = user.getFirstName() + " " + user.getLastName();
//        this.byWhomFiled = caseInfo.getOppositePartyName();
//        this.subjectMatterOfCase = caseInfo.setSubjectMatterOfCase();
//        this.firstOrderDate = caseInfo.getFirstOrderDate();
//        this.hearingDate = caseInfo.getHearingDate();
//        this.courseOfAction = caseInfo.getCourseOfAction();
//        this.blaAttendance = caseInfo.getBlaAttendance();
//        this.amountInvolved = caseInfo.getAmountInvolved();
//        this.legalExpense = caseInfo.getLegalExpense();
//        this.lawyers = caseInfo.getLawyer().stream().map(Lawyer::getName).collect(Collectors.toList()).toString();
//        this.lawyersPhone = caseInfo.getLawyer().stream().map(Lawyer::getMobileNo).collect(Collectors.toList()).toString();
//        this.lawyersChange = ;
//        this.artharinSuitNo =;
//        this.niActCaseNo =;
//        this.otherSuitCase =;
//        this.status =;
//        this.caseStatus =;
//        this.remarks =;
//    }
//

    public AccountWiseCaseReportUnderSupremeCourtDto(Object[] data, int serialNo) {
        this.serialNo = serialNo;
        this.ldNo = data[0];
        this.branchCode = data[1];
        this.branchName = data[2];
        this.customerCifNo = data[3];
        this.accountNoIf = data[4];
        this.nameOfAcc = data[5];
        this.accusedName = data[6];
        this.accusedMobile = data[7];
        this.businessSegment = data[8];
        this.caseType = data[9];
        this.petitionerName = data[10];
        this.oppositePartyName = data[11];
        this.district = data[12];
        this.caseNumber = data[13];
        this.dateOfFiling = data[14];
        this.byWhomFiledByBank = data[15];
        this.byWhomFiled = data[16];
        this.subjectMatterOfCase = data[17];
        this.firstOrderDate = data[18];
        this.hearingDate = data[19];
        this.courseOfAction = data[20];
        this.blaAttendance = data[21];
        this.amountInvolved = data[22];
        this.legalExpense = data[23];
        this.lawyers = data[24];
        this.lawyersPhone = data[25];
        this.lawyersChange = data[26];
        this.artharinSuitNo = data[27];
        this.niActCaseNo = data[28];
        this.otherSuitCase = data[29];
        this.status = data[30];
        this.caseStatus = data[31];
        this.remarks = data[32];
    }
}
