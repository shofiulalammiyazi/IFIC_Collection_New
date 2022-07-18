package com.csinfotechbd.customerloanprofile.followup;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

/**
 * Created by Yasir Araphat
 * at 19 April, 2021
 */

@Data
public class DealerWiseFollowUpSummary {
    private String dealerPin;
    private String dealerName;
    private String callCount;
    private String callSum;
    private String emailCount;
    private String emailSum;
    private String smsCount;
    private String smsSum;
    private String visitCount;
    private String visitSum;
    private String contactedCount;
    private String contactedSum;
    private String notContactedCount;
    private String notContactedSum;

    public DealerWiseFollowUpSummary() {
    }

    public DealerWiseFollowUpSummary(Tuple data) {
        dealerPin = Objects.toString(data.get("DEALER_PIN"), "-");
        dealerName = Objects.toString(data.get("DEALER_NAME"), "-");
        callCount = Objects.toString(data.get("CALL_COUNT"), "0");
        callSum = Objects.toString(data.get("CALL_SUM"), "0");
        emailCount = Objects.toString(data.get("EMAIL_COUNT"), "0");
        emailSum = Objects.toString(data.get("EMAIL_SUM"), "0");
        smsCount = Objects.toString(data.get("SMS_COUNT"), "0");
        smsSum = Objects.toString(data.get("SMS_SUM"), "0");
        visitCount = Objects.toString(data.get("VISIT_COUNT"), "0");
        visitSum = Objects.toString(data.get("VISIT_SUM"), "0");
        contactedCount = Objects.toString(data.get("CONTACTED_COUNT"), "0");
        contactedSum = Objects.toString(data.get("CONTACTED_SUM"), "0");
        notContactedCount = Objects.toString(data.get("NOT_CONTACTED_COUNT"), "0");
        notContactedSum = Objects.toString(data.get("NOT_CONTACTED_SUM"), "0");
    }
}
