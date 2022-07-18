package com.csinfotechbd.collection.dashboard;

import lombok.Data;

import java.util.Optional;

@Data
public class AdvanceSearchPayload {

    private String accountNo = "";
    private String cif = ""; // customer id from client API
    private String customerName = "";
    private String fatherName = "";
    private String motherName = "";
    private String mobile = "";
    private String nationalId = "";
    private String dateOfBirth = "";
    private String organization = "";
    private String tin = "";
    private String email = "";
    private String passport = "";
    private String status  = "";
    private String dealerName = "";
    private String dealerId = "";
    private String autoDebit = "";
    private String linkAccount = "";
    private String loanId = "";
    private String clsFlag = "";
    private String clientId = "";
    private String contractId= "";
}
