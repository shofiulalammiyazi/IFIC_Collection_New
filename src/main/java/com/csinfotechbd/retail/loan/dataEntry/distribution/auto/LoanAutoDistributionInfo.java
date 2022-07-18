package com.csinfotechbd.retail.loan.dataEntry.distribution.auto;

import com.csinfotechbd.common.CommonEntity;
import com.csinfotechbd.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerModel;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Data model for temporarily storing delinquent account related data pulled from client database
 * Need to clear all previous data before storing new data at the first day of every month.
 *
 * Created by Yasir Araphat on 29 May 2021
 */
@Data
@Entity
public class LoanAutoDistributionInfo extends CommonEntity implements PropertyBasedMakerCheckerModel {

    private String accountNo;
    private String dealerPin;
    private String dealerName;
    private Long agencyId;
    private String dpdBucket;
    private String location;
    private String productCode; // Scheme Code
    private double outstanding;
    private String remark;


}
