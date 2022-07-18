package com.csinfotechbd.collection.KPI.esau;
/*
Created by Monirul Islam at 9/23/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class EsauDefaultRatingSetup extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loanEsauRating;
    private String cardEsauRating;
}
