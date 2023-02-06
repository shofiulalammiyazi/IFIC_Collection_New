package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.loanStatus.LoanStatusEntity;
import com.unisoft.collection.settings.loanType.LoanTypeEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SmsAndAutoDistributionRulesEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private String noOfDaysBeforeEmiDate;

    private String unpaidInstallmentNumber;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LoanStatusEntity> loanStatusEntity;

    @Transient
    List<String> LoanStatusIds = new ArrayList <>();


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LoanTypeEntity> loanTypeEntities;

    @Transient
    List<String> LoanTypeIds = new ArrayList <>();

}
