package com.unisoft.collection.settings.dealerPerformance;
/*
Created by   Islam at 8/28/2019
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DealerPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int overAllKpi;
    @OneToOne(cascade = CascadeType.REFRESH)
    private EmployeeInfoEntity employeeInfoEntity;
    @Transient
    private List<ProductTypeEntity> productGroupEntityList = new ArrayList<>();
    @Transient
    private List<ProductTypeEntity> productTypeEntityList = new ArrayList<>();
    @Transient
    private List<DPDBucketEntity> dpdBucketEntityList = new ArrayList<>();
    @Transient
    private List<AgeCodeEntity> ageCodeEntityList = new ArrayList<>();

}
