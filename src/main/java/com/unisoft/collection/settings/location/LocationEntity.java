package com.unisoft.collection.settings.location;
/*
Created by   Islam at 6/19/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class LocationEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String SOLID;
    private String city;
    private String contactNo;
    private String contactAddress;

    public LocationEntity() {
    }

    public LocationEntity(Long id) {
        this.id = id;
    }
}
