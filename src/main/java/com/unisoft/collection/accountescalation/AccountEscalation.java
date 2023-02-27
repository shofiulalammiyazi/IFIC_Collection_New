package com.unisoft.collection.accountescalation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class AccountEscalation extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String note;

    private String fromUserName;

    private String fromUserPin;

    private String toUserName;

    private String toUserPin;

    private String status;

    private String accountNumber;

    private String typeCheck;

    private Double bucket;

    private String dealerPin;

    private String teamLeaderPin;

    private String superVisorPin;

    private String managerPin;

    private String approvedBy;

    @OneToMany(mappedBy = "accountEscalation", cascade = CascadeType.REFRESH)
    @JsonManagedReference
    private List<AccountEscalationNote> accountEscalationNotes;

    public AccountEscalation() {
    }

    public AccountEscalation(Long id) {
        this.id = id;
    }

    public AccountEscalation(String note, String fromUserName, String fromUserPin, String toUserName, String toUserPin, String status, String accountNumber, String typeCheck) {
        this.note = note;
        this.fromUserName = fromUserName;
        this.fromUserPin = fromUserPin;
        this.toUserName = toUserName;
        this.toUserPin = toUserPin;
        this.status = status;
        this.accountNumber = accountNumber;
        this.typeCheck = typeCheck;
    }

    public AccountEscalation(Long id, String note, String fromUserName, String fromUserPin, String toUserName, String toUserPin, String status, String accountNumber, String typeCheck, double bucket, String dealerPin, String teamLeaderPin, String superVisorPin, String managerPin, List<AccountEscalationNote> accountEscalationNotes) {
        this.id = id;
        this.note = note;
        this.fromUserName = fromUserName;
        this.fromUserPin = fromUserPin;
        this.toUserName = toUserName;
        this.toUserPin = toUserPin;
        this.status = status;
        this.accountNumber = accountNumber;
        this.typeCheck = typeCheck;
        this.bucket = bucket;
        this.dealerPin = dealerPin;
        this.teamLeaderPin = teamLeaderPin;
        this.superVisorPin = superVisorPin;
        this.managerPin = managerPin;
        this.accountEscalationNotes = accountEscalationNotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserPin() {
        return fromUserPin;
    }

    public void setFromUserPin(String fromUserPin) {
        this.fromUserPin = fromUserPin;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserPin() {
        return toUserPin;
    }

    public void setToUserPin(String toUserPin) {
        this.toUserPin = toUserPin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTypeCheck() {
        return typeCheck;
    }

    public void setTypeCheck(String typeCheck) {
        this.typeCheck = typeCheck;
    }

    public String getDealerPin() {
        return dealerPin;
    }

    public void setDealerPin(String dealerPin) {
        this.dealerPin = dealerPin;
    }

    public String getTeamLeaderPin() {
        return teamLeaderPin;
    }

    public void setTeamLeaderPin(String teamLeaderPin) {
        this.teamLeaderPin = teamLeaderPin;
    }

    public String getSuperVisorPin() {
        return superVisorPin;
    }

    public void setSuperVisorPin(String superVisorPin) {
        this.superVisorPin = superVisorPin;
    }

    public String getManagerPin() {
        return managerPin;
    }

    public void setManagerPin(String managerPin) {
        this.managerPin = managerPin;
    }
}
