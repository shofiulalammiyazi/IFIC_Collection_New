package com.unisoft.collection.settings.dunningLetterRulesLoan;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

//@Data
@Entity
public class DunningLetterRulesLoanCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer daysBefore;

    private Integer daysAfter;

    private Integer outstandingAmount;

    private ConditionTypeEnum conditionType;


    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(nullable=false)
    @ManyToOne(cascade = CascadeType.REFRESH)
    private DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo;


    @JsonManagedReference
    @OneToMany(mappedBy="dunningLetterRulesLoanCondition", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DunningLetterRulesDpdLetterType> dunningLetterRulesDpdLetterType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(Integer daysBefore) {
        this.daysBefore = daysBefore;
    }

    public Integer getDaysAfter() {
        return daysAfter;
    }

    public void setDaysAfter(Integer daysAfter) {
        this.daysAfter = daysAfter;
    }

    public Integer getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Integer outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public ConditionTypeEnum getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionTypeEnum conditionType) {
        this.conditionType = conditionType;
    }

    public DunningLetterRulesLoanInfo getDunningLetterRulesLoanInfo() {
        return dunningLetterRulesLoanInfo;
    }

    public void setDunningLetterRulesLoanInfo(DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo) {
        this.dunningLetterRulesLoanInfo = dunningLetterRulesLoanInfo;
    }

    public List<DunningLetterRulesDpdLetterType> getDunningLetterRulesDpdLetterType() {
        return dunningLetterRulesDpdLetterType;
    }

    public void setDunningLetterRulesDpdLetterType(List<DunningLetterRulesDpdLetterType> dunningLetterRulesDpdLetterType) {
        this.dunningLetterRulesDpdLetterType = dunningLetterRulesDpdLetterType;
    }
}
