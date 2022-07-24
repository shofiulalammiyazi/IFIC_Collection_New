package com.unisoft.retail.loan.dashboard.kpi;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Data model for KPI performance matrix.
 * Calculation logics -
 * 1) Shortfall = Target - Achievement (Minimum startfall 0)
 * 2) Performance = Achievement / Target
 * 3) Flow Performance = 2 - (Achievement / Target)
 * 4) Weighted Performance = Performance * weight
 * 4) Final Performance = Sum of all performance percentage
 * <p>
 * N.B. No target, no performance. Performance is less than  or equal to 25%, no performance.
 * <p>
 * Implemented by Yasir Araphat
 * on August 10, 2021
 */

@Data
@Entity
public class LoanKpiTargetVsAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String dealerPin;
    private String productGroup;
    private String dpdBucket;

    private double total;

    private double flowTarget;
    private double flowAchievement;
    private double flowShortfall;
    private double flowPerformance;
    private double flowWeight;
    private double flowWeightedPerformance;

    private double saveTarget;
    private double saveAchievement;
    private double saveShortfall;
    private double savePerformance;
    private double saveWeight;
    private double saveWeightedPerformance;

    private double backTarget;
    private double backAchievement;
    private double backShortfall;
    private double backPerformance;
    private double backWeight;
    private double backWeightedPerformance;

    private double overdueTarget;
    private double overdueAchievement;
    private double overdueShortfall;
    private double overduePerformance;
    private double overdueWeight;
    private double overdueWeightedPerformance;

    private double regularTarget;
    private double regularAchievement;
    private double regularShortfall;
    private double regularPerformance;
    private double regularWeight;
    private double regularWeightedPerformance;

    private double flowTargetPercentage;
    private double flowAchievementPercentage;
    private double flowShortfallPercentage;
    private double flowPerformancePercentage;
    private double flowWeightPercentage;
    private double flowWeightedPerformancePercentage;


    private double saveTargetPercentage;
    private double saveAchievementPercentage;
    private double saveShortfallPercentage;
    private double savePerformancePercentage;
    private double saveWeightPercentage;
    private double saveWeightedPerformancePercentage;

    private double backTargetPercentage;
    private double backAchievementPercentage;
    private double backShortfallPercentage;
    private double backPerformancePercentage;
    private double backWeightPercentage;
    private double backWeightedPerformancePercentage;

    private double overdueTargetPercentage;
    private double overdueAchievementPercentage;
    private double overdueShortfallPercentage;
    private double overduePerformancePercentage;
    private double overdueWeightPercentage;
    private double overdueWeightedPerformancePercentage;

    private double regularTargetPercentage;
    private double regularAchievementPercentage;
    private double regularShortfallPercentage;
    private double regularPerformancePercentage;
    private double regularWeightPercentage;
    private double regularWeightedPerformancePercentage;


    private double finalPerformance;


    private Date createdDate;

    public LoanKpiTargetVsAchievement() {
    }

    public LoanKpiTargetVsAchievement(Tuple data) {
        dealerPin = Objects.toString(data.get("DEALER_PIN"), "");
        productGroup = Objects.toString(data.get("PRODUCT"), "");
        dpdBucket = Objects.toString(data.get("BUCKET"), "");

        total = ((Number) Optional.ofNullable(data.get("TOTAL")).orElse(0)).doubleValue();

        flowTarget = ((Number) Optional.ofNullable(data.get("FLOW_TARGET")).orElse(0)).doubleValue();
        flowAchievement = ((Number) Optional.ofNullable(data.get("FLOW_ACHIEVEMENT")).orElse(0)).doubleValue();
        setFlowShortfall();
        setFlowPerformance();
        flowWeight = ((Number) Optional.ofNullable(data.get("FLOW_WEIGHT")).orElse(0)).doubleValue();
        setFlowWeightedPerformance();

        saveTarget = ((Number) Optional.ofNullable(data.get("SAVE_TARGET")).orElse(0)).doubleValue();
        saveAchievement = ((Number) Optional.ofNullable(data.get("SAVE_ACHIEVEMENT")).orElse(0)).doubleValue();
        setSaveShortfall();
        setSavePerformance();
        saveWeight = ((Number) Optional.ofNullable(data.get("SAVE_WEIGHT")).orElse(0)).doubleValue();
        setSaveWeightedPerformance();

        backTarget = ((Number) Optional.ofNullable(data.get("BACK_TARGET")).orElse(0)).doubleValue();
        backAchievement = ((Number) Optional.ofNullable(data.get("BACK_ACHIEVEMENT")).orElse(0)).doubleValue();
        setBackShortfall();
        setBackPerformance();
        backWeight = ((Number) Optional.ofNullable(data.get("BACK_WEIGHT")).orElse(0)).doubleValue();
        setBackWeightedPerformance();

        overdueTarget = ((Number) Optional.ofNullable(data.get("OVERDUE_TARGET")).orElse(0)).doubleValue();
        overdueAchievement = ((Number) Optional.ofNullable(data.get("OVERDUE_ACHIEVEMENT")).orElse(0)).doubleValue();
        setOverdueShortfall();
        setOverduePerformance();
        overdueWeight = ((Number) Optional.ofNullable(data.get("OVERDUE_WEIGHT")).orElse(0)).doubleValue();
        setOverdueWeightedPerformance();

        regularTarget = ((Number) Optional.ofNullable(data.get("REGULAR_TARGET")).orElse(0)).doubleValue();
        regularAchievement = ((Number) Optional.ofNullable(data.get("REGULAR_ACHIEVEMENT")).orElse(0)).doubleValue();
        setRegularShortfall();
        setRegularPerformance();
        regularWeight = ((Number) Optional.ofNullable(data.get("REGULAR_WEIGHT")).orElse(0)).doubleValue();
        setRegularWeightedPerformance();


        flowTargetPercentage = ((Number) Optional.ofNullable(data.get("FLOW_TARGET_PERCENTAGE")).orElse(0)).doubleValue();
        flowAchievementPercentage = ((Number) Optional.ofNullable(data.get("FLOW_ACHIEVEMENT_PERCENTAGE")).orElse(0)).doubleValue();
        setFlowShortfallPercentage();
        setFlowPerformancePercentage();
        flowWeightPercentage = ((Number) Optional.ofNullable(data.get("FLOW_WEIGHT_PERCENTAGE")).orElse(0)).doubleValue();
        setFlowWeightedPerformancePercentage();

        saveTargetPercentage = ((Number) Optional.ofNullable(data.get("SAVE_TARGET_PERCENTAGE")).orElse(0)).doubleValue();
        saveAchievementPercentage = ((Number) Optional.ofNullable(data.get("SAVE_ACHIEVEMENT_PERCENTAGE")).orElse(0)).doubleValue();
        setSaveShortfallPercentage();
        setSavePerformancePercentage();
        saveWeightPercentage = ((Number) Optional.ofNullable(data.get("SAVE_WEIGHT_PERCENTAGE")).orElse(0)).doubleValue();
        setSaveWeightedPerformancePercentage();

        backTargetPercentage = ((Number) Optional.ofNullable(data.get("BACK_TARGET_PERCENTAGE")).orElse(0)).doubleValue();
        backAchievementPercentage = ((Number) Optional.ofNullable(data.get("BACK_ACHIEVEMENT_PERCENTAGE")).orElse(0)).doubleValue();
        setBackShortfallPercentage();
        setBackPerformancePercentage();
        backWeightPercentage = ((Number) Optional.ofNullable(data.get("BACK_WEIGHT_PERCENTAGE")).orElse(0)).doubleValue();
        setBackWeightedPerformancePercentage();

        overdueTargetPercentage = ((Number) Optional.ofNullable(data.get("OVERDUE_TARGET_PERCENTAGE")).orElse(0)).doubleValue();
        overdueAchievementPercentage = ((Number) Optional.ofNullable(data.get("OVERDUE_ACHIEVEMENT_PERCENTAGE")).orElse(0)).doubleValue();
        setOverdueShortfallPercentage();
        setOverduePerformancePercentage();
        overdueWeightPercentage = ((Number) Optional.ofNullable(data.get("OVERDUE_WEIGHT_PERCENTAGE")).orElse(0)).doubleValue();
        setOverdueWeightedPerformancePercentage();

        regularTargetPercentage = ((Number) Optional.ofNullable(data.get("REGULAR_TARGET_PERCENTAGE")).orElse(0)).doubleValue();
        regularAchievementPercentage = ((Number) Optional.ofNullable(data.get("REGULAR_ACHIEVEMENT_PERCENTAGE")).orElse(0)).doubleValue();
        setRegularShortfallPercentage();
        setRegularPerformancePercentage();
        regularWeightPercentage = ((Number) Optional.ofNullable(data.get("REGULAR_WEIGHT_PERCENTAGE")).orElse(0)).doubleValue();
        setRegularWeightedPerformancePercentage();

        setFinalPerformance();
    }

    public void setFlowShortfall() {
        this.flowShortfall = Math.max(0.01, flowTarget - flowAchievement);
    }

    public void setFlowPerformance() {
        if (flowTarget > 0) {
            double performance = 2 - (flowAchievement / flowTarget);
            this.flowPerformance = performance <= flowTarget / 4 ? 0.0 : performance;
        }
    }

    public void setFlowWeightedPerformance() {
        if (flowTarget > 0) {
            this.flowWeightedPerformance = flowPerformance * flowWeight;
        }
    }

    public void setSaveShortfall() {
        this.saveShortfall = Math.max(0.01, saveTarget - saveAchievement);
    }

    public void setSavePerformance() {
        if (saveTarget > 0) {
            double performance = saveAchievement / saveTarget;
            this.savePerformance = performance <= saveTarget / 4 ? 0.0 : performance;
        }
    }

    public void setSaveWeightedPerformance() {
        if (saveTarget > 0) {
            this.saveWeightedPerformance = savePerformance * saveWeight;
        }
    }

    public void setBackShortfall() {
        this.backShortfall = Math.max(0.01, backTarget - backAchievement);
    }

    public void setBackPerformance() {
        if (backTarget > 0) {
            double performance = backAchievement / backTarget;
            this.backPerformance = performance <= backTarget / 4 ? 0.0 : performance;
        }
    }

    public void setBackWeightedPerformance() {
        if (backTarget > 0) {
            this.backWeightedPerformance = backPerformance * backWeight;
        }
    }

    public void setOverdueShortfall() {
        this.overdueShortfall = Math.max(0.01, overdueTarget - overdueAchievement);
    }

    public void setOverduePerformance() {
        if (overdueTarget > 0) {
            double performance = overdueAchievement / overdueTarget;
            this.overduePerformance = performance <= overdueTarget / 4 ? 0.0 : performance;
        }
    }

    public void setOverdueWeightedPerformance() {
        if (overdueTarget > 0) {
            this.overdueWeightedPerformance = overduePerformance * overdueWeight;
        }
    }

    public void setRegularShortfall() {
        this.regularShortfall = Math.max(0.01, regularTarget - regularAchievement);
    }

    public void setRegularPerformance() {
        if (regularTarget > 0) {
            double performance = regularAchievement / regularTarget;
            this.regularPerformance = performance <= regularTarget / 4 ? 0.0 : performance;
        }
    }

    public void setRegularWeightedPerformance() {
        if (regularTarget > 0) {
            this.regularWeightedPerformance = regularPerformance * regularWeight;
        }
    }

    public void setFlowShortfallPercentage() {
        this.flowShortfallPercentage = Math.max(0.01, flowTargetPercentage - flowAchievementPercentage);
    }

    public void setFlowPerformancePercentage() {
        if (flowTargetPercentage > 0) {
            double performance = 2 - (flowAchievementPercentage / flowTargetPercentage);
            this.flowPerformancePercentage = performance <= flowTargetPercentage / 4 ? 0.0 : performance;
        }
    }

    public void setFlowWeightedPerformancePercentage() {
        if (flowTargetPercentage > 0) {
            this.flowWeightedPerformancePercentage = flowPerformancePercentage * flowWeightPercentage;
        }
    }

    public void setSaveShortfallPercentage() {
        this.saveShortfallPercentage = Math.max(0.01, saveTargetPercentage - saveAchievementPercentage);
    }

    public void setSavePerformancePercentage() {
        if (saveTargetPercentage > 0) {
            double performance = saveAchievementPercentage / saveTargetPercentage;
            this.savePerformancePercentage = performance <= saveTargetPercentage / 4 ? 0.0 : performance;
        }
    }

    public void setSaveWeightedPerformancePercentage() {
        if (saveTargetPercentage > 0) {
            this.saveWeightedPerformancePercentage = savePerformancePercentage * saveWeightPercentage;
        }
    }

    public void setBackShortfallPercentage() {
        this.backShortfallPercentage = Math.max(0.01, backTargetPercentage - backAchievementPercentage);
    }

    public void setBackPerformancePercentage() {
        if (backTargetPercentage > 0) {
            double performance = backAchievementPercentage / backTargetPercentage;
            this.backPerformancePercentage = performance <= backTargetPercentage / 4 ? 0.0 : performance;
        }
    }

    public void setBackWeightedPerformancePercentage() {
        if (backTargetPercentage > 0) {
            this.backWeightedPerformancePercentage = backPerformancePercentage * backWeightPercentage;
        }
    }

    public void setOverdueShortfallPercentage() {
        this.overdueShortfallPercentage = Math.max(0.01, overdueTargetPercentage - overdueAchievementPercentage);
    }

    public void setOverduePerformancePercentage() {
        if (overdueTargetPercentage > 0) {
            double performance = overdueAchievementPercentage / overdueTargetPercentage;
            this.overduePerformancePercentage = performance <= overdueTargetPercentage / 4 ? 0.0 : performance;
        }
    }

    public void setOverdueWeightedPerformancePercentage() {
        if (overdueTargetPercentage > 0) {
            this.overdueWeightedPerformancePercentage = overduePerformancePercentage * overdueWeightPercentage;
        }
    }

    public void setRegularShortfallPercentage() {
        this.regularShortfallPercentage = Math.max(0.01, regularTargetPercentage - regularAchievementPercentage);
    }

    public void setRegularPerformancePercentage() {
        if (regularTargetPercentage > 0) {
            double performance = regularAchievementPercentage / regularTargetPercentage;
            this.regularPerformancePercentage = performance <= regularTargetPercentage / 4 ? 0.0 : performance;
        }
    }

    public void setRegularWeightedPerformancePercentage() {
        if (regularTargetPercentage > 0) {
            this.regularWeightedPerformancePercentage = regularPerformancePercentage * regularWeightPercentage;
        }
    }

    public void setFinalPerformance() {
        this.finalPerformance = flowWeightedPerformancePercentage + saveWeightedPerformancePercentage + backWeightedPerformancePercentage + overdueWeightedPerformancePercentage + regularWeightedPerformancePercentage;
    }
}
