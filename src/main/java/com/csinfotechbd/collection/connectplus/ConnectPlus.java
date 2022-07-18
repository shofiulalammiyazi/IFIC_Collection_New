package com.csinfotechbd.collection.connectplus;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "RDMS_DATADUMPSTORE")
public class ConnectPlus {

    @Id
    @Column(name = "LOANID")
    private String loanId;

    @Column(name = "APPLIEDLOANTYPE")
    private String appliedLoanType;

    @Column(name = "CUSTOMERSEGMENT")
    private String customerSegment;

    @Column(name = "OCCUPATION")
    private String applicationName;

    @Column(name = "APPLICANTNAME")
    private String applicantName;

    @Column(name = "APPLICANTFATHERNAME")
    private String applicationFatherName;

    @Column(name = "APPLICANTMOTHERNAME")
    private String applicationMotherName;

    @Column(name = "SPOUSENAME")
    private String spouseName;

    @Column(name = "COAPPNAME1")
    private String coAppName1;

    @Column(name = "COAPPNAME2")
    private String coAppName2;

    @Column(name = "RELATIONWITHGUARANTOR1")
    private String elationWithGurantor1;

    @Column(name = "RELATIONWITHGUARANTOR2")
    private String elationWithGurantor2;

    @Column(name = "REFERENCE1")
    private String reference1;

    @Column(name = "REFERENCE2")
    private String reference2;

    @Column(name = "RELATIONWITHREFERENCE1")
    private String relationWithReference1;

    @Column(name = "RELATIONWITHREFERENCE2")
    private String relationWithReference2;

    @Column(name = "ORGANIZATIONNAME1")
    private String organizationName1;

    @Column(name = "ORGANIZATIONNAME2")
    private String organizationName2;

    @Column(name = "ORGANIZATIONNAME3")
    private String organizationName3;

    @Column(name = "APPLICANTINCOMEDEB")
    private String applicatIncodeDeb;

    @Column(name = "APPLICANTINCOMECTO")
    private String applicantIncomeCto;

    @Column(name = "COAPPRENTALINCOME")
    private String coAppRentalIncome;

    @Column(name = "BBLACCOUNTNO")
    private String bblAccountNo;

    @Column(name = "APPLICANTGROSSSALARY")
    private String applicantGrossSalary;

    @Column(name = "APPLICANTNETSALARY")
    private String applicantNetSalary;

    @Column(name = "APPLICANTRENTALINCOME")
    private String applicantRentalIncome;

    @Column(name = "APPLICANTRENTINCAFTERCPV")
    private String applicantRentCaftercpv;

    @Column(name = "COAPPRENTALINCAFTERCPV")
    private String coAppRentalIncafTerCpv;

    @Column(name = "TOTALINCOMEAAPCOAPP")
    private String totalIncomeAppCoApp;

    @Column(name = "SECTOR")
    private String sector;

    @Column(name = "SUBSECTOR")
    private String subSector;

    @Column(name = "SECTORCODE")
    private String sectorCode;

    @Column(name = "BANKNAME1")
    private String bankName1;

    @Column(name = "BANKNAME2")
    private String bankName2;

    @Column(name = "BANKNAME3")
    private String bankName3;

    @Column(name = "BANKNAME4")
    private String bankName4;

    @Column(name = "BANKNAME5")
    private String bankName5;

    @Column(name = "BANKNAME6")
    private String bankName6;

    @Column(name = "BANKNAME7")
    private String bankName7;

    @Column(name = "BANKNAME8")
    private String bankName8;

    @Column(name = "BANKNAME9")
    private String bankName9;

    @Column(name = "BANKNAME10")
    private String bankName10;

    @Column(name = "BANKNAME11")
    private String bankName11;

    @Column(name = "BANKNAME12")
    private String bankName12;

    @Column(name = "ACCOUNTNOORCREDITCARDNO1")
    private String accountNoorCreditCardNo1;

    @Column(name = "ACCOUNTNOORCREDITCARDNO2")
    private String accountNoorCreditCardNo2;

