package com.unisoft.collection.samd.setup.businessSegment;

import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.common.CommonEntity;
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
public class BusinessSegmentSamd extends CommonEntity {

    @Transient
    private List<String> locationIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LocationEntity> locations = new ArrayList<>();

    @Column(nullable = true)
    private String segmentName;
}
