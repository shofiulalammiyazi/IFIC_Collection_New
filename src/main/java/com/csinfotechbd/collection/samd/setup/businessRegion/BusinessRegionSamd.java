package com.csinfotechbd.collection.samd.setup.businessRegion;

import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class BusinessRegionSamd extends CommonEntity {

    @Transient
    private List<String> locationIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LocationEntity> locations = new ArrayList<>();

    @Column(nullable = true)
    private String regionName;
}
