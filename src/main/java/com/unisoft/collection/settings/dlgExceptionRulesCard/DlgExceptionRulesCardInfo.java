package com.unisoft.collection.settings.dlgExceptionRulesCard;


import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DlgExceptionRulesCardInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String outstanding;
    private String minDue;
    private boolean sam;
    private boolean writeOff;
    private boolean vvip;
    private boolean securedCard;
    private String statusCd[];
    private String plasticCd[];



    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany (cascade = CascadeType.ALL)
    private List<AgeCodeEntity> ageCode = new ArrayList<>();


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany (cascade = CascadeType.ALL)
    private List<CustomerBasicInfoEntity> customerId = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductTypeEntity> productType = new ArrayList<>();

    @Transient
    private List<String> ageCodeIds = new ArrayList<>();
    @Transient
    private List<String> customerIdIds = new ArrayList<>();
    @Transient
    private List<String> productTypeIds = new ArrayList<>();



}
