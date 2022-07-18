package com.csinfotechbd.reports.retail.loan.listingReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingReportDto {

    private Object loanAccountNo;
    private Object oldAccountNo;
    private Object customerID;
    private Object bookingChannel;
    private Object sourceID;
    private Object sourceName;
    private Object customerName;
    private Object companyName;
    private Object customerAddress;
    private Object customerPhone;
    private Object settlementAC;
    private Object settlementAcCustomerName;
    private Object balanceSettlementAmount;
    private Object loanlimit;
    private Object loanOutstanding;
    private Object principalOutstanding;
    private Object interestOutstanding;
    private Object overdueAmount;
    private Object interestSuspense;
    private Object emi;
    private Object totalRecovaryDuringMonth;
    private Object clStatus;
    private Object mainInterestRate;
    private Object disburseDate;
    private Object downloadDate;
    private Object nextSchDate;
    private Object expiryDate;
    private Object loanStatus;
    private Object productName;
    private Object productCode;
    private Object optimaID;
    private Object branchCode;
    private Object branchName;
    private Object region;
    private Object penalInterestRate;

    public Object getLoanAccountNo() {
        return loanAccountNo;
    }

    public void setLoanAccountNo(Object loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public Object getOldAccountNo() {
        return oldAccountNo;
    }

    public void setOldAccountNo(Object oldAccountNo) {
        this.oldAccountNo = oldAccountNo;
    }

    public Object getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Object customerID) {
        this.customerID = customerID;
    }

    public Object getBookingChannel() {
        return bookingChannel;
    }

    public void setBookingChannel(Object bookingChannel) {
        this.bookingChannel = bookingChannel;
    }

    public Object getSourceID() {
        return sourceID;
    }

    public void setSourceID(Object sourceID) {
        this.sourceID = sourceID;
    }

    public Object getSourceName() {
        return sourceName;
    }

    public void setSourceName(Object sourceName) {
        this.sourceName = sourceName;
    }

    public Object getCustomerName() {
        return customerName;
    }

    public void setCustomerName(Object customerName) {
        this.customerName = customerName;
    }

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public Object getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Object customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Object getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(Object customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Object getSettlementAC() {
        return settlementAC;
    }

    public void setSettlementAC(Object settlementAC) {
        this.settlementAC = settlementAC;
    }

    public Object getSettlementAcCustomerName() {
        return settlementAcCustomerName;
    }

    public void setSettlementAcCustomerName(Object settlementAcCustomerName) {
        this.settlementAcCustomerName = settlementAcCustomerName;
    }

    public Object getBalanceSettlementAmount() {
        return balanceSettlementAmount;
    }

    public void setBalanceSettlementAmount(Object balanceSettlementAmount) {
        this.balanceSettlementAmount = balanceSettlementAmount;
    }

    public Object getLoanlimit() {
        return loanlimit;
    }

    public void setLoanlimit(Object loanlimit) {
        this.loanlimit = loanlimit;
    }

    public Object getLoanOutstanding() {
        return loanOutstanding;
    }

    public void setLoanOutstanding(Object loanOutstanding) {
        this.loanOutstanding = loanOutstanding;
    }

    public Object getPrincipalOutstanding() {
        return principalOutstanding;
    }

    public void setPrincipalOutstanding(Object principalOutstanding) {
        this.principalOutstanding = principalOutstanding;
    }

    public Object getInterestOutstanding() {
        return interestOutstanding;
    }

    public void setInterestOutstanding(Object interestOutstanding) {
        this.interestOutstanding = interestOutstanding;
    }

    public Object getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(Object overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Object getInterestSuspense() {
        return interestSuspense;
    }

    public void setInterestSuspense(Object interestSuspense) {
        this.interestSuspense = interestSuspense;
    }

    public Object getEmi() {
        return emi;
    }

    public void setEmi(Object emi) {
        this.emi = emi;
    }

    public Object getTotalRecovaryDuringMonth() {
        return totalRecovaryDuringMonth;
    }

    public void setTotalRecovaryDuringMonth(Object totalRecovaryDuringMonth) {
        this.totalRecovaryDuringMonth = totalRecovaryDuringMonth;
    }

    public Object getClStatus() {
        return clStatus;
    }

    public void setClStatus(Object clStatus) {
        this.clStatus = clStatus;
    }

    public Object getMainInterestRate() {
        return mainInterestRate;
    }

    public void setMainInterestRate(Object mainInterestRate) {
        this.mainInterestRate = mainInterestRate;
    }

    public Object getDisburseDate() {
        return disburseDate;
    }

    public void setDisburseDate(Object disburseDate) {
        this.disburseDate = disburseDate;
    }

    public Object getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(Object downloadDate) {
        this.downloadDate = downloadDate;
    }

    public Object getNextSchDate() {
        return nextSchDate;
    }

    public void setNextSchDate(Object nextSchDate) {
        this.nextSchDate = nextSchDate;
    }

    public Object getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Object expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Object getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(Object loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Object getProductName() {
        return productName;
    }

    public void setProductName(Object productName) {
        this.productName = productName;
    }

    public Object getProductCode() {
        return productCode;
    }

    public void setProductCode(Object productCode) {
        this.productCode = productCode;
    }

    public Object getOptimaID() {
        return optimaID;
    }

    public void setOptimaID(Object optimaID) {
        this.optimaID = optimaID;
    }

    public Object getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Object branchCode) {
        this.branchCode = branchCode;
    }

    public Object getBranchName() {
        return branchName;
    }

    public void setBranchName(Object branchName) {
        this.branchName = branchName;
    }

    public Object getRegion() {
        return region;
    }

    public void setRegion(Object region) {
        this.region = region;
    }

    public Object getPenalInterestRate() {
        return penalInterestRate;
    }

    public void setPenalInterestRate(Object penalInterestRate) {
        this.penalInterestRate = penalInterestRate;
    }
}
