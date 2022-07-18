package com.csinfotechbd.retail.loan.dataEntry.kpiData;
/*
  Created by Yasir Araphat on 3 May 2021
*/

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class LoanKpiAchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String accountNumber;

    private double totalPayment;

    private String dealerPin;
    private String cusId;
    private String dpdBucket;

    @Transient
    private double amntPerPT;

    //Flow
    private double flowAch;
    private double flowPerformance;
    private double flowShortFall;
    private double flowWeight;
    private double flowWeightedPerformance;
    private double flowTarget;
    private double flowTargetPerc;
    @Transient
    private double flowAmntPerDay;

    //save
    private double saveAch;
    private double savePerformance;
    private double saveShortFall;
    private double saveWeight;
    private double saveWeightedPerformance;
    private double saveTarget;
    private double saveTargetPerc;
    @Transient
    private double saveAmntPerDay;

    //back
    private double backAch;
    private double backPerformance;
    private double backShortFall;
    private double backWeight;
    private double backWeightedPerformance;
    private double backTarget;
    private double backTargetPerc;
    @Transient
    private double backAmntPerDay;

    //reg
    private double regAch;
    private double regPerformance;
    private double regShortFall;
    private double regWeight;
    private double regWeightedPerformance;
    private double regTarget;
    private double regTargetPerc;
    @Transient
    private double regAmntPerDay;

    //par
    private double parAch;
    private double parPerformance;
    private double parShortFall;
    private double parWeight;
    private double parWeightedPerformance;
    private double parTarget;
    private double parTargetPerc;
    @Transient
    private double parAmntPerDay;

    //npl
    private double nplAch;
    private double nplPerformance;
    private double nplShortFall;
    private double nplWeight;
    private double nplWeightedPerformance;
    private double nplTarget;
    private double nplTargetPerc;
    @Transient
    private double nplAmntPerDay;

    //raw
    private double rawAch;
    private double rawPerformance;
    private double rawShortFall;
    private double rawWeight;
    private double rawWeightedPerformance;
    private double rawTarget;
    private double rawTargetPerc;
    @Transient
    private double rawAmntPerDay;

    //min due
    private double moMinDueAmnt;
    private double minAch;
    private double minPerformance;
    private double minShortFall;
    private double minWeight;
    private double minWeightedPerformance;
    private double minTarget;
    private double minTargetPerc;
    @Transient
    private double minAmntPerDay;

    private String revCode = "";
    private double revAmnt;
    private double newOutstanding;
    private boolean disputeIndicator;

}
