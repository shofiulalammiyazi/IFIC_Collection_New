package com.csinfotechbd.customerloanprofile.letterinformation;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class LetterInformation extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(value = TemporalType.DATE)
    private Date letterSendDate;
    @Temporal(value = TemporalType.DATE)
    private Date letterReturnDate;
    private String refNo;
    private String accountNo;
    private String accountName;
    private String letterType;
    private String letterReason;
    private String reciever;
    private String recieverName;

    public LetterInformation() {
    }

    public LetterInformation(Date letterSendDate, Date letterReturnDate, String refNo, String accountNo, String accountName, String letterType, String letterReason, String reciever, String recieverName) {
        this.letterSendDate = letterSendDate;
        this.letterReturnDate = letterReturnDate;
        this.refNo = refNo;
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.letterType = letterType;
        this.letterReason = letterReason;
        this.reciever = reciever;
        this.recieverName = recieverName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLetterSendDate() {
        return letterSendDate;
    }

    public void setLetterSendDate(Date letterSendDate) {
        this.letterSendDate = letterSendDate;
    }

    public Date getLetterReturnDate() {
        return letterReturnDate;
    }

    public void setLetterReturnDate(Date letterReturnDate) {
        this.letterReturnDate = letterReturnDate;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLetterType() {
        return letterType;
    }

    public void setLetterType(String letterType) {
        this.letterType = letterType;
    }

    public String getLetterReason() {
        return letterReason;
    }

    public void setLetterReason(String letterReason) {
        this.letterReason = letterReason;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }
}
