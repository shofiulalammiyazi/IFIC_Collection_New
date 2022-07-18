package com.csinfotechbd.collection.accountescalation;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class AccountEscalationHistory extends BaseInfo {

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
}
