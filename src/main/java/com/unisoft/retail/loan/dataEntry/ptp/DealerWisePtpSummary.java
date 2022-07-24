package com.unisoft.retail.loan.dataEntry.ptp;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

/**
 * Created by
 * at 19 April, 2021
 */

@Data
public class DealerWisePtpSummary {
    private String dealerPin;
    private String dealerName;
    private String keptCount;
    private String keptSum;
    private String brokenCount;
    private String brokenSum;
    private String curedCount;
    private String curedSum;

    public DealerWisePtpSummary() {
    }

    public DealerWisePtpSummary(Tuple data) {
        dealerPin = Objects.toString(data.get("DEALER_PIN"), "-");
        dealerName = Objects.toString(data.get("DEALER_NAME"), "-");
        keptCount = Objects.toString(data.get("KEPT_COUNT"), "-");
        keptSum = Objects.toString(data.get("KEPT_SUM"), "-");
        brokenCount = Objects.toString(data.get("BROKEN_COUNT"), "-");
        brokenSum = Objects.toString(data.get("BROKEN_SUM"), "-");
        curedCount = Objects.toString(data.get("CURED_COUNT"), "-");
        curedSum = Objects.toString(data.get("CURED_SUM"), "-");
    }
}
