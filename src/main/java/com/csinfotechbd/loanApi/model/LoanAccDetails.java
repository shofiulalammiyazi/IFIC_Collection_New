package com.csinfotechbd.loanApi.model;

import com.csinfotechbd.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;



@Data
public class LoanAccDetails implements BaseLoanApiModel {

    private String expiryDate;
    private double emiAmount;
    private String userDefineEmi;
    private String productCode;
    private long overdueDays;
    private double totalAmountPaid;
    private Date lastPaymentDate;
    private double amountPaidLastMnth;
    private String sattlementLoanAc;
    private double interestRate;
    private String sectorCode;
    private double overdue;
    private String ecoPurposeCode;
    private Date firstRepayDueDate;
    private double limitAmount;
    private String sectCode;
    private String groupName;
    private String liabilityNo;
    private String smeCode;
    private String envRisk;
    private double outStandingLocalCurrency;
    private double interestOutstanding;
    private String latestApprovalDate;
    private String installmentFrequence;
    private String noOfIntallmentDue;
    private String noOfIntallmentPaid;
    private double sattlementAcBal;
    private String previousAccountNo;
    private Date scheduleNextDate;
    private double interestSuspence;
    private String status1;
    private String numberOfRescheduled;
    private long liveDpd;

    //extra field for passing error in services
    private double overdueAmount;
    private double principalOutstanding;
    private String accountNumber;
    private String accountName;
    private String customerCifNumber;
    private String branchCode;
    private String productDesc;
    private String disbursementDate;

    private String proddes;
    private String maturityDate;
    private double dpdBucket; // For report ceil the value, otherwise 2 precision
    private long loanExpiryDay;
    private String sectDesc;
    private String altAccNo;
    private String valueDate;
    private Date generationDate;
    private double actualTenor;
    private long totalTenor;
    private String fid;



    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.expiryDate = ResultSetExtractor.getValueFromResultSet(data, "MATURITY_DATE", "-");
        this.emiAmount = ResultSetExtractor.getDoubleFromResultSet(data, "EMI_AMOUNT", 0D);
        this.userDefineEmi = ResultSetExtractor.getValueFromResultSet(data, "USER_DEFINE_EMI", "-");
        this.productCode = ResultSetExtractor.getValueFromResultSet(data, "PRODUCT_CODE", "-");
        this.overdueDays = ResultSetExtractor.getLongFromResultSet(data, "OVERDUE_DAYS", 0L);
        this.totalAmountPaid = ResultSetExtractor.getDoubleFromResultSet(data, "TOTAL_AMOUNT_PAID", 0D);
        this.lastPaymentDate = ResultSetExtractor.getDateFromResultSet(data, "LAST_PAYMENT_DATE", null);
        this.amountPaidLastMnth = ResultSetExtractor.getDoubleFromResultSet(data, "AMOUNT_PAID_LAST_MNTH", 0D);
        this.sattlementLoanAc = ResultSetExtractor.getValueFromResultSet(data, "SATTLEMENT_LOAN_AC", "-");
        this.interestRate = ResultSetExtractor.getDoubleFromResultSet(data, "INTEREST_RATE", 0);
        this.sectorCode = ResultSetExtractor.getValueFromResultSet(data, "SECT_CODE", "-");
        this.overdue = ResultSetExtractor.getDoubleFromResultSet(data, "OVERDUE", 0D);
        this.ecoPurposeCode = ResultSetExtractor.getValueFromResultSet(data, "ECO_PURPOSE_CODE", "-");
        this.firstRepayDueDate = ResultSetExtractor.getDateFromResultSet(data, "FIRST_REPAY_DUE_DATE", null);
        this.limitAmount = ResultSetExtractor.getDoubleFromResultSet(data, "LIMIT_AMOUNT", 0D);
        this.sectCode = ResultSetExtractor.getValueFromResultSet(data, "SECT_CODE", "-");
        this.groupName = ResultSetExtractor.getValueFromResultSet(data, "GROUP_NAME", "-");
        this.liabilityNo = ResultSetExtractor.getValueFromResultSet(data, "LIABILITY_NO", "-");
        this.smeCode = ResultSetExtractor.getValueFromResultSet(data, "SME_CODE", "-");
        this.envRisk = ResultSetExtractor.getValueFromResultSet(data, "ENV_RISK", "-");
        this.outStandingLocalCurrency = ResultSetExtractor.getDoubleFromResultSet(data, "OUTSTANDING_LOCAL_CURRENCY", 0D);
        this.interestOutstanding = ResultSetExtractor.getDoubleFromResultSet(data, "INTEREST_OUTSTANDING", 0D);
        this.latestApprovalDate = ResultSetExtractor.getValueFromResultSet(data, "LATEST_APPROVAL_DATE", "-");
        this.installmentFrequence = ResultSetExtractor.getValueFromResultSet(data, "INSTALLMENT_FREQUENCE", "-");
        this.noOfIntallmentDue = ResultSetExtractor.getValueFromResultSet(data, "NO_OF_INTALLMENT_DUE", "-");
        this.noOfIntallmentPaid = ResultSetExtractor.getValueFromResultSet(data, "NO_OF_INTALLMENT_PAID", "-");
        this.sattlementAcBal = ResultSetExtractor.getDoubleFromResultSet(data, "SATTLEMENT_AC_BAL", 0D);
        this.previousAccountNo = ResultSetExtractor.getValueFromResultSet(data, "PREVIOUS_ACCOUNT_NO", "-");
//        this.scheduleNextDate = getDateFormat(ResultSetExtractor.getDateFromResultSet(data, "SCHEDULE_NEXT_DATE", null));
        this.scheduleNextDate = ResultSetExtractor.getDateFromResultSet(data, "SCHEDULE_NEXT_DATE", null);
        this.interestSuspence = ResultSetExtractor.getDoubleFromResultSet(data, "INTEREST_SUSPENCE", 0D);
        this.status1 = ResultSetExtractor.getValueFromResultSet(data, "STATUS1", "-");
        this.numberOfRescheduled = ResultSetExtractor.getValueFromResultSet(data, "NUMBER_OF_RESCHEDULED", "-");
        this.principalOutstanding = ResultSetExtractor.getDoubleFromResultSet(data, "OUTSTANDING_LOCAL_CURRENCY", 0D);
        this.accountNumber = ResultSetExtractor.getValueFromResultSet(data, "CONT_REF_NO", "-");
        this.accountName = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_NAME", "-");
        this.customerCifNumber = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_ID", "-");
        this.branchCode = ResultSetExtractor.getValueFromResultSet(data, "BRANCH_CODE", "-");
        this.proddes = ResultSetExtractor.getValueFromResultSet(data, "PRODDES", "-");
        this.maturityDate = ResultSetExtractor.getValueFromResultSet(data, "MATURITY_DATE", "-");
        this.disbursementDate = ResultSetExtractor.getValueFromResultSet(data, "VALUE_DATE", "-");
        this.loanExpiryDay = getExpairyDay(this.expiryDate, new Date());
//        this.sectDesc = ResultSetExtractor.getValueFromResultSet(data, "SECT_DESC", "-");
        this.altAccNo = ResultSetExtractor.getValueFromResultSet(data, "ALT_ACC_NO", "-");

