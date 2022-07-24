package com.unisoft.collection.dashboard;
/*
  Created by Md.   Islam on 2/1/2020
*/

import lombok.Data;

@Data
public class CardPerformanceDatamodel {

    private int ageCode=0;
    private double savePerf=0;
    private double flowPerf=0;
    private double backPerf=0;
    private double minDuePerf=0;

    private int ageCodeCount=0;
    private int producttypeCount=0;
}
