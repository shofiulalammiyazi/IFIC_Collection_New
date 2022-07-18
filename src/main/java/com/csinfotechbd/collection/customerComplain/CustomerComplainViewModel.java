package com.csinfotechbd.collection.customerComplain;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class CustomerComplainViewModel {

    private String id;
    private String custDate;
    private String custTime;
    private String reqThough;
    private String reqDetails;
    private String mobileNo;
    private String reqTime;
    private String status;
    private String dealerPin;
    private String fileName;
    private String dmsfileId;
    private String dmsFileType;
    private String accountNo;
    private String accountName;
    private String clStatus;
    private String assignee;
    private String assigneePin;
    private String assigneeNote;


    public CustomerComplainViewModel(Tuple t) {
        this.id = Objects.toString(t.get("ID").toString(),"-");
        this.custDate = Objects.toString(t.get("CUST_DATE"),"-");
        this.custTime = Objects.toString(t.get("CUST_TIME"),"-");
        this.reqThough = Objects.toString(t.get("REQ_THOUGH"),"-");
        this.reqDetails = Objects.toString(t.get("REQ_DETAILS"),"-");
        this.mobileNo = Objects.toString(t.get("MOBILE_NO"),"-");
        this.reqTime = Objects.toString(t.get("REQ_TIME"),"-");
        this.status = Objects.toString(t.get("STATUS"),"-");
        this.dealerPin = Objects.toString(t.get("DEALER_PIN"),"-");
        this.accountNo = Objects.toString(t.get("ACCOUNT_NO"),"-");
        this.accountName = Objects.toString(t.get("CUSTOMER_NAME"),"-");
        this.clStatus = Objects.toString(t.get("CL_STATUS"),"-");
        this.assignee = Objects.toString(t.get("ASSIGNEE"),"-");
        this.assigneePin = Objects.toString(t.get("ASSIGNEE_PIN"),"-");
        this.assigneeNote = Objects.toString(t.get("ASSIGNEE_NOTE"),"-");
    }
}
