package com.unisoft.collection.settings.SMS.template;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.SMS.smsType.SMSEntity;

import javax.persistence.*;

@Entity
public class TemplateGenerate extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private SMSEntity smsType;

    @Lob
    private String massege;


    public TemplateGenerate() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SMSEntity getSmsType() {
        return smsType;
    }

    public void setSmsType(SMSEntity smsType) {
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