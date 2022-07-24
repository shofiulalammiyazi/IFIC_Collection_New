package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by
 * Created at 30 December 2020
 */
@Data
public class LoanAccSchedule implements BaseLoanApiModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String scheduleDueDate;   // use for fid
    private String emiAmount;
    private String amountDue;
    private String principal;
    private String totalDueDisplay;
    private Double overdueInt;
    private String totOverdue;
    private String amountSattled;
    private String totalDue;
    private double totalOverdue;
    private double penalCharge;
    private double penalForcast;
//    private Date

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        try {
            this.scheduleDueDate = ResultSetExtractor.getValueFromResultSet(data, "SCHEDULE_DUE_DATE", "-");
            this.emiAmount = ResultSetExtractor.getValueFromResultSet(data, "EMI_AMOUNT", "-");
            this.amountDue = ResultSetExtractor.getValueFromResultSet(data, "AMOUNT_DUE", "-");
            this.principal = ResultSetExtractor.getValueFromResultSet(data, "PRINCIPAL", "-");
            this.totalDueDisplay = ResultSetExtractor.getValueFromResultSet(data, "TOTAL_DUE_DISPLAY", "-");
            this.overdueInt = ResultSetExtractor.getDoubleFromResultSet(data, "OVERDUE_INT", 0.0);
            this.totOverdue = ResultSetExtractor.getValueFromResultSet(data, "TOT_OVERDUE", "-");
            this.amountSattled = ResultSetExtractor.getValueFromResultSet(data, "AMOUNT_SATTLED", "-");
            this.totalDue = ResultSetExtractor.getValueFromResultSet(data, "TOTAL_DUE", "-");
            this.totalOverdue = ResultSetExtractor.getDoubleFromResultSet(data, "TOTAL_OVERDUE", 0.0);
        }catch (Exception e){

        }
    }




    private Date formatStringToDateType(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
}
