package com.unisoft.collection.settings.zone;
/*
Created by   Islam at 7/3/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.location.LocationEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ZoneEntity extends BaseInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    private String name;
}
