package com.unisoft.collection.settings.earlyNotifyEngine;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class EarlyNotifyEngine extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private double cumulative;
    private double maxPso;
    private double maxCompany;
    private double maxDpd;
    private double maxPtp;
    private double maxOverlimit;
}
