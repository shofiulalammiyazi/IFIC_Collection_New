package com.unisoft.collection.settings.SMS.sendSms;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class SendSMS {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sendsms;

//    @Transient
    private String accountNo;



}
