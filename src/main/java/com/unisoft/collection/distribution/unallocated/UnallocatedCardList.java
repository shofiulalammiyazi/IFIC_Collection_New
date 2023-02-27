package com.unisoft.collection.distribution.unallocated;

import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UnallocatedCardList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private CardAccountBasicInfo cardAccountBasicInfo;

    private String supervisorPin;

    private String supervisorName;

    private String dealerPin;

    private String productGroup;

    private String ageCode;

    private String samAccount;

    private String writeOffAccount;

    private String dealerName;

    private String vvip;

    private String plasticCd;

    private String billingCycle;

    private String customerName;

    private String outstandingAmount;

    private String secureCard;

    @Temporal(TemporalType.DATE)
    private Date statusDate;

    private String monitoringStatus;//default=SINGLE

    private String latest;//latest=0

    private String mobileNo;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdDate;

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

    @Column(nullable = true)
    private double minDuePayment;

    public UnallocatedCardList() {
    }

    public UnallocatedCardList(CardAccountBasicInfo cardAccountBasicInfo, String supervisorPin, String supervisorName, String dealerPin, String productGroup, String ageCode, String samAccount, String writeOffAccount, String dealerName, String vvip, String plasticCd, String billingCycle, String customerName, String outstandingAmount, String secureCard, Date statusDate, String monitoringStatus, String latest, String mobileNo, double parRelAmnt, double nplRelAmnt, double saveAmount, double remSaveAmount, double backAmount, double remBackAmount, double remRegularkAmount, double remRawCollAmount, double cashCollection, ProductTypeEntity productType, Date currentMonthPayDueDate, Date dueDateWithGracePeriod, double totalPayment, double minDuePayment) {
        this.cardAccountBasicInfo = cardAccountBasicInfo;
        this.supervisorPin = supervisorPin;
        this.supervisorName = supervisorName;
        this.dealerPin = dealerPin;
        this.productGroup = productGroup;
        this.ageCode = ageCode;
        this.samAccount = samAccount;
        this.writeOffAccount = writeOffAccount;
        this.dealerName = dealerName;
        this.vvip = vvip;
        this.plasticCd = plasticCd;
        this.billingCycle = billingCycle;
        this.customerName = customerName;
        this.outstandingAmount = outstandingAmount;
        this.secureCard = secureCard;
        this.statusDate = statusDate;
        this.monitoringStatus = monitoringStatus;
        this.latest = latest;
        this.mobileNo = mobileNo;
        this.parRelAmnt = parRelAmnt;
        this.nplRelAmnt = nplRelAmnt;
        this.saveAmount = saveAmount;
        this.remSaveAmount = remSaveAmount;
        this.backAmount = backAmount;
        this.remBackAmount = remBackAmount;
        this.remRegularkAmount = remRegularkAmount;
        this.remRawCollAmount = remRawCollAmount;
        this.cashCollection = cashCollection;
        this.productType = productType;
        this.currentMonthPayDueDate = currentMonthPayDueDate;
        this.dueDateWithGracePeriod = dueDateWithGracePeriod;
        this.totalPayment = totalPayment;
        this.minDuePayment = minDuePayment;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardAccountBasicInfo getCardAccountBasicInfo() {
        return cardAccountBasicInfo;
    }

    public void setCardAccountBasicInfo(CardAccountBasicInfo cardAccountBasicInfo) {
        this.cardAccountBasicInfo = cardAccountBasicInfo;
    }

    public String getSupervisorPin() {
        return supervisorPin;
    }

    public void setSupervisorPin(String supervisorPin) {
        this.supervisorPin = supervisorPin;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getDealerPin() {
        return dealerPin;
    }

    public void setDealerPin(String dealerPin) {
        this.dealerPin = dealerPin;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(String ageCode) {
        this.ageCode = ageCode;
    }

    public String getSamAccount() {
        return samAccount;
    }

    public void setSamAccount(String samAccount) {
        this.samAccount = samAccount;
    }

    public String getWriteOffAccount() {
        return writeOffAccount;
    }

    public void setWriteOffAccount(String writeOffAccount) {
        this.writeOffAccount = writeOffAccount;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getVvip() {
        return vvip;
    }

    public void setVvip(String vvip) {
        this.vvip = vvip;
    }

    public String getPlasticCd() {
        return plasticCd;
    }

    public void setPlasticCd(String plasticCd) {
        this.plasticCd = plasticCd;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(String outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getSecureCard() {
        return secureCard;
    }

    public void setSecureCard(String secureCard) {
        this.secureCard = secureCard;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getMonitoringStatus() {
        return monitoringStatus;
    }

    public void setMonitoringStatus(String monitoringStatus) {
        this.monitoringStatus = monitoringStatus;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public double getParRelAmnt() {
        return parRelAmnt;
    }

    public void setParRelAmnt(double parRelAmnt) {
        this.parRelAmnt = parRelAmnt;
    }

    public double getNplRelAmnt() {
        return nplRelAmnt;
    }

    public void setNplRelAmnt(double nplRelAmnt) {
        this.nplRelAmnt = nplRelAmnt;
    }

    public double getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(double saveAmount) {
        this.saveAmount = saveAmount;
    }

    public double getRemSaveAmount() {
        return remSaveAmount;
    }

    public void setRemSaveAmount(double remSaveAmount) {
        this.remSaveAmount = remSaveAmount;
    }

    public double getBackAmount() {
        return backAmount;
    }

    public void setBackAmount(double backAmount) {
        this.backAmount = backAmount;
    }

    public double getRemBackAmount() {
        return remBackAmount;
    }

    public void setRemBackAmount(double remBackAmount) {
        this.remBackAmount = remBackAmount;
    }

    public double getRemRegularkAmount() {
        return remRegularkAmount;
    }

    public void setRemRegularkAmount(double remRegularkAmount) {
        this.remRegularkAmount = remRegularkAmount;
    }

    public double getRemRawCollAmount() {
        return remRawCollAmount;
    }

    public void setRemRawCollAmount(double remRawCollAmount) {
        this.remRawCollAmount = remRawCollAmount;
    }

    public double getCashCollection() {
        return cashCollection;
    }

    public void setCashCollection(double cashCollection) {
        this.cashCollection = cashCollection;
    }

    public ProductTypeEntity getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeEntity productType) {
        this.productType = productType;
    }

    public Date getCurrentMonthPayDueDate() {
        return currentMonthPayDueDate;
    }

    public void setCurrentMonthPayDueDate(Date currentMonthPayDueDate) {
        this.currentMonthPayDueDate = currentMonthPayDueDate;
    }

    public Date getDueDateWithGracePeriod() {
        return dueDateWithGracePeriod;
    }

    public void setDueDateWithGracePeriod(Date dueDateWithGracePeriod) {
        this.dueDateWithGracePeriod = dueDateWithGracePeriod;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getMinDuePayment() {
        return minDuePayment;
    }

    public void setMinDuePayment(double minDuePayment) {
        this.minDuePayment = minDuePayment;
    }
}
