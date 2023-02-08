package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class SmsAndAutoDistributionRulesEntityDto {

    private String type;

    private String noOfDaysBeforeEmiDate;

    private String unpaidInstallmentNumber;

    private String loanType;

    private String loanStatus;

    public SmsAndAutoDistributionRulesEntityDto(Tuple t) {
        this.type = Objects.toString(t.get("RULE_NAME"), "");
        this.noOfDaysBeforeEmiDate = Objects.toString(t.get("NO_OF_INSTALLMENT_DUE"), "");
        this.unpaidInstallmentNumber = Objects.toString(t.get("NO_OF_DAYS_BEFORE_EMI_DATE"), "");
        this.loanType = Objects.toString(t.get("LOAN_STATUS"), "");
        this.loanStatus = Objects.toString(t.get("LOAN_TYPE"), "");
    }
}
