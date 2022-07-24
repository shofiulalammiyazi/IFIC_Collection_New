package com.unisoft.collection.KPI.Card.TargetExceptionRule;
/*
  Created by Md.   Islam on 9/4/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.statuscdmanagement.StatusCd;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name ="KPI_TARGET_EXCEPTION_CARD")
public class CardKPITargetExceptionRuleEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductTypeEntity> productType = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<StatusCd> statusCd= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<EmployeeInfoEntity> dealerName= new ArrayList<>();

    @Transient
    List<String> productTypeIds = new ArrayList<>();
    @Transient
    List<String> statusCdIds = new ArrayList<>();
    @Transient
    List<String> dealerNameIds = new ArrayList<>();



}
