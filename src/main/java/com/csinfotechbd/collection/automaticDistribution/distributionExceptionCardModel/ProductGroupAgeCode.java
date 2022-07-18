package com.csinfotechbd.collection.automaticDistribution.distributionExceptionCardModel;
/*
Created by Monirul Islam at 9/8/2019
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ProductGroupAgeCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productGroup;

    private String ageCode;
}
