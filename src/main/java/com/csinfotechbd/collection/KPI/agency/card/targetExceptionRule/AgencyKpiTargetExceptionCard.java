package com.csinfotechbd.collection.KPI.agency.card.targetExceptionRule;
/*
Created by Monirul Islam at 9/23/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCd;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "ag_kpi_tar_exc_card")
public class AgencyKpiTargetExceptionCard extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<ProductTypeEntity> productTypeEntityList = new ArrayList<>();
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<EmployeeInfoEntity> employeeInfoEntityList= new ArrayList<>();
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<StatusCd> statusCdList = new ArrayList<>();

    @Transient
    private List<String> productIds = new ArrayList<>();
    @Transient
    private List<String> employeeIds = new ArrayList<>();
    @Transient
    private List<String> statusCdIds = new ArrayList<>();

}
