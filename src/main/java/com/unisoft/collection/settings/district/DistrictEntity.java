package com.unisoft.collection.settings.district;
/*
Created by   Islam at 7/3/2019
*/

import com.google.gson.annotations.Expose;
import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.division.DivisionEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.zone.ZoneEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class DistrictEntity extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    private String name;

    //    Changes made for lawyer table
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "division_id")
    DivisionEntity division;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;


    public DistrictEntity() {
    }

    public DistrictEntity(Long id) {
        this.id = id;
    }


    /**
     * Provides Json string from the entity
     *
     * @return Json string
     */
    @Override
    public String toString() {
        return "{'id':" + id + ", 'name':'" + name + "', 'division':" + division + "}";
    }
}
