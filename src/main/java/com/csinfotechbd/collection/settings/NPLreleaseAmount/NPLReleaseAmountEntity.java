package com.csinfotechbd.collection.settings.NPLreleaseAmount;
/*
Created by Monirul Islam on 7/9/2019
*/


import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "NPL_RELEASE_CARD")
public class NPLReleaseAmountEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "NPL_RELEASE_CARD_ID")
    private List<AgeCodeEntity> ageCodeList = new ArrayList<>();
}