    @Column(name = "ACCOUNTNOORCREDITCARDNO3")
    private String accountNoorCreditCardNo3;

    @Column(name = "ACCOUNTNOORCREDITCARDNO4")
    private String accountNoorCreditCardNo4;

    @Column(name = "ACCOUNTNOORCREDITCARDNO5")
    private String accountNoorCreditCardNo5;

    @Column(name = "ACCOUNTNOORCREDITCARDNO6")
    private String accountNoorCreditCardNo6;

    @Column(name = "ACCOUNTNOORCREDITCARDNO7")
    private String accountNoorCreditCardNo7;

    @Column(name = "ACCOUNTNOORCREDITCARDNO8")
    private String accountNoorCreditCardNo8;

    @Column(name = "ACCOUNTNOORCREDITCARDNO9")
    private String accountNoorCreditCardNo9;

    @Column(name = "ACCOUNTNOORCREDITCARDNO10")
    private String accountNoorCreditCardNo10;

    @Column(name = "ACCOUNTNOORCREDITCARDNO11")
    private String accountNoorCreditCardNo11;

    @Column(name = "ACCOUNTNOORCREDITCARDNO12")
    private String accountNoorCreditCardNo12;

    @Column(name = "EMI1")
    private String emi1;

    @Column(name = "EMI2")
    private String emi2;

    @Column(name = "EMI3")
    private String emi3;

    @Column(name = "EMI4")
    private String emi4;

    @Column(name = "EMI5")
    private String emi5;

    @Column(name = "EMI6")
    private String emi6;

    @Column(name = "EMI7")
    private String emi7;

    @Column(name = "EMI8")
    private String emi8;

    @Column(name = "EMI9")
    private String emi9;

    @Column(name = "EMI10")
    private String emi10;

    @Column(name = "EMI11")
    private String emi11;

    @Column(name = "EMI12")
    private String emi12;

    @Column(name = "SANCTIONAMOUNT1")
    private String sanctionAmount1;

    @Column(name = "SANCTIONAMOUNT2")
    private String sanctionAmount2;

    @Column(name = "SANCTIONAMOUNT3")
    private String sanctionAmount3;

    @Column(name = "SANCTIONAMOUNT4")
    private String sanctionAmount4;

    @Column(name = "SANCTIONAMOUNT5")
    private String sanctionAmount5;

    @Column(name = "SANCTIONAMOUNT6")
    private String sanctionAmount6;

    @Column(name = "SANCTIONAMOUNT7")
    private String sanctionAmount7;

    @Column(name = "SANCTIONAMOUNT8")
    private String sanctionAmount8;

    @Column(name = "SANCTIONAMOUNT9")
    private String sanctionAmount9;

    @Column(name = "SANCTIONAMOUNT10")
    private String sanctionAmount10;

    @Column(name = "SANCTIONAMOUNT11")
    private String sanctionAmount11;

    @Column(name = "SANCTIONAMOUNT12")
    private String sanctionAmount12;

    @Column(name = "SANCTIONLIMIT1")
    private String sanctionLimit1;

    @Column(name = "SANCTIONLIMIT2")
    private String sanctionLimit2;

    @Column(name = "SANCTIONLIMIT3")
    private String sanctionLimit3;

    @Column(name = "SANCTIONLIMIT4")
    private String sanctionLimit4;

    @Column(name = "SANCTIONLIMIT5")
    private String sanctionLimit5;

    @Column(name = "SANCTIONLIMIT6")
    private String sanctionLimit6;

    @Column(name = "SANCTIONLIMIT7")
    private String sanctionLimit7;

    @Column(name = "SANCTIONLIMIT8")
    private String sanctionLimit8;

    @Column(name = "SANCTIONLIMIT9")
    private String sanctionLimit9;

    @Column(name = "SANCTIONLIMIT10")
    private String sanctionLimit10;

    @Column(name = "SANCTIONLIMIT11")
    private String sanctionLimit11;

