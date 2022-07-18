package com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo;
/*
Created by Monirul Islam at 7/21/2019
*/

import com.csinfotechbd.common.CommonEntity;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.utillity.NumberUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CardAccountDistributionInfo extends CommonEntity {

    private String supervisorPin;

    private String supervisorName;

    private String dealerPin;

    private String productGroup;

    private String ageCode;
    
    private String currentAgeCode; // Latest age code from core banking
    
    private String schemeCode;

    private String samAccount = "0";

    private String writeOffAccount = "0";

    private String dealerName;

    private String vvip;

    private String plasticCd;

    private String billingCycle;

    private String customerName;

    private String outstandingAmount;

    private String secureCard;

    private String stateCode;

    @Temporal(TemporalType.DATE)
    private Date statusDate;

    private String clientId;

    private String monitoringStatus = "SINGLE";//default=SINGLE

    private String latest = "1"; //latest=1

    private String mobileNo;

    private String remarks;

    private Double bdtUsdConversionRate;

    private Double totalOutstanding;

    private Double minDuePayment;
    private Double emiAmount;
    
    private Double lastPaidAmount = 0D;
    
    private String outStanding;

    private Double openingOverDue;
    private Double currentOverDue;
    
    private String dpdBucket; // Month opening bucket
    
    private String currentDpdBucket; // Latest Bucket from core banking
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private Date lastPaymentDate;

    @ManyToOne
    private CardAccountBasicInfo cardAccountBasicInfo;

    @Transient
    private double parRelAmnt = 0;

    @Transient
    private double nplRelAmnt = 0;

    @Transient
    private double saveAmount = 0;

    @Transient
    private double remSaveAmount = 0;

    @Transient
    private double backAmount = 0;

    @Transient
    private double remBackAmount = 0;

    @Transient
    private double remRegularkAmount = 0;

    @Transient
    private double remRawCollAmount = 0;

    @Transient
    private double cashCollection = 0;

    @Transient
    private ProductTypeEntity productType;

    @Transient
    private Date currentMonthPayDueDate;

    @Transient
    private Date dueDateWithGracePeriod;

    @Transient
    private double totalPayment;

    public CardAccountDistributionInfo() {
    }

    public CardAccountDistributionInfo(String dealerName, String dealerPin, String clientId, Double bdtUsdConversionRate) {
        this.dealerName = dealerName;
        this.dealerPin = dealerPin;
        this.clientId = clientId;
        this.bdtUsdConversionRate = bdtUsdConversionRate;
        this.statusDate = new Date();
    }

    public String getOutstandingAmount() {
        return NumberUtils.isNumberString(outstandingAmount) ? outstandingAmount : "0";
    }

    public double getNumericOutstandingAmount() {
        return Double.valueOf(getOutstandingAmount());
    }

    public double getNumericOutStanding() {
        return Double.valueOf(getOutstandingAmount());
    }

}
