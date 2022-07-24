package com.unisoft.collection.dashboard;


import lombok.Data;


@Data
public class KpiVsAchDataModel{

    private int ageCode;
    private String cardsCategory;
    private double amntPerAgeCode=0;

    //Flow
    private Double flowAch = 0d;
    private Double flowPerformance= 0d;
    private Double flowShortFall= 0d;
    private Double flowWeight= 0d;
    private Double flowWeightedPerformance= 0d;
    private double flowTarget= 0d;
    private double flowAmntPerDay= 0d;

    private Double flowAchPerc= 0d;
    private double flowTargetPerc= 0d;

    //save
    private Double saveAch= 0d;
    private Double savePerformance= 0d;
    private Double saveShortFall= 0d;
    private Double saveWeight= 0d;
    private Double saveWeightedPerformance= 0d;
    private double saveTarget= 0d;
    private double saveAmntPerDay= 0d;

    private Double saveAchPerc= 0d;
    private double saveTargetPerc= 0d;

    //back
    private Double backAch= 0d;
    private Double backPerformance= 0d;
    private Double backShortFall= 0d;
    private Double backWeight= 0d;
    private Double backWeightedPerformance= 0d;
    private double backTarget= 0d;
    private double backAmntPerDay= 0d;

    private Double backAchPerc= 0d;
    private double backTargetPerc= 0d;

    //reg
    private Double regAch= 0d;
    private Double regPerformance= 0d;
    private Double regShortFall= 0d;
    private Double regWeight= 0d;
    private Double regWeightedPerformance= 0d;
    private double regTarget= 0d;
    private double regAmntPerDay= 0d;

    private Double regkAchPerc= 0d;
    private double regTargetPerc= 0d;

    //par
    private Double parAch= 0d;
    private Double parPerformance= 0d;
    private Double parShortFall= 0d;
    private Double parWeight= 0d;
    private Double parWeightedPerformance= 0d;
    private double parTarget= 0d;
    private double parAmntPerDay= 0d;

    private Double parkAchPerc= 0d;
    private double parTargetPerc= 0d;

    //npl
    private Double nplAch= 0d;
    private Double nplPerformance= 0d;
    private Double nplShortFall= 0d;
    private Double nplWeight= 0d;
    private Double nplWeightedPerformance= 0d;
    private double nplTarget= 0d;
    private double nplAmntPerDay= 0d;

    private Double nplkAchPerc= 0d;
    private double nplTargetPerc= 0d;

    //raw
    private Double rawAch= 0d;
    private Double rawPerformance= 0d;
    private Double rawShortFall= 0d;
    private Double rawWeight= 0d;
    private Double rawWeightedPerformance= 0d;
    private double rawTarget= 0d;
    private double rawAmntPerDay= 0d;

    private Double rawkAchPerc= 0d;
    private double rawTargetPerc= 0d;

    //min due
    private Double minAch= 0d;
    private Double minPerformance= 0d;
    private Double minShortFall= 0d;
    private Double minWeight= 0d;
    private Double minWeightedPerformance= 0d;
    private double minTarget= 0d;
    private double minAmntPerDay= 0d;

    private Double minkAchPerc= 0d;
    private double minTargetPerc= 0d;

}
