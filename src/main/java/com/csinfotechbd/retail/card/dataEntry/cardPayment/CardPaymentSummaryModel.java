package com.csinfotechbd.retail.card.dataEntry.cardPayment;

import com.csinfotechbd.utillity.StringUtils;
import com.google.gson.Gson;
import lombok.Data;

import javax.persistence.Tuple;
import java.sql.Clob;
import java.util.Objects;
import java.util.Optional;

@Data
public class CardPaymentSummaryModel {
    
    private String category;
    private int currentMonthTotalAccount;
    private double currentMonthTotalPayment;
    private int lastDateTotalAccount;
    private double lastDateTotalPayment;
    private AccountWiseCardPaymentData[] paymentDetails;
    
    public CardPaymentSummaryModel(){
    
    }
    
    public CardPaymentSummaryModel(String narration){
        this.category = narration;
    }
    
    public void setFieldValuesFromTuple(Tuple data) {
        
        category = Objects.toString(data.get("category"), "-");
        currentMonthTotalAccount = ((Number) Optional.ofNullable(data.get("currentMonthTotalAccount")).orElse(0)).intValue();
        currentMonthTotalPayment = ((Number) Optional.ofNullable(data.get("currentMonthTotalPayment")).orElse(0)).doubleValue();
        lastDateTotalAccount = ((Number) Optional.ofNullable(data.get("lastDateTotalAccount")).orElse(0)).intValue();
        lastDateTotalPayment = ((Number) Optional.ofNullable(data.get("lastDateTotalPayment")).orElse(0)).doubleValue();
        
        try {
            Clob followUpDetails = (Clob) data.get("paymentDetails");
            String dataListString = StringUtils.clobToString(followUpDetails);
            paymentDetails = new Gson().fromJson(dataListString, AccountWiseCardPaymentData[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

@Data
class AccountWiseCardPaymentData {
    private String accountNo;
    private String accountName;
    private double currentMonthPayment;
    private double lastDatePayment;
    private double overdue;
    private double outstanding;
    private String dealerName;
    private String lastPaymentDate;
}
