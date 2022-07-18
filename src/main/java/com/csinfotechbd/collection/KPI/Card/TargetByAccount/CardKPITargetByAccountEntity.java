package com.csinfotechbd.collection.KPI.Card.TargetByAccount;
/*
  Created by Md. Monirul Islam on 9/2/2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.productGroup.ProductGroupEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCd;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KPI_TARGET_BY_ACCOUNT_CARD")
@Data
public class CardKPITargetByAccountEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<LocationEntity> location= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<ProductTypeEntity> productType = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<AgeCodeEntity> ageCode = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<EmployeeInfoEntity> dealerName= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<StatusCd> statusCd= new ArrayList<>();
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<AssetClassificationLoanEntity> clStatus= new ArrayList<>();

    private Double minDueTarget;
    private Double saveTarget;
    private Double backTarget;
    private Double flowTarget;
    private Double regularTarget;
    private Double rawCollectionTarget;
    private boolean rawColGtMinDue;
    private Double PARRelTarget;
    private Double NPLRelTarget;
    private boolean writeOff;
    private String legalStatus;
    private String proposal;

    @Transient
    List<String> productTypeIds = new ArrayList<>();
    @Transient
    List<String> locationIds = new ArrayList<>();
    @Transient
    List<String> ageCodeIds = new ArrayList<>();
    @Transient
    List<String> dealerNameIds = new ArrayList<>();
    @Transient
    List<String> statusCdIds = new ArrayList<>();

}
