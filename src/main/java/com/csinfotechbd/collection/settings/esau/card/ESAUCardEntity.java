package com.csinfotechbd.collection.settings.esau.card;
/*
  Created by Md. Monirul Islam on 9/11/2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "ESAU_RATING_CARD")
public class ESAUCardEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<AgeCodeEntity> ageCodeEntities;

    private int countMonth;
    private Double finalAvgUpperLimit;
    private Double finalAvgLowerLimit;
    private String ratingName;
}
