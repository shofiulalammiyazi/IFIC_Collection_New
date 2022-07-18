package com.csinfotechbd.collection.KPI.agency.card.targetByCommision;
/*
Created by Monirul Islam at 9/22/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "ag_comm_card_set")
public class AgencyKpiCommisionRateCardSetup extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<AgeCodeEntity> ageCodeEntityList = new ArrayList<>();
    private double minTargetAchievement;
    private double maxTargetAchievement;
    private double commisionRate;


    @Transient
    private List<String> dpdBucketIds = new ArrayList<>();
}
