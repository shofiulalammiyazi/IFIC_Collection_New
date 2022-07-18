package com.csinfotechbd.collection.settings.deviationSetDistribution;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class DeviationSetDistributionInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<AgeCodeEntity> ageCode= new ArrayList<>();

    private double maxDeviation;

    @Transient
    private List<String> ageCodeIds = new ArrayList<>();
}
