package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 2/1/2020
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class DealerPerformanceDataEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dealerPin;
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss" )
    private Date performanceDate;

    private double performanceAvg;
    private String unit;



}
