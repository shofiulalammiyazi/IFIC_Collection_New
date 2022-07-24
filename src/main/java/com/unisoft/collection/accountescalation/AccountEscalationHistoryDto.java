package com.unisoft.collection.accountescalation;

import lombok.Data;

@Data
public class AccountEscalationHistoryDto {

    private String dealerPin;

    private String superVisorPin;

    private String managerPin;

    private String srManagerPin;

    private String dealerNote;

    private String supervisorNote;

    private String managerNote;

    private String srManagerNote;

    private String dealerName;

    private String supervisorName;

    private String managerName;

    private String srManagerName;

    private String desig;

    private String status;

    private String createdDate;
}
