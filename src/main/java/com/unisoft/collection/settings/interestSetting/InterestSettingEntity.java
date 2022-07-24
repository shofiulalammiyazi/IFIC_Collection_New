package com.unisoft.collection.settings.interestSetting;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class InterestSettingEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer saveAmountEmi;
    private Integer saveAmountOverdue;
    private Integer backAmountEmi;
    private Integer backAmountOverdue;
}
