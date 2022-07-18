package com.csinfotechbd.collection.automaticDistribution.distributionExceptionCard;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.automaticDistribution.distributionExceptionCardModel.ProductGroupAgeCode;
import com.csinfotechbd.collection.samd.setup.riskCategories.RiskCategory;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
Created by Monirul Islam at 8/22/2019
*/
@Data
@Entity
public class DistributionException extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String samIgnore;

    private String writeOffIgnore;

    private String VVIP;

    private String secureCard;

    private String[] billingCycle;

    private String[] plasticCd;
    private String[] contractId;


    private String isMultiProductDistribute;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<CustomerBasicInfoEntity> customerList = new ArrayList<>();

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<EmployeeInfoEntity> employeeInfos = new ArrayList<>();

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<ProductTypeEntity> productsList = new ArrayList<>();

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Card_Exeception_id", nullable = false)
    private List<ProductGroupAgeCode> productGroupAgeCodes = new ArrayList<>();

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<RiskCategory> riskCategoryList = new ArrayList<>();

    @Transient
    private List<String> customerIds = new ArrayList<>();

    @Transient
    private List<String> dealerIds = new ArrayList<>();

    @Transient
    private List<String> productIds = new ArrayList<>();

    @Transient
    private List<String> riskCategoryIds = new ArrayList<>();
}
