package com.unisoft.customerbasicinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unisoft.base.BaseInfo;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class CustomerBasicInfoEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String customerId;

    @NonNull
    @Column(unique = true)
    private String accountNo;

    private String customerName;

    private String customerFatherName;

    private String customerMotherName;

    private String mobileNumber;

    private String typeAccount;

    private String companyName;

    private String email;

    private Boolean fid = false; // First installment due(not paid)

    // Report Related Fields
    private String clStatus; // Asset Classification

    private String occupation; // Profession

    private String branchCode;

    private String branchName;

    private String sourcingChannel;

    private String region;


    //new field added by shanto for card file upload
    private String clientId;
    private String nameOnCard;
    private String spouseName;
    private String motherName;
    private String fatherName;
    private String designation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date dob;

    private String sex;
    private String passportNo;
    private String nid;
    private String cAddress;
    private String cCity;
    private String cRegion;
    private String contractId;
    private String fileNo;
    private String cardNo;
    private String cardType;
    private String cardProfile;
    private String title;
    private String cardState;
    private String cardStatus;
    private String cardProductName;
    private String marketBy;
    private String deliveryInstr;
    private String resAddress;
    private String resCity;
    private String resRegion;
    private String regAddress;
    private String regCity;
    private String regRegion;
    //private String companyName;
    private String companyAddress;
    private String billAddress;
    private String homeAddress;
    private String telephone;
    private String fax;
    private String mobileNo;
    //private String email;
    private String tin;
    private String pppass;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date givenDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date closeDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date anniveDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date expDate;


    public CustomerBasicInfoEntity() {
    }

    public CustomerBasicInfoEntity(String accountNo) {
        this.accountNo = accountNo;
    }

    public CustomerBasicInfoEntity(String customerId, String accountNo, String customerName, String customerFatherName, String customerMotherName, String mobileNumber, String typeAccount) {
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.customerName = customerName;
        this.customerFatherName = customerFatherName;
        this.customerMotherName = customerMotherName;
        this.mobileNumber = mobileNumber;
        this.typeAccount = typeAccount;
    }

    public CustomerBasicInfoEntity(String card, String contractId, String customerName, String clientId, Date date) {
        this.setTypeAccount(card);
        this.accountNo = contractId;
        this.customerName = customerName;
        this.customerId = clientId;
        this.setCreatedDate(date);
    }

    @Override
    public String toString() {
        return "{"
                + " 'id':'" + id + "'"
                + ",  'customerId':'" + customerId + "'"
                + ",  'accountNo':'" + accountNo + "'"
                + ",  'customerName':'" + customerName + "'"
                + ",  'customerFatherName':'" + customerFatherName + "'"
                + ",  'customerMotherName':'" + customerMotherName + "'"
                + ",  'mobileNumber':'" + mobileNumber + "'"
                + ",  'typeAccount':'" + typeAccount + "'"
                + ",  'client id':'" + clientId + "'"
                + "}";
    }
}
