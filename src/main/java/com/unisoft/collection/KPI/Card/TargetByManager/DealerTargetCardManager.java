package com.unisoft.collection.KPI.Card.TargetByManager;
/*
Created by   Islam at 9/23/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "del_tar_card_man")
public class DealerTargetCardManager extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<AgeCodeEntity> ageCodeEntityList = new ArrayList<>();
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<LocationEntity> locationEntityList = new ArrayList<>();
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<EmployeeInfoEntity> employeeInfoEntityList= new ArrayList<>();
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<ProductTypeEntity> productType = new ArrayList<>();

    private double regularAccountTarget;
    private double backAccountTarget;
    private double saveAccountTarget;
    private double flowAccountTarget;
    private double cashCollectionTarget;
    private double parReleaseAmountTarget;
    private double nplReleaseAmountTarget;

    @Transient
    private List<String> ageCodeIds = new ArrayList<>();
    @Transient
    private List<String> locationIds = new ArrayList<>();
    @Transient
    private List<String> employeeIds = new ArrayList<>();
    @Transient
    private List<String> productTypeIds=new ArrayList<>();


}
