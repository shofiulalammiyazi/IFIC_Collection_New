package com.unisoft.collection.settings.SMS.generator;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.SMS.SMSEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TemplateGenerate extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String smsType;

    private String massege;


    public TemplateGenerate() {
    }

    public TemplateGenerate(String smsType, SMSEntity smsEntity, String massege) {
        this.smsType = smsType;
        this.massege = massege;
//        this.smsEntity = smsEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    //    public SMSEntity getSmsEntity() {
//        return smsEntity;
//    }
//
//    public void setSmsEntity(SMSEntity smsEntity) {
//        this.smsEntity = smsEntity;
//    }
}
