package com.unisoft.collection.settings.SMS.smslog;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class SmsLog extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String msisdn;

    private String permitted;

    private String pushapi;

    private String parameter;

    private String login;

    private String stakeholderid;

    private String refid;

    private String tnxId;

    private String accNo;

    private String msg;

    private String dealReference;

    private String smsType;

}
