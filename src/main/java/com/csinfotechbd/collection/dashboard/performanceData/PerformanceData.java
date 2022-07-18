package com.csinfotechbd.collection.dashboard.performanceData;
/*
  Created by Md. Monirul Islam on 1/1/2020
*/

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PerformanceData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String unit;
    
    private String userPin;

    private double flowPerf;
    private double savePerf;
    private double backPerf;
//    private double parPerf;
//    private double nplPerf;

}
