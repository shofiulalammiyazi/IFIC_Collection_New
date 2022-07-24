package com.unisoft.collection.samd.setup.writtenOffManagement;


import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.common.CommonEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class WrittenOffManagement extends CommonEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date wOffDate;
    private Double wOffInterestSuspenseAmount;
    private Double wOffProvisionAmount;
    private Double totalWOffAmount;      // wOffInterestSuspenseAmount + wOffProvisionAmount
    private Double previousMonthWOffRecoveryAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date previousMonthWOffRecoveryDate;
    private Double currentMonthWOffRecoveryAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date currentMonthWOffRecoveryDate;

    private Double previousMonthTotalWOffRecoveryAmount;

    private Double cumulativeWOffRecovery;  //  previousMonthTotalWOffRecoveryAmount + currentMonthWOffRecoveryAmount

    private Double lastMonthRemainingWOffOutstanding;


    private Double currentMonthRemainingWOffOutstanding;   //  lastMonthRemainingWOffOutstanding - currentMonthWOffRecoveryAmount

    private Double wOffWaiverAmountSettlement;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date wOffWaiverDateSettlement;


    private Double previousMonthTotalWOffWaivedAmount;

    private Double cumulativeWOffWaivedAmount;  //   previousMonthTotalWOffWaivedAmount + wOffWaiverAmountSettlement


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    List<AssetClassificationLoanEntity> clStatus = new ArrayList<>();

    private String wOffAccountStatus;

    private String customerId;

//    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//    @JoinColumn(name = "customerId")
//    private CustomerBasicInfoEntity customerBasicInfoEntity;


    @Transient
    private List<Long> clStatusIds;


    public void setClStatusIds(List<Long> clStatusIds) {
        this.clStatus = clStatusIds != null ? clStatusIds.stream().map(AssetClassificationLoanEntity::new).collect(Collectors.toList()): null;
    }
}
