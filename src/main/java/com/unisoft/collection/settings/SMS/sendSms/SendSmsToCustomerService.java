package com.unisoft.collection.settings.SMS.sendSms;

import com.unisoft.collection.settings.SMS.SmsLog;
import com.unisoft.collection.settings.SMS.smslog.SMSLogDto;
import org.springframework.stereotype.Service;

@Service
public class SendSmsToCustomerService {

    public SmsLog setValue(SMSLogDto smsLogDto, String tnxId){
        SmsLog smsLog = new SmsLog();

        smsLog.setMsisdn(smsLogDto.getMsisdn());
        smsLog.setPermitted(smsLogDto.getPermitted().get("0"));
        smsLog.setPushapi(smsLogDto.getPushapi().get("0"));
        smsLog.setParameter(smsLogDto.getParameter().get("0"));
        smsLog.setLogin(smsLogDto.getLogin().get("0"));
        smsLog.setStakeholderid(smsLogDto.getStakeholderid().get("0"));
        smsLog.setRefid(smsLogDto.getRefid().get("0"));
        smsLog.setTnxId(tnxId);

        return smsLog;
    }
}
