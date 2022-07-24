package com.unisoft.reports.retail.loan.clReportMonthBasis;

import lombok.Data;

import javax.persistence.Tuple;

@Data
public class MonthBasisCLReportDto {


    private Object status;
    private Object noOfAcc;
    private Object outstanding;
    private Object bucket;

    public MonthBasisCLReportDto(Tuple tuple) {
        this.status = String.valueOf(tuple.get("clStatus"));
        this.noOfAcc = tuple.get("accCount") ;
        this.outstanding =  tuple.get("outstanding");
        this.bucket = tuple.get("bucket");
    }

    //    private String status;
//    private Integer accountNo = 0;
//    private Double amount = 0.00;
//    private Double previousAmount = 0.00;
//    private Double changeValue = 0.00;
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Integer getAccountNo() {
//        return accountNo;
//    }
//
//    public void setAccountNo(Integer accountNo) {
//        this.accountNo = accountNo;
//    }
//
//    public Double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }
//
//    public Double getPreviousAmount() {
//        return previousAmount;
//    }
//
//    public void setPreviousAmount(Double previousAmount) {
//        this.previousAmount = previousAmount;
//    }
//
//    public MonthBasisCLReportDto(){
//
//    }
//
//    public MonthBasisCLReportDto(String status, Integer accountNo, Double amount, Double previousAmount) {
//        this.status = status;
//        this.accountNo = accountNo;
//        this.amount = amount == null ? 0 : amount;
//        this.previousAmount = previousAmount == null ? 0 : previousAmount;
//        this.changeValue = this.amount - this.previousAmount;
//    }
}