        this.liveDpd = overdueDays + 1;
        this.valueDate = ResultSetExtractor.getValueFromResultSet(data, "VALUE_DATE", null);
        this.generationDate = ResultSetExtractor.getDateFromResultSet(data, "GENERATION_DATE", null);
        this.actualTenor = ResultSetExtractor.getDoubleFromResultSet(data, "ACTUAL_TENOR", 0D);
        this.totalTenor = ResultSetExtractor.getLongFromResultSet(data, "TOTAL_TENOR", 0);

        if(loanExpiryDay > 0){
            this.dpdBucket = new BigDecimal(overdueDays).divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).doubleValue();
        }else if (emiAmount > 0) {
            BigDecimal overdueDecimal = new BigDecimal(overdue);
            BigDecimal emiDecimal = new BigDecimal(emiAmount);
            this.dpdBucket = overdueDecimal.divide(emiDecimal, 2, RoundingMode.HALF_UP).doubleValue();
        }
    }

    private long getExpairyDay(String expiryDate, Date newDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date formatedExpiryDate = simpleDateFormat.parse(expiryDate);
            if (formatedExpiryDate.compareTo(newDate) < 0) {
                return ChronoUnit.DAYS.between(formatedExpiryDate.toInstant(), newDate.toInstant());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Date getDateFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date formatedDate = new Date();
        if (date == null){
            return null;
        }
        String newDate = simpleDateFormat.format(date);
        try {
            Date date1 = simpleDateFormat.parse(newDate);
            formatedDate = date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formatedDate);
        return formatedDate;
    }


    private String getFidValue(Date firstRepayDueDate, LoanAccSchedule loanAccSchedule) {
        Date fristPaymentDate = firstRepayDueDate;
        String scheduleDate = loanAccSchedule != null ? loanAccSchedule.getScheduleDueDate() : null;
        long differenceDate = getExpairyDay(scheduleDate, fristPaymentDate);
        if (differenceDate <= 0 && (Double.parseDouble(loanAccSchedule.getEmiAmount()) >= Double.parseDouble(loanAccSchedule.getAmountSattled()))){
            return "No";
        }
        return "Yes";
    }





}
