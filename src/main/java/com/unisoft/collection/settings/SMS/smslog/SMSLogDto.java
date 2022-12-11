package com.unisoft.collection.settings.SMS.smslog;

import lombok.Data;

import javax.persistence.Transient;
import java.util.Map;

@Data
public class SMSLogDto {

    private String msisdn;

    private Map<String,String> permitted;

    private Map<String,String> pushapi;

    private Map<String,String> parameter;

    private Map<String,String> login;

    private Map<String,String> stakeholderid;

    private Map<String,String> refid;
}