    @Column(name = "SANCTIONLIMIT12")
    private String sanctionLimit12;

    @Column(name = "SECURITYTYPE1")
    private String securityType1;

    @Column(name = "SECURITYTYPE2")
    private String securityType2;

    @Column(name = "SECURITYTYPE3")
    private String securityType3;

    @Column(name = "SECURITYTYPE4")
    private String securityType4;

    @Column(name = "SECURITYTYPE5")
    private String securityType5;

    @Column(name = "FACEVALUESECURITY1")
    private String faceValueSecurity1;

    @Column(name = "FACEVALUESECURITY2")
    private String faceValueSecurity2;

    @Column(name = "FACEVALUESECURITY3")
    private String faceValueSecurity3;

    @Column(name = "FACEVALUESECURITY4")
    private String faceValueSecurity4;

    @Column(name = "FACEVALUESECURITY5")
    private String faceValueSecurity5;

    @Column(name = "PROPERTYADDRESS")
    private String propertyAddress;

    @Column(name = "DEVELOPERNAME")
    private String developerName;

    @Column(name = "LANDVALUE")
    private String landValue;

    @Column(name = "APARTMENTSIZE")
    private String apartmentSize;

    @Column(name = "CARPRICE")
    private String carPrice;

    @Column(name = "CARBRANDDETAILS")
    private String cardBrandDetails;

    @Column(name = "CARMODEL")
    private String carModel;

    @Column(name = "CARCATEGORY")
    private String carCategory;

    @Column(name = "NAMEOFBRANCH")
    private String nameOfBranch;

    @Column(name = "MEDIATOR")
    private String mediator;

    @Column(name = "TOPUP")
    private String topUp;

    @Column(name = "AGEOFTOPUP")
    private String ageOfTopUp;

    @Column(name = "LOANACCOUNTNUMBER")
    private String loanAccountNumber;

    @Column(name = "TAKEOVER")
    private String takeOver;

    @Column(name = "FILERECEIVEDAT")
    private String fileReceiveDat;

    @Column(name = "APPLICANTTYPE")
    private String applicantType;

    @Column(name = "ORGANIZATIONTYPE1")
    private String organizationType1;

    @Column(name = "ORGANIZATIONTYPE2")
    private String organizationType2;

    @Column(name = "ORGANIZATIONTYPE3")
    private String organizationType3;

    @Column(name = "COSTCENTER")
    private String costCenter;

    @Column(name = "LOANACCOUNTORCARDNUMBER")
    private String loanAccountCardNumber;

    @Column(name = "APPROVALLEVEL")
    private String approvalLevel;

