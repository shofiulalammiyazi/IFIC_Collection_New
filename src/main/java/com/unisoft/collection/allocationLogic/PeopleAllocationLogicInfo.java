package com.unisoft.collection.allocationLogic;
/*
Created by   Islam at 7/18/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardEntity;
import com.unisoft.collection.settings.sector.SectorEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "people_allocation_logic")
public class PeopleAllocationLogicInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String unit;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "dealer_id")
    private EmployeeInfoEntity dealer;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "manager_id")
    private EmployeeInfoEntity manager;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "team_lead_id")
    private EmployeeInfoEntity teamlead;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "supervisor_id")
    private EmployeeInfoEntity supervisor;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "agency_id")
    private AgencyEntity agency;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<DPDBucketEntity> dpdBucketEntity = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<AgeCodeEntity> ageCodeEntity = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<ProductTypeEntity> productTypeEntity = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<ProductTypeEntity> productTypeEntityLoan = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<ProductTypeCardEntity> productTypeEntityCard = new ArrayList<>();

    private String distributionType;

    private String secureCard;

    private Integer osRange; /*os=outstanding*/

    private Integer odRange; /*od=overdue*/

    private String accountRange;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<SectorEntity> sectorEntity = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "people_allocation_logic_branches", joinColumns = @JoinColumn(name = "people_allocation_logic__id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id"))
    private Set<Branch> branches = new HashSet<>();

    @Transient
    private List<String> selectedDpdBucket = new ArrayList<>();

    @Transient
    private List<String> selectedAgeCode = new ArrayList<>();

    @Transient
    private List<String> selectedProductType = new ArrayList<>();

    @Transient
    private List<String> selectedProductGroup = new ArrayList<>();

    @Transient
    private List<String> selectedSector = new ArrayList<>();

    @Transient
    private List<String> selectedBranch = new ArrayList<>();
}
