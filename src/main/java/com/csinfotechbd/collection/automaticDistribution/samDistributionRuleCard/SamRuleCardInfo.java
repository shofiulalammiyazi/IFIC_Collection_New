package com.csinfotechbd.collection.automaticDistribution.samDistributionRuleCard;
/*
Created by Monirul Islam at 8/8/2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class SamRuleCardInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<AgeCodeEntity> ageCodeEntityList = new ArrayList<>();

    @Transient
    private List<String> ageCodeIds = new ArrayList<>();
}
