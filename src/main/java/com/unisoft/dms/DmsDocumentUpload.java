package com.unisoft.dms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisoft.base.BaseInfo;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;

import javax.persistence.*;

@Entity
public class DmsDocumentUpload extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String comments;

    private String name;

    private String dmsId;

    private String type;

    private String glat;

    private String glong;

    @Transient
    private String pin;

    @Transient
    private String creatorName;

    @OneToOne
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfoEntity;

    public DmsDocumentUpload() {
    }

    public DmsDocumentUpload(String comments, String name, String dmsId, String type, CustomerBasicInfoEntity customerBasicInfoEntity) {
        this.comments = comments;
        this.name = name;
        this.dmsId = dmsId;
        this.type = type;
        this.customerBasicInfoEntity = customerBasicInfoEntity;
    }

    public DmsDocumentUpload(String comments, String name, String dmsId, String type, String glat, String glong, String pin, String creatorName, CustomerBasicInfoEntity customerBasicInfoEntity) {
        this.comments = comments;
        this.name = name;
        this.dmsId = dmsId;
        this.type = type;
        this.glat = glat;
        this.glong = glong;
        this.pin = pin;
        this.creatorName = creatorName;
        this.customerBasicInfoEntity = customerBasicInfoEntity;
    }

    public String getGlat() {
        return glat;
    }

    public void setGlat(String glat) {
        this.glat = glat;
    }

    public String getGlong() {
        return glong;
    }

    public void setGlong(String glong) {
        this.glong = glong;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDmsId() {
        return dmsId;
    }

    public void setDmsId(String dmsId) {
        this.dmsId = dmsId;
    }

    public CustomerBasicInfoEntity getCustomerBasicInfoEntity() {
        return customerBasicInfoEntity;
    }

    public void setCustomerBasicInfoEntity(CustomerBasicInfoEntity customerBasicInfoEntity) {
        this.customerBasicInfoEntity = customerBasicInfoEntity;
    }

    @Override
    public String toString() {
        return "DmsDocumentUpload{" +
                "id=" + id +
                ", comments='" + comments + '\'' +
                ", dmsId='" + dmsId + '\'' +
                ", customerBasicInfoEntity=" + customerBasicInfoEntity +
                '}';
    }
}
