package com.unisoft.collection.settings.PARAccountRuleCard;
/*
Created by   Islam on 7/14/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PAR_ACC_RULE_CARD")
@Data
public class PARAccountRuleCardEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private List<AgeCodeEntity> ageCodeList;
}
