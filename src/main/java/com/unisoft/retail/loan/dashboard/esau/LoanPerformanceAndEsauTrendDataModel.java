package com.unisoft.retail.loan.dashboard.esau;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class LoanPerformanceAndEsauTrendDataModel {

    String dealerPin;

    double currentMonthAchievement;
    String currentMonthRating;

    double oneMonthAgoAchievement;
    String oneMonthAgoRating;

    double lastThreeMonthAchievementAverage;
    double lastSixMonthAchievementAverage;
    double lastTwelveMonthAchievementAverage;

    double twoMonthsAgoAchievement;
    String twoMonthsAgoRating;

    double threeMonthsAgoAchievement;
    String threeMonthsAgoRating;

    double fourMonthsAgoAchievement;
    String fourMonthsAgoRating;

    double fiveMonthsAgoAchievement;
    String fiveMonthsAgoRating;

    double sixMonthAgoAchievement;
    String sixMonthAgoRating;


    public LoanPerformanceAndEsauTrendDataModel() {
    }

    public LoanPerformanceAndEsauTrendDataModel(Tuple data) {
        dealerPin = Objects.toString(data.get("DEALER_PIN"), "");

        currentMonthAchievement = ((Number) Optional.ofNullable(data.get("CURRENT_MONTH_ACHIEVEMENT")).orElse(0)).doubleValue();
        currentMonthRating = Objects.toString(data.get("CURRENT_MONTH_RATING"), "");

        oneMonthAgoAchievement = ((Number) Optional.ofNullable(data.get("ONE_MONTH_AGO_ACHIEVEMENT")).orElse(0)).doubleValue();
        oneMonthAgoRating = Objects.toString(data.get("ONE_MONTH_AGO_RATING"), "");

        lastThreeMonthAchievementAverage = ((Number) Optional.ofNullable(data.get("LAST_THREE_MONTH_ACHIEVEMENT_AVERAGE")).orElse(0)).doubleValue();
        lastSixMonthAchievementAverage = ((Number) Optional.ofNullable(data.get("LAST_SIX_MONTH_ACHIEVEMENT_AVERAGE")).orElse(0)).doubleValue();
        lastTwelveMonthAchievementAverage = ((Number) Optional.ofNullable(data.get("LAST_TWELVE_MONTH_ACHIEVEMENT_AVERAGE")).orElse(0)).doubleValue();

        twoMonthsAgoAchievement = ((Number) Optional.ofNullable(data.get("TWO_MONTH_AGO_ACHIEVEMENT")).orElse(0)).doubleValue();
        twoMonthsAgoRating = Objects.toString(data.get("TWO_MONTH_AGO_RATING"), "");

        threeMonthsAgoAchievement = ((Number) Optional.ofNullable(data.get("THREE_MONTH_AGO_ACHIEVEMENT")).orElse(0)).doubleValue();
        threeMonthsAgoRating = Objects.toString(data.get("THREE_MONTH_AGO_RATING"), "");

        fourMonthsAgoAchievement = ((Number) Optional.ofNullable(data.get("FOUR_MONTH_AGO_ACHIEVEMENT")).orElse(0)).doubleValue();
        fourMonthsAgoRating = Objects.toString(data.get("FOUR_MONTH_AGO_RATING"), "");

        fiveMonthsAgoAchievement = ((Number) Optional.ofNullable(data.get("FIVE_MONTH_AGO_ACHIEVEMENT")).orElse(0)).doubleValue();
        fiveMonthsAgoRating = Objects.toString(data.get("FIVE_MONTH_AGO_RATING"), "");

        sixMonthAgoAchievement = ((Number) Optional.ofNullable(data.get("SIX_MONTH_AGO_ACHIEVEMENT")).orElse(0)).doubleValue();
        sixMonthAgoRating = Objects.toString(data.get("SIX_MONTH_AGO_RATING"), "");
        
    }
}
