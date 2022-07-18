package com.csinfotechbd.collection.settings.atm;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.district.DistrictEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AtmEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    private String type;

    private String note;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    private DistrictEntity district;

    /**
     * Provides Json string from the entity
     *
     * @return Json string
     */
    @Override
    public String toString() {
        return "{'id':" + id + ",'name':'" + name + "', 'district':" + district + "}";
    }

}
