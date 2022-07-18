package com.csinfotechbd.collection.automaticDistribution.distributionExceptionLoanModel;
/*
Created by Monirul Islam at 9/5/2019
*/

import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ProductGroupDpdBucket {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Expose
    private String productGroup;

    @Expose
    private String dpdBucket;
}
