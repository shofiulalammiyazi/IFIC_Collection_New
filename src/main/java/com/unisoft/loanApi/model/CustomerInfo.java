package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;


/**
 * Created by
 * Created at 30 December 2020
 */
@Data
public class CustomerInfo implements BaseLoanApiModel{

    private String custNo;
    private String customerName;
    private String employer;
    private String dateOfBirth;
    private String gender;
    private String proprietorName;
    private String correspondenceAddress;
    private String permanentAddress;
    private String homePhone;
    private String workPhone;
    private String mobileNumber;
    private String fatherName;
    private String motherName;
    private String spouseName;
    private String passportNumber;
    private String nationalId;
    private String eMail;
    private String acDesc;

    //    new propertise
    private String tinNo;
    private String location;
    private String creditRating;
    //    private String rmCode;
//    private String customerIdOfBorrower;
    private String cifStatus;
    private String customerType;
    private String accountType;
    private String customerCategory; // Provided as Company Category by UCBL IT

    private String secCode;
    private String secDesc;
    private String smeCode;
    private String smeDesc;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.custNo = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_NO", "-");
        this.customerName = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_NAME", "-");
        this.employer = ResultSetExtractor.getValueFromResultSet(data, "EMPLOYER", "-");
        this.dateOfBirth = ResultSetExtractor.getValueFromResultSet(data, "DATE_OF_BIRTH", "-");
        this.gender = ResultSetExtractor.getValueFromResultSet(data, "GENDER", "-");
        this.proprietorName = ResultSetExtractor.getValueFromResultSet(data, "PROPRIETOR_NAME", "-");
        this.correspondenceAddress = ResultSetExtractor.getValueFromResultSet(data, "CORRESPONDENCE_ADDRESS", "-");
        this.permanentAddress = ResultSetExtractor.getValueFromResultSet(data, "PERMANENT_ADDRESS", "-");
        this.homePhone = ResultSetExtractor.getValueFromResultSet(data, "HOME_PHONE", "-");
        this.workPhone = ResultSetExtractor.getValueFromResultSet(data, "WORK_PHONE", "-");
        this.mobileNumber = ResultSetExtractor.getValueFromResultSet(data, "MOBILE_NUMBER", "-");
        this.fatherName = ResultSetExtractor.getValueFromResultSet(data, "FATHER_NAME", "-");
        this.motherName = ResultSetExtractor.getValueFromResultSet(data, "MOTHER_NAME", "-");
        this.spouseName = ResultSetExtractor.getValueFromResultSet(data, "SPOUSE_NAME", "-");
        this.passportNumber = ResultSetExtractor.getValueFromResultSet(data, "PASSPORT_NUMBER", "-");
        this.nationalId = ResultSetExtractor.getValueFromResultSet(data, "NATIONAL_ID", "-");
        this.eMail = ResultSetExtractor.getValueFromResultSet(data, "E_MAIL", "-");
//        this.acDesc = ResultSetExtractor.getValueFromResultSet(data, "AC_DESC", "-");
        this.tinNo = ResultSetExtractor.getValueFromResultSet(data, "TIN_NO", "-");
        this.location = ResultSetExtractor.getValueFromResultSet(data, "LOCATION", "-");
//        this.creditRating = ResultSetExtractor.getValueFromResultSet(data, "CREDIT_RATING", "-");
        this.cifStatus = ResultSetExtractor.getValueFromResultSet(data, "CIF_STATUS", "-");
        this.customerType = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_TYPE", "-");
        this.customerCategory = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_CATEGORY", "-");
        this.accountType = ResultSetExtractor.getValueFromResultSet(data, "ACC_TYPE", "-");
        this.secCode = ResultSetExtractor.getValueFromResultSet(data, "SECT_CODE", "-");
        this.secDesc = ResultSetExtractor.getValueFromResultSet(data, "SECT_DESC", "-");
        this.smeCode = ResultSetExtractor.getValueFromResultSet(data, "SME_CODE", "-");
        this.smeDesc = ResultSetExtractor.getValueFromResultSet(data, "SME_DESC", "-");


    }

}