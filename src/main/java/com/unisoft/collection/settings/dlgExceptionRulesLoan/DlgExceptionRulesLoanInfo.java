package com.unisoft.collection.settings.dlgExceptionRulesLoan;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.vipStatus.VipStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DlgExceptionRulesLoanInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean sam;
    private boolean writeOff;
    private String outstanding;
    private String minOverDue;
    private String customerCifNo = new String("");

//    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.REFRESH)
    private List<ProductTypeEntity> productType = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH)
    private List<Branch> branches = new ArrayList<>();


    @OneToMany(cascade = CascadeType.REFRESH)
    private List<VipStatus> vipStatuses = new ArrayList<>();

    @Transient
    private List<Long> productTypeIds = new ArrayList<>();

    @Transient
    private List<Integer> branchIds = new ArrayList<>();


    @Transient
    private List<Long> vipStatusIds = new ArrayList<>();
}
