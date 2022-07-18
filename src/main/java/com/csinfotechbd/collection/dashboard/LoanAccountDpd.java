package com.csinfotechbd.collection.dashboard;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
  Created by Md. Monirul Islam on 1/15/2020
*/
@Data
@Entity
public class LoanAccountDpd extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accNo;
    private double currentDpd;
    private double ate;
}
