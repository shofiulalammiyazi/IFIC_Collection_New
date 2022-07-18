package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 1/28/2020
*/

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class CardKpiAchManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss" )
    private Date updateDate;

    @Column(unique = true)
    private String finAccNo;

    private Double totalPayment=0d;

    private String cardAccNo;
    private String dealerPin;
    private String cusId;
    private int ageCode;
    private String cardsCategory;

    @Transient
    private Double amntPerPT;

    //Flow
    private Double flowAch = 0d;
    private Double flowPerformance= 0d;
    private Double flowShortFall= 0d;
    private Double flowWeight= 0d;
    private Double flowWeightedPerformance= 0d;
    private double flowTarget= 0d;
    private double flowTargetPerc= 0d;
    @Transient
    private double flowAmntPerDay= 0d;

    //save
    private Double saveAch= 0d;
    private Double savePerformance= 0d;
    private Double saveShortFall= 0d;
    private Double saveWeight= 0d;
    private Double saveWeightedPerformance= 0d;
    private double saveTarget= 0d;
    private double saveTargetPerc= 0d;
    @Transient
    private double saveAmntPerDay= 0d;

    //back
    private Double backAch= 0d;
    private Double backPerformance= 0d;
    private Double backShortFall= 0d;
    private Double backWeight= 0d;
    private Double backWeightedPerformance= 0d;
    private double backTarget= 0d;
    private double backTargetPerc= 0d;
    @Transient
    private double backAmntPerDay= 0d;

    //reg
    private Double regAch= 0d;
    private Double regPerformance= 0d;
    private Double regShortFall= 0d;
    private Double regWeight= 0d;
    private Double regWeightedPerformance= 0d;
    private double regTarget= 0d;
    private double regTargetPerc= 0d;
    @Transient
    private double regAmntPerDay= 0d;

    //par
    private Double parAch= 0d;
    private Double parPerformance= 0d;
    private Double parShortFall= 0d;
    private Double parWeight= 0d;
    private Double parWeightedPerformance= 0d;
    private double parTarget= 0d;
    private double parTargetPerc= 0d;
    @Transient
    private double parAmntPerDay= 0d;

    //npl
    private Double nplAch= 0d;
    private Double nplPerformance= 0d;
    private Double nplShortFall= 0d;
    private Double nplWeight= 0d;
    private Double nplWeightedPerformance= 0d;
    private double nplTarget= 0d;
    private double nplTargetPerc= 0d;
    @Transient
    private double nplAmntPerDay= 0d;

    //raw
    private Double rawAch= 0d;
    private Double rawPerformance= 0d;
    private Double rawShortFall= 0d;
    private Double rawWeight= 0d;
    private Double rawWeightedPerformance= 0d;
    private double rawTarget= 0d;
    private double rawTargetPerc= 0d;
    @Transient
    private double rawAmntPerDay= 0d;

    //min due
    private Double moMinDueAmnt;
    private Double minAch= 0d;
    private Double minPerformance= 0d;
    private Double minShortFall= 0d;
    private Double minWeight= 0d;
    private Double minWeightedPerformance= 0d;
    private double minTarget= 0d;
    private double minTargetPerc=0d;
    @Transient
    private double minAmntPerDay= 0d;

    private String revCode="";
    private double revAmnt;
    private double newOutstanding;
    private boolean disputeIndicator;
}
