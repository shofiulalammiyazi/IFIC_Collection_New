package com.csinfotechbd.collection.samd.dashboard.shortfallDetails;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Optional;

@Data
public class SamdDashboardShortFallDetails {

    private double minDueShortFall;
    private double saveAmountShortFall;
    private double backAmountShortFall;
    private double perDayShortFall;

    public SamdDashboardShortFallDetails() {
    }

    public SamdDashboardShortFallDetails(Tuple data) {
        if (data == null) return;
        minDueShortFall = ((Number) Optional.ofNullable(data.get("minDueShortFall")).orElse(0)).doubleValue();
        saveAmountShortFall = ((Number) Optional.ofNullable(data.get("saveAmountShortFall")).orElse(0)).doubleValue();
        backAmountShortFall = ((Number) Optional.ofNullable(data.get("backAmountShortFall")).orElse(0)).doubleValue();
        perDayShortFall = ((Number) Optional.ofNullable(data.get("perDayShortFall")).orElse(0)).doubleValue();

    }

}
