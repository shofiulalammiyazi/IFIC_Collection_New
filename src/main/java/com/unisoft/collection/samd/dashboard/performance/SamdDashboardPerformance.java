package com.unisoft.collection.samd.dashboard.performance;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class SamdDashboardPerformance {

    private double performanceAchievement;
    private String lastMonthRating;
    private double remainingParAccount;
    private double remainingParAmount;
    private double remainingNplAccount;
    private double remainingNplAmount;
    private double remainingParQueAccount;
    private double remainingParQueAmount;
    private double remainingNplQueAccount;
    private double remainingNplQueAmount;

    public SamdDashboardPerformance() {
    }

    public SamdDashboardPerformance(Tuple data) {
        if (data == null) return;
        performanceAchievement = ((Number) Optional.ofNullable(data.get("performanceAchievement")).orElse(0)).doubleValue();
        lastMonthRating = Objects.toString(data.get("lastMonthRating"), "-");
        remainingParAccount = ((Number) Optional.ofNullable(data.get("remainingParAccount")).orElse(0)).doubleValue();
        remainingParAmount = ((Number) Optional.ofNullable(data.get("remainingParAmount")).orElse(0)).doubleValue();
        remainingNplAccount = ((Number) Optional.ofNullable(data.get("remainingNplAccount")).orElse(0)).doubleValue();
        remainingNplAmount = ((Number) Optional.ofNullable(data.get("remainingNplAmount")).orElse(0)).doubleValue();
        remainingParQueAccount = ((Number) Optional.ofNullable(data.get("remainingParQueAccount")).orElse(0)).doubleValue();
        remainingParQueAmount = ((Number) Optional.ofNullable(data.get("remainingParQueAmount")).orElse(0)).doubleValue();
        remainingNplQueAccount = ((Number) Optional.ofNullable(data.get("remainingNplQueAccount")).orElse(0)).doubleValue();
        remainingNplQueAmount = ((Number) Optional.ofNullable(data.get("remainingNplQueAmount")).orElse(0)).doubleValue();
    }

}
