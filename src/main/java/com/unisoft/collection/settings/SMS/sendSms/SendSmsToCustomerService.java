package com.unisoft.collection.settings.SMS.sendSms;

import com.unisoft.collection.settings.SMS.smslog.SMSLogDto;
import com.unisoft.collection.settings.SMS.smslog.SmsLog;
import com.unisoft.user.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SendSmsToCustomerService {

    public SmsLog setValue(SMSLogDto smsLogDto, String tnxId){
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SmsLog smsLog = new SmsLog();

        smsLog.setMsisdn(smsLogDto.getMsisdn());
        smsLog.setPermitted(smsLogDto.getPermitted().get("0"));
        smsLog.setPushapi(smsLogDto.getPushapi().get("0"));
        smsLog.setParameter(smsLogDto.getParameter().get("0"));
        smsLog.setLogin(smsLogDto.getLogin().get("0"));
        smsLog.setStakeholderid(smsLogDto.getStakeholderid().get("0"));
        smsLog.setRefid(smsLogDto.getRefid().get("0"));
        smsLog.setTnxId(tnxId);

        smsLog.setCreatedDate(new Date());
        smsLog.setCreatedBy(principal.getUsername());

        return smsLog;
    }
}
