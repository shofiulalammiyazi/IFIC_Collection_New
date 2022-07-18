package com.csinfotechbd.reports.retail.loan.productWiseClReport;

import lombok.Data;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Data
public class ProductWiseCLReportDto {

    private String productGroup;
    private double totalExposureOutstanding;
    private double totalExposurePercentage;
    private long totalExposureAccount;
    private double totalClOutstanding;
    private long totalClAccount;
    private double totalClPercentage;
    private double totalParOutstanding;
    private long totalParAccount;
    private double totalParPercentage;
    private double totalBlOutstanding;
    private long totalBlAccount;
    private double totalDfOutstanding;
    private long totalDfAccount;
    private double totalSsOutstanding;
    private long totalSsAccount;
    private double totalSmaOutstanding;
    private long totalSmaAccount;
    private double totalUcOutstanding;
    private long totalUcAccount;

    public ProductWiseCLReportDto() {
    }

    public ProductWiseCLReportDto(Object[] data) {
        productGroup = Objects.toString(data[0], "-");
        totalExposureOutstanding = ((Number) data[1]).doubleValue();
        totalExposurePercentage = ((Number) data[2]).doubleValue();
        totalExposureAccount = ((Number) data[3]).longValue();
        totalClOutstanding = ((Number) data[4]).doubleValue();
        totalClAccount = ((Number) data[5]).longValue();
        totalClPercentage = ((Number) data[6]).doubleValue();
        totalParOutstanding = ((Number) data[7]).doubleValue();
        totalParAccount = ((Number) data[8]).longValue();
        totalParPercentage = ((Number) data[9]).doubleValue();
        totalBlOutstanding = ((Number) data[10]).doubleValue();
        totalBlAccount = ((Number) data[11]).longValue();
        totalDfOutstanding = ((Number) data[12]).doubleValue();
        totalDfAccount = ((Number) data[13]).longValue();
        totalSsOutstanding = ((Number) data[14]).doubleValue();
        totalSsAccount = ((Number) data[15]).longValue();
        totalSmaOutstanding = ((Number) data[16]).doubleValue();
        totalSmaAccount = ((Number) data[17]).longValue();
        totalUcOutstanding = ((Number) data[18]).doubleValue();
        totalUcAccount = ((Number) data[19]).longValue();
    }


}
