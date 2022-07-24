package com.unisoft.collection.settings.assetClassificationLoan;


import lombok.Data;

import javax.validation.constraints.NotNull;




@Data
public class AssetClassificationLoanEntityDto{


    private Long id;
    private String name;
    private String code;
    private String type;

    private double minSanctionAmount;
    private double minDisburseAmount;
    private double minOutstandingAmount;


    public AssetClassificationLoanEntityDto(Long id) {
        this.id = id;
    }

    public AssetClassificationLoanEntityDto() {

    }

    public AssetClassificationLoanEntityDto(@NotNull AssetClassificationLoanEntity assetClassificationLoanEntity) {
        this.id = assetClassificationLoanEntity.getId();
        //this.name = assetClassificationLoanEntity.getName();
        //this.code = assetClassificationLoanEntity.getCode();
        this.type = assetClassificationLoanEntity.getType().toString();
        this.minSanctionAmount = assetClassificationLoanEntity.getMinSanctionAmount();
        this.minDisburseAmount = assetClassificationLoanEntity.getMinDisburseAmount();
        this.minOutstandingAmount = assetClassificationLoanEntity.getMinOutstandingAmount();
    }

    public AssetClassificationLoanEntityDto(Long id, String name, String code, String type, double minSanctionAmount, double minDisburseAmount, double minOutstandingAmount) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.type = type;
        this.minSanctionAmount = minSanctionAmount;
        this.minDisburseAmount = minDisburseAmount;
        this.minOutstandingAmount = minOutstandingAmount;
    }
}
