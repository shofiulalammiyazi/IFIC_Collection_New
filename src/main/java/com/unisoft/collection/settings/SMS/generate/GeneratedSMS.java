package com.unisoft.collection.settings.SMS.generate;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.SMS.smsType.SMSEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "GENERATED_SMS")
public class GeneratedSMS extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private SMSEntity smsType;

    @Lob
    private String massege;

    private String accountNo;

    private String mobileNo;

    @Transient
    private String dealReference;

    public GeneratedSMS(SMSEntity smsType, String massege, String accountNo, String mobileNo) {
        this.smsType = smsType;
        this.massege = massege;
        this.accountNo = accountNo;
        this.mobileNo = mobileNo;
    }

    public GeneratedSMS(Long id, String massege, String accountNo, String mobileNo, String dealReference) {
        this.id = id;
        this.massege = massege;
        this.accountNo = accountNo;
        this.mobileNo = mobileNo;
        this.dealReference = dealReference;
    }

    public GeneratedSMS(Long id, SMSEntity smsType, String massege, String accountNo, String mobileNo) {
        this.id = id;
        this.smsType = smsType;
        this.massege = massege;
        this.accountNo = accountNo;
        this.mobileNo = mobileNo;
    }

    public GeneratedSMS() {
    }
}