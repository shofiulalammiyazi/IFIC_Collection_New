package com.unisoft.collection.settings.PARreleaseAmount;
/*
Created by   Islam on 7/9/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "PAR_RELEASE_CARD")
public class PARReleaseAmountEntity extends BaseInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade =CascadeType.REFRESH)
    @JoinColumn(name="PAR_RELEASE_CARD_ID")
    private List<AgeCodeEntity> ageCodeList;
}
