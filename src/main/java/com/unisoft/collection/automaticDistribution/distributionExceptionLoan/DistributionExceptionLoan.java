package com.unisoft.collection.automaticDistribution.distributionExceptionLoan;


import com.google.gson.annotations.Expose;
import com.unisoft.base.BaseInfo;
import com.unisoft.collection.automaticDistribution.distributionExceptionLoanModel.ProductGroupDpdBucket;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DistributionExceptionLoan extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Expose
    private String samIgnore;

    @Expose
    private String writeOffIgnore;

    @Expose
    private String interim;

    @Expose
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<EmployeeInfoEntity> employeeInfos = new ArrayList<>();

    @Expose
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<ProductTypeEntity> productsList = new ArrayList<>();

    @Expose
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Loan_Exeception_id", nullable = false)
    private List<ProductGroupDpdBucket> productGroupDpdBuckets = new ArrayList<>();

    @Transient
    private List<String> dealerIds = new ArrayList<>();

    @Transient
    private List<String> productIds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSamIgnore() {
        return samIgnore;
    }

    public void setSamIgnore(String samIgnore) {
        this.samIgnore = samIgnore;
    }

    public String getWriteOffIgnore() {
        return writeOffIgnore;
    }

    public void setWriteOffIgnore(String writeOffIgnore) {
        this.writeOffIgnore = writeOffIgnore;
    }

    public String getInterim() {
        return interim;
    }

    public void setInterim(String interim) {
        this.interim = interim;
    }

    public List<EmployeeInfoEntity> getEmployeeInfos() {
        return employeeInfos;
    }

    public void setEmployeeInfos(List<EmployeeInfoEntity> employeeInfos) {
        this.employeeInfos = employeeInfos;
    }

    public List<ProductTypeEntity> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<ProductTypeEntity> productsList) {
        this.productsList = productsList;
    }

    public List<ProductGroupDpdBucket> getProductGroupDpdBuckets() {
        return productGroupDpdBuckets;
    }

    public void setProductGroupDpdBuckets(List<ProductGroupDpdBucket> productGroupDpdBuckets) {
        this.productGroupDpdBuckets = productGroupDpdBuckets;
    }

    public List<String> getDealerIds() {
        return dealerIds;
    }

    public void setDealerIds(List<String> dealerIds) {
        this.dealerIds = dealerIds;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
