package com.csinfotechbd.collection.KPI.Card.TargetExceptionRule;
/*
  Created by Md. Monirul Islam on 9/4/2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
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
