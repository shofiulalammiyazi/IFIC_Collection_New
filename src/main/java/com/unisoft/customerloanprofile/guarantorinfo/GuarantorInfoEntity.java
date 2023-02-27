package com.unisoft.customerloanprofile.guarantorinfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisoft.base.BaseInfo;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class GuarantorInfoEntity extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountNo;
    private String name;
    private String occupation;
    private String companyName;
    private String designation;
    private String homeAddress;
    private String officeAddress;
    private String permanentAddress;
    private String phoneNo;
    private String relationGuarantor;
//    this Added By Abdullah
    private String loanAccountNo;
    private String facilityWithBank;
    private String dob;
    private String nidOrPassport;
    private String guarantorFatherName;
    private String guarantorMotherName;

    private String optimaId;

    @Enumerated(EnumType.STRING)
    private GuarantorInfoStatus status;
    private String dealerPin;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public  String getGuarantorFatherName (){return guarantorFatherName;}
    public void  setGuarantorFatherName(String guarantorFatherName){this.guarantorFatherName = guarantorFatherName;}

    public  String getGuarantorMotherName (){return guarantorMotherName;}
    public void  setGuarantorMotherName(String guarantorMotherName){this.guarantorMotherName = guarantorMotherName;}

    public String getRelationGuarantor() {
        return relationGuarantor;
    }

    public void setRelationGuarantor(String relationGuarantor) {
        this.relationGuarantor = relationGuarantor;
    }

    public CustomerBasicInfoEntity getCustomerBasicInfo() {
        return customerBasicInfo;
    }

    public void setCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfo) {
        this.customerBasicInfo = customerBasicInfo;
    }

    public GuarantorInfoStatus getStatus() {
        return status;
    }

    public void setStatus(GuarantorInfoStatus status) {
        this.status = status;
    }


    public String getDealerPin() {
        return dealerPin;
    }

    public void setDealerPin(String dealerPin) {
        this.dealerPin = dealerPin;
    }
}

