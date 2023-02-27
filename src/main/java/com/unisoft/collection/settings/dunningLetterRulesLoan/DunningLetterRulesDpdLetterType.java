package com.unisoft.collection.settings.dunningLetterRulesLoan;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.manualLetterSetup.ManualLetterSetupInfo;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Entity
//@Data
public class DunningLetterRulesDpdLetterType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade = CascadeType.REFRESH)
    private DPDBucketEntity dpdBucket;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade = CascadeType.REFRESH)
    private ManualLetterSetupInfo letterType;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="dunning_letter_rules_loan_condition_id")
    DunningLetterRulesLoanCondition dunningLetterRulesLoanCondition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DPDBucketEntity getDpdBucket() {
        return dpdBucket;
    }

    public void setDpdBucket(DPDBucketEntity dpdBucket) {
        this.dpdBucket = dpdBucket;
    }

    public ManualLetterSetupInfo getLetterType() {
        return letterType;
    }

    public void setLetterType(ManualLetterSetupInfo letterType) {
        this.letterType = letterType;
    }

    public DunningLetterRulesLoanCondition getDunningLetterRulesLoanCondition() {
        return dunningLetterRulesLoanCondition;
    }

    public void setDunningLetterRulesLoanCondition(DunningLetterRulesLoanCondition dunningLetterRulesLoanCondition) {
        this.dunningLetterRulesLoanCondition = dunningLetterRulesLoanCondition;
    }
}
