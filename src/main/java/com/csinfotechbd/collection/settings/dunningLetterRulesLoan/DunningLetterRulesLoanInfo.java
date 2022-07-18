package com.csinfotechbd.collection.settings.dunningLetterRulesLoan;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Data
@Entity
public class DunningLetterRulesLoanInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<ProductTypeEntity> productType = new ArrayList<>();


    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dunningLetterRulesLoanInfo",cascade = CascadeType.ALL)
    private List<DunningLetterRulesLoanCondition> conditions = new ArrayList<DunningLetterRulesLoanCondition>();

    @Transient
    ConditionTypeEnum conditionType;

    private Boolean guarantor;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.REFRESH)
    private List<DPDBucketEntity> guarantorDpdBucket = new ArrayList<DPDBucketEntity>();


    private Boolean autoLoan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductTypeEntity> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductTypeEntity> productType) {
        this.productType = productType;
    }

    public List<DunningLetterRulesLoanCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<DunningLetterRulesLoanCondition> conditions) {
        this.conditions = conditions;
    }

    public ConditionTypeEnum getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionTypeEnum conditionType) {
        this.conditionType = conditionType;
    }

    public Boolean getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(Boolean guarantor) {
        this.guarantor = guarantor;
    }

    public List<DPDBucketEntity> getGuarantorDpdBucket() {
        return guarantorDpdBucket;
    }

    public void setGuarantorDpdBucket(List<DPDBucketEntity> guarantorDpdBucket) {
        this.guarantorDpdBucket = guarantorDpdBucket;
    }

    public Boolean getAutoLoan() {
        return autoLoan;
    }

    public void setAutoLoan(Boolean autoLoan) {
        this.autoLoan = autoLoan;
    }
}
