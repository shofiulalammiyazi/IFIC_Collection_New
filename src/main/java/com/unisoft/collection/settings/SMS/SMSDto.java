package com.unisoft.collection.settings.SMS;

import lombok.Data;

@Data
public class SMSDto {

    private String msisdn;

    private String msg;

    private String user;

    private String pass;

    private String sid;

    private String trxid;

    public SMSDto(String msisdn, String msg, String user, String pass, String sid, String trxid) {
        this.msisdn = msisdn;
        this.msg = msg;
        this.user = user;
        this.pass = pass;
        this.sid = sid;
        this.trxid = trxid;
    }

    public SMSDto() {
    }
}