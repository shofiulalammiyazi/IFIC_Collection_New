package com.unisoft.collection.distribution.card;
/*
Created by   Islam at 9/25/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "acc_loc_map")
public class AccountLocationMapping extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH)
    private CustomerBasicInfoEntity customer;

    @OneToOne(cascade = CascadeType.REFRESH)
    private LocationEntity location;

    private String custComAddress;

    @Transient
    String customerId;

    String locationName;
}
