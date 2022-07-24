package com.unisoft.collection.settings.esau.card;
/*
  Created by Md.   Islam on 9/11/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
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
