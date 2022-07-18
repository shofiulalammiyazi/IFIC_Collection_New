package com.csinfotechbd.collection.settings.dunningLetterRulesCard;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.manualLetterSetup.ManualLetterSetupInfo;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DunningLetterRulesCardInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductTypeEntity> productTypeCard = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<AgeCodeEntity> ageCode = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ManualLetterSetupInfo> letterType = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    List<LocationEntity> location = new ArrayList<>();

    private String statusCd[];
    private boolean supervisorInfo;
    private boolean managerInfo;

    @Transient
    List<String> productTypeCardIds = new ArrayList<>();
    @Transient
    List<String> ageCodeIds = new ArrayList<>();
    @Transient
    List<String> letterTypeIds =  new ArrayList<>();
    @Transient
    List<String> locationIds = new ArrayList<>();

}
