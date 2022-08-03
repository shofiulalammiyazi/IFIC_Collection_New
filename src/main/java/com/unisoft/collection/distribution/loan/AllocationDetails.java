package com.unisoft.collection.distribution.loan;

import lombok.Data;

import javax.persistence.Tuple;
import java.math.BigDecimal;

@Data
public class AllocationDetails {
    String dealerName;
    String dealerId;
    String dpdBucket;
    long totalAcc;
    double totalOS;
    double totalOD;

    public AllocationDetails(String dealerName, String dealerId, String dpdBucket, long totalAcc, double totalOS, double totalOD) {
        this.dealerName = dealerName;
        this.dealerId = dealerId;
        this.dpdBucket = dpdBucket;
        this.totalAcc = totalAcc;
        this.totalOS = totalOS;
        this.totalOD = totalOD;
    }

    public AllocationDetails(Tuple tuple) {
        this.dealerName = (String) tuple.get(0);
        this.dealerId = (String) tuple.get(1);
        this.dpdBucket = (String) tuple.get(2);
        BigDecimal b = (BigDecimal)tuple.get(3);
        BigDecimal b1 = (BigDecimal)tuple.get(4);
        BigDecimal b2 = (BigDecimal)tuple.get(5);
        this.totalAcc = b.longValue();
        this.totalOS = b1.doubleValue();
        this.totalOD = b2.doubleValue();
    }
}
