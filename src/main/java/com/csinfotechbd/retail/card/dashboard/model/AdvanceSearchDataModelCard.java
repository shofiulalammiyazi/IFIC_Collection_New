package com.csinfotechbd.retail.card.dashboard.model;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.Objects;

@Data
public class AdvanceSearchDataModelCard {
    private String accountNo;
    private String cifNo;
    private String customerName;
    private String dob;
    private String mobileNo;
    private String outstanding;
    private String overdue;
    private String linkProxyAcc;
    private String mothersName;
    private String email;
    private String acStatus;
    private String nid;
    private String homePhone;
    private String workPhone;
    
    public AdvanceSearchDataModelCard(){
    
    }
    
    public AdvanceSearchDataModelCard(Tuple tuple){
        customerName=Objects.toString(tuple.get("NAME_ON_CARD"), "-");
        accountNo=Objects.toString(tuple.get("ACCOUNT_NO"), "-");
        dob=Objects.toString(tuple.get("DOB"), "-");
        mobileNo=Objects.toString(tuple.get("MOBILE_NO"), "-");
        mothersName=Objects.toString(tuple.get("MOTHER_NAME"), "-");
        email=Objects.toString(tuple.get("EMAIL"), "-");
        nid=Objects.toString(tuple.get("NID"), "-");
    }
}