    @Temporal(TemporalType.DATE)
    @Column(name = "RECEIVEDATE")
    private Date receivedDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "CURRENTSTATUSDATE")
    private Date currentStatusDate;

    public String getApprovalLevel() {
        return approvalLevel;
    }

    public void setApprovalLevel(String approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getAppliedLoanType() {
        return appliedLoanType;
    }

    public void setAppliedLoanType(String appliedLoanType) {
        this.appliedLoanType = appliedLoanType;
    }

    public String getCustomerSegment() {
        return customerSegment;
    }

    public void setCustomerSegment(String customerSegment) {
        this.customerSegment = customerSegment;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicationFatherName() {
        return applicationFatherName;
    }

    public void setApplicationFatherName(String applicationFatherName) {
        this.applicationFatherName = applicationFatherName;
    }

    public String getApplicationMotherName() {
        return applicationMotherName;
    }

    public void setApplicationMotherName(String applicationMotherName) {
        this.applicationMotherName = applicationMotherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getCoAppName1() {
        return coAppName1;
    }

    public void setCoAppName1(String coAppName1) {
        this.coAppName1 = coAppName1;
    }

    public String getCoAppName2() {
        return coAppName2;
    }

    public void setCoAppName2(String coAppName2) {
        this.coAppName2 = coAppName2;
    }

    public String getElationWithGurantor1() {
        return elationWithGurantor1;
    }

    public void setElationWithGurantor1(String elationWithGurantor1) {
        this.elationWithGurantor1 = elationWithGurantor1;
    }

    public String getElationWithGurantor2() {
        return elationWithGurantor2;
    }

    public void setElationWithGurantor2(String elationWithGurantor2) {
        this.elationWithGurantor2 = elationWithGurantor2;
    }

    public String getReference1() {
        return reference1;
    }

    public void setReference1(String reference1) {
        this.reference1 = reference1;
    }

    public String getReference2() {
        return reference2;
    }

    public void setReference2(String reference2) {
        this.reference2 = reference2;
    }

    public String getRelationWithReference1() {
        return relationWithReference1;
    }

    public void setRelationWithReference1(String relationWithReference1) {
        this.relationWithReference1 = relationWithReference1;
    }

    public String getRelationWithReference2() {
        return relationWithReference2;
    }

    public void setRelationWithReference2(String relationWithReference2) {
        this.relationWithReference2 = relationWithReference2;
    }

    public String getOrganizationName1() {
        return organizationName1;
    }

    public void setOrganizationName1(String organizationName1) {
        this.organizationName1 = organizationName1;
    }

    public String getOrganizationName2() {
        return organizationName2;
    }

    public void setOrganizationName2(String organizationName2) {
        this.organizationName2 = organizationName2;
    }

    public String getOrganizationName3() {
        return organizationName3;
    }

    public void setOrganizationName3(String organizationName3) {
        this.organizationName3 = organizationName3;
    }

    public String getApplicatIncodeDeb() {
        return applicatIncodeDeb;
    }

    public void setApplicatIncodeDeb(String applicatIncodeDeb) {
        this.applicatIncodeDeb = applicatIncodeDeb;
    }

    public String getApplicantIncomeCto() {
        return applicantIncomeCto;
    }

    public void setApplicantIncomeCto(String applicantIncomeCto) {
        this.applicantIncomeCto = applicantIncomeCto;
    }

    public String getCoAppRentalIncome() {
        return coAppRentalIncome;
    }

    public void setCoAppRentalIncome(String coAppRentalIncome) {
        this.coAppRentalIncome = coAppRentalIncome;
    }

    public String getBblAccountNo() {
        return bblAccountNo;
    }

    public void setBblAccountNo(String bblAccountNo) {
        this.bblAccountNo = bblAccountNo;
    }

    public String getApplicantGrossSalary() {
        return applicantGrossSalary;
    }

    public void setApplicantGrossSalary(String applicantGrossSalary) {
        this.applicantGrossSalary = applicantGrossSalary;
    }

    public String getApplicantNetSalary() {
        return applicantNetSalary;
    }

    public void setApplicantNetSalary(String applicantNetSalary) {
        this.applicantNetSalary = applicantNetSalary;
    }

    public String getApplicantRentalIncome() {
        return applicantRentalIncome;
    }

    public void setApplicantRentalIncome(String applicantRentalIncome) {
        this.applicantRentalIncome = applicantRentalIncome;
    }

    public String getApplicantRentCaftercpv() {
        return applicantRentCaftercpv;
    }

    public void setApplicantRentCaftercpv(String applicantRentCaftercpv) {
        this.applicantRentCaftercpv = applicantRentCaftercpv;
    }

    public String getCoAppRentalIncafTerCpv() {
        return coAppRentalIncafTerCpv;
    }

    public void setCoAppRentalIncafTerCpv(String coAppRentalIncafTerCpv) {
        this.coAppRentalIncafTerCpv = coAppRentalIncafTerCpv;
    }

    public String getTotalIncomeAppCoApp() {
        return totalIncomeAppCoApp;
    }

    public void setTotalIncomeAppCoApp(String totalIncomeAppCoApp) {
        this.totalIncomeAppCoApp = totalIncomeAppCoApp;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSubSector() {
        return subSector;
    }

    public void setSubSector(String subSector) {
        this.subSector = subSector;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getBankName1() {
        return bankName1;
    }

    public void setBankName1(String bankName1) {
        this.bankName1 = bankName1;
    }

    public String getBankName2() {
        return bankName2;
    }

    public void setBankName2(String bankName2) {
        this.bankName2 = bankName2;
    }

    public String getBankName3() {
        return bankName3;
    }

    public void setBankName3(String bankName3) {
        this.bankName3 = bankName3;
    }

    public String getBankName4() {
        return bankName4;
    }

    public void setBankName4(String bankName4) {
        this.bankName4 = bankName4;
    }

    public String getBankName5() {
        return bankName5;
    }

    public void setBankName5(String bankName5) {
        this.bankName5 = bankName5;
    }

    public String getBankName6() {
        return bankName6;
    }

    public void setBankName6(String bankName6) {
        this.bankName6 = bankName6;
    }

    public String getBankName7() {
        return bankName7;
    }

    public void setBankName7(String bankName7) {
        this.bankName7 = bankName7;
    }

    public String getBankName8() {
        return bankName8;
    }

    public void setBankName8(String bankName8) {
        this.bankName8 = bankName8;
    }

    public String getBankName9() {
        return bankName9;
    }

    public void setBankName9(String bankName9) {
        this.bankName9 = bankName9;
    }

    public String getBankName10() {
        return bankName10;
    }

    public void setBankName10(String bankName10) {
        this.bankName10 = bankName10;
    }

    public String getBankName11() {
        return bankName11;
    }

    public void setBankName11(String bankName11) {
        this.bankName11 = bankName11;
    }

    public String getBankName12() {
        return bankName12;
    }

    public void setBankName12(String bankName12) {
        this.bankName12 = bankName12;
    }

    public String getAccountNoorCreditCardNo1() {
        return accountNoorCreditCardNo1;
    }

    public void setAccountNoorCreditCardNo1(String accountNoorCreditCardNo1) {
        this.accountNoorCreditCardNo1 = accountNoorCreditCardNo1;
    }

    public String getAccountNoorCreditCardNo2() {
        return accountNoorCreditCardNo2;
    }

    public void setAccountNoorCreditCardNo2(String accountNoorCreditCardNo2) {
        this.accountNoorCreditCardNo2 = accountNoorCreditCardNo2;
    }

    public String getAccountNoorCreditCardNo3() {
        return accountNoorCreditCardNo3;
    }

    public void setAccountNoorCreditCardNo3(String accountNoorCreditCardNo3) {
        this.accountNoorCreditCardNo3 = accountNoorCreditCardNo3;
    }

    public String getAccountNoorCreditCardNo4() {
        return accountNoorCreditCardNo4;
    }

    public void setAccountNoorCreditCardNo4(String accountNoorCreditCardNo4) {
        this.accountNoorCreditCardNo4 = accountNoorCreditCardNo4;
    }

    public String getAccountNoorCreditCardNo5() {
        return accountNoorCreditCardNo5;
    }

    public void setAccountNoorCreditCardNo5(String accountNoorCreditCardNo5) {
        this.accountNoorCreditCardNo5 = accountNoorCreditCardNo5;
    }

    public String getAccountNoorCreditCardNo6() {
        return accountNoorCreditCardNo6;
    }

    public void setAccountNoorCreditCardNo6(String accountNoorCreditCardNo6) {
        this.accountNoorCreditCardNo6 = accountNoorCreditCardNo6;
    }

    public String getAccountNoorCreditCardNo7() {
        return accountNoorCreditCardNo7;
    }

    public void setAccountNoorCreditCardNo7(String accountNoorCreditCardNo7) {
        this.accountNoorCreditCardNo7 = accountNoorCreditCardNo7;
    }

    public String getAccountNoorCreditCardNo8() {
        return accountNoorCreditCardNo8;
    }

    public void setAccountNoorCreditCardNo8(String accountNoorCreditCardNo8) {
        this.accountNoorCreditCardNo8 = accountNoorCreditCardNo8;
    }

    public String getAccountNoorCreditCardNo9() {
        return accountNoorCreditCardNo9;
    }

    public void setAccountNoorCreditCardNo9(String accountNoorCreditCardNo9) {
        this.accountNoorCreditCardNo9 = accountNoorCreditCardNo9;
    }

    public String getAccountNoorCreditCardNo10() {
        return accountNoorCreditCardNo10;
    }

    public void setAccountNoorCreditCardNo10(String accountNoorCreditCardNo10) {
        this.accountNoorCreditCardNo10 = accountNoorCreditCardNo10;
    }

    public String getAccountNoorCreditCardNo11() {
        return accountNoorCreditCardNo11;
    }

    public void setAccountNoorCreditCardNo11(String accountNoorCreditCardNo11) {
        this.accountNoorCreditCardNo11 = accountNoorCreditCardNo11;
    }

    public String getAccountNoorCreditCardNo12() {
        return accountNoorCreditCardNo12;
    }

    public void setAccountNoorCreditCardNo12(String accountNoorCreditCardNo12) {
        this.accountNoorCreditCardNo12 = accountNoorCreditCardNo12;
    }

    public String getEmi1() {
        return emi1;
    }

    public void setEmi1(String emi1) {
        this.emi1 = emi1;
    }

    public String getEmi2() {
        return emi2;
    }

    public void setEmi2(String emi2) {
        this.emi2 = emi2;
    }

    public String getEmi3() {
        return emi3;
    }

    public void setEmi3(String emi3) {
        this.emi3 = emi3;
    }

    public String getEmi4() {
        return emi4;
    }

    public void setEmi4(String emi4) {
        this.emi4 = emi4;
    }

    public String getEmi5() {
        return emi5;
    }

    public void setEmi5(String emi5) {
        this.emi5 = emi5;
    }

    public String getEmi6() {
        return emi6;
    }

    public void setEmi6(String emi6) {
        this.emi6 = emi6;
    }

    public String getEmi7() {
        return emi7;
    }

    public void setEmi7(String emi7) {
        this.emi7 = emi7;
    }

    public String getEmi8() {
        return emi8;
    }

    public void setEmi8(String emi8) {
        this.emi8 = emi8;
    }

    public String getEmi9() {
        return emi9;
    }

    public void setEmi9(String emi9) {
        this.emi9 = emi9;
    }

    public String getEmi10() {
        return emi10;
    }

    public void setEmi10(String emi10) {
        this.emi10 = emi10;
    }

    public String getEmi11() {
        return emi11;
    }

    public void setEmi11(String emi11) {
        this.emi11 = emi11;
    }

    public String getEmi12() {
        return emi12;
    }

    public void setEmi12(String emi12) {
        this.emi12 = emi12;
    }

    public String getSanctionAmount1() {
        return sanctionAmount1;
    }

    public void setSanctionAmount1(String sanctionAmount1) {
        this.sanctionAmount1 = sanctionAmount1;
    }

    public String getSanctionAmount2() {
        return sanctionAmount2;
    }

    public void setSanctionAmount2(String sanctionAmount2) {
        this.sanctionAmount2 = sanctionAmount2;
    }

    public String getSanctionAmount3() {
        return sanctionAmount3;
    }

    public void setSanctionAmount3(String sanctionAmount3) {
        this.sanctionAmount3 = sanctionAmount3;
    }

    public String getSanctionAmount4() {
        return sanctionAmount4;
    }

    public void setSanctionAmount4(String sanctionAmount4) {
        this.sanctionAmount4 = sanctionAmount4;
    }

    public String getSanctionAmount5() {
        return sanctionAmount5;
    }

    public void setSanctionAmount5(String sanctionAmount5) {
        this.sanctionAmount5 = sanctionAmount5;
    }

    public String getSanctionAmount6() {
        return sanctionAmount6;
    }

    public void setSanctionAmount6(String sanctionAmount6) {
        this.sanctionAmount6 = sanctionAmount6;
    }

    public String getSanctionAmount7() {
        return sanctionAmount7;
    }

    public void setSanctionAmount7(String sanctionAmount7) {
        this.sanctionAmount7 = sanctionAmount7;
    }

    public String getSanctionAmount8() {
        return sanctionAmount8;
    }

    public void setSanctionAmount8(String sanctionAmount8) {
        this.sanctionAmount8 = sanctionAmount8;
    }

    public String getSanctionAmount9() {
        return sanctionAmount9;
    }

    public void setSanctionAmount9(String sanctionAmount9) {
        this.sanctionAmount9 = sanctionAmount9;
    }

    public String getSanctionAmount10() {
        return sanctionAmount10;
    }

    public void setSanctionAmount10(String sanctionAmount10) {
        this.sanctionAmount10 = sanctionAmount10;
    }

    public String getSanctionAmount11() {
        return sanctionAmount11;
    }

    public void setSanctionAmount11(String sanctionAmount11) {
        this.sanctionAmount11 = sanctionAmount11;
    }

    public String getSanctionAmount12() {
        return sanctionAmount12;
    }

    public void setSanctionAmount12(String sanctionAmount12) {
        this.sanctionAmount12 = sanctionAmount12;
    }

    public String getSanctionLimit1() {
        return sanctionLimit1;
    }

    public void setSanctionLimit1(String sanctionLimit1) {
        this.sanctionLimit1 = sanctionLimit1;
    }

    public String getSanctionLimit2() {
        return sanctionLimit2;
    }

    public void setSanctionLimit2(String sanctionLimit2) {
        this.sanctionLimit2 = sanctionLimit2;
    }

    public String getSanctionLimit3() {
        return sanctionLimit3;
    }

    public void setSanctionLimit3(String sanctionLimit3) {
        this.sanctionLimit3 = sanctionLimit3;
    }

    public String getSanctionLimit4() {
        return sanctionLimit4;
    }

    public void setSanctionLimit4(String sanctionLimit4) {
        this.sanctionLimit4 = sanctionLimit4;
    }

    public String getSanctionLimit5() {
        return sanctionLimit5;
    }

    public void setSanctionLimit5(String sanctionLimit5) {
        this.sanctionLimit5 = sanctionLimit5;
    }

    public String getSanctionLimit6() {
        return sanctionLimit6;
    }

    public void setSanctionLimit6(String sanctionLimit6) {
        this.sanctionLimit6 = sanctionLimit6;
    }

    public String getSanctionLimit7() {
        return sanctionLimit7;
    }

    public void setSanctionLimit7(String sanctionLimit7) {
        this.sanctionLimit7 = sanctionLimit7;
    }

    public String getSanctionLimit8() {
        return sanctionLimit8;
    }

    public void setSanctionLimit8(String sanctionLimit8) {
        this.sanctionLimit8 = sanctionLimit8;
    }

    public String getSanctionLimit9() {
        return sanctionLimit9;
    }

    public void setSanctionLimit9(String sanctionLimit9) {
        this.sanctionLimit9 = sanctionLimit9;
    }

    public String getSanctionLimit10() {
        return sanctionLimit10;
    }

    public void setSanctionLimit10(String sanctionLimit10) {
        this.sanctionLimit10 = sanctionLimit10;
    }

    public String getSanctionLimit11() {
        return sanctionLimit11;
    }

    public void setSanctionLimit11(String sanctionLimit11) {
        this.sanctionLimit11 = sanctionLimit11;
    }

    public String getSanctionLimit12() {
        return sanctionLimit12;
    }

    public void setSanctionLimit12(String sanctionLimit12) {
        this.sanctionLimit12 = sanctionLimit12;
    }

    public String getSecurityType1() {
        return securityType1;
    }

    public void setSecurityType1(String securityType1) {
        this.securityType1 = securityType1;
    }

    public String getSecurityType2() {
        return securityType2;
    }

    public void setSecurityType2(String securityType2) {
        this.securityType2 = securityType2;
    }

    public String getSecurityType3() {
        return securityType3;
    }

    public void setSecurityType3(String securityType3) {
        this.securityType3 = securityType3;
    }

    public String getSecurityType4() {
        return securityType4;
    }

    public void setSecurityType4(String securityType4) {
        this.securityType4 = securityType4;
    }

    public String getSecurityType5() {
        return securityType5;
    }

    public void setSecurityType5(String securityType5) {
        this.securityType5 = securityType5;
    }

    public String getFaceValueSecurity1() {
        return faceValueSecurity1;
    }

    public void setFaceValueSecurity1(String faceValueSecurity1) {
        this.faceValueSecurity1 = faceValueSecurity1;
    }

    public String getFaceValueSecurity2() {
        return faceValueSecurity2;
    }

    public void setFaceValueSecurity2(String faceValueSecurity2) {
        this.faceValueSecurity2 = faceValueSecurity2;
    }

    public String getFaceValueSecurity3() {
        return faceValueSecurity3;
    }

    public void setFaceValueSecurity3(String faceValueSecurity3) {
        this.faceValueSecurity3 = faceValueSecurity3;
    }

    public String getFaceValueSecurity4() {
        return faceValueSecurity4;
    }

    public void setFaceValueSecurity4(String faceValueSecurity4) {
        this.faceValueSecurity4 = faceValueSecurity4;
    }

    public String getFaceValueSecurity5() {
        return faceValueSecurity5;
    }

    public void setFaceValueSecurity5(String faceValueSecurity5) {
        this.faceValueSecurity5 = faceValueSecurity5;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getLandValue() {
        return landValue;
    }

    public void setLandValue(String landValue) {
        this.landValue = landValue;
    }

    public String getApartmentSize() {
        return apartmentSize;
    }

    public void setApartmentSize(String apartmentSize) {
        this.apartmentSize = apartmentSize;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public String getCardBrandDetails() {
        return cardBrandDetails;
    }

    public void setCardBrandDetails(String cardBrandDetails) {
        this.cardBrandDetails = cardBrandDetails;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public String getNameOfBranch() {
        return nameOfBranch;
    }

    public void setNameOfBranch(String nameOfBranch) {
        this.nameOfBranch = nameOfBranch;
    }

    public String getMediator() {
        return mediator;
    }

    public void setMediator(String mediator) {
        this.mediator = mediator;
    }

    public String getTopUp() {
        return topUp;
    }

    public void setTopUp(String topUp) {
        this.topUp = topUp;
    }

    public String getAgeOfTopUp() {
        return ageOfTopUp;
    }

    public void setAgeOfTopUp(String ageOfTopUp) {
        this.ageOfTopUp = ageOfTopUp;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public String getTakeOver() {
        return takeOver;
    }

    public void setTakeOver(String takeOver) {
        this.takeOver = takeOver;
    }

    public String getFileReceiveDat() {
        return fileReceiveDat;
    }

    public void setFileReceiveDat(String fileReceiveDat) {
        this.fileReceiveDat = fileReceiveDat;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String getOrganizationType1() {
        return organizationType1;
    }

    public void setOrganizationType1(String organizationType1) {
        this.organizationType1 = organizationType1;
    }

    public String getOrganizationType2() {
        return organizationType2;
    }

    public void setOrganizationType2(String organizationType2) {
        this.organizationType2 = organizationType2;
    }

    public String getOrganizationType3() {
        return organizationType3;
    }

    public void setOrganizationType3(String organizationType3) {
        this.organizationType3 = organizationType3;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getLoanAccountCardNumber() {
        return loanAccountCardNumber;
    }

    public void setLoanAccountCardNumber(String loanAccountCardNumber) {
        this.loanAccountCardNumber = loanAccountCardNumber;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getCurrentStatusDate() {
        return currentStatusDate;
    }

    public void setCurrentStatusDate(Date currentStatusDate) {
        this.currentStatusDate = currentStatusDate;
    }
}
