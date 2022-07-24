package com.unisoft.collection.emi;

import lombok.Data;

@Data
public class EmiEmtityDto {

    private String contractId;
    private String paymentDate;
    private double bdtEmi;
    private double usdEmi;
}
