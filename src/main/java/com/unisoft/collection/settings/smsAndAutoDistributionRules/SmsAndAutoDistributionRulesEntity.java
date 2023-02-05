package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class SmsAndAutoDistributionRulesEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String unpaidInstallmentNumber;

}
