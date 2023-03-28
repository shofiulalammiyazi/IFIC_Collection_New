package com.unisoft.collection.settings.SMS.sendSms;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.unisoft.collection.settings.SMS.SMSDto;
import com.unisoft.collection.settings.SMS.generate.GeneratedSMS;
import com.unisoft.collection.settings.SMS.generate.GeneratedSMSRepository;
import com.unisoft.collection.settings.SMS.smslog.SMSLogDto;
import com.unisoft.collection.settings.SMS.smslog.SMSLogRepository;
import com.unisoft.collection.settings.SMS.smslog.SmsLog;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.user.UserPrincipal;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SendSmsToCustomerService {

    @Autowired
    private GeneratedSMSRepository generatedSMSRepository;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private SMSLogRepository smsLogRepository;

    @Value("${ific.sms.api.username}")
    private String smsApiUsername;

    @Value("${ific.sms.api.password}")
    private String smsApiPassword;

    @Value("${ific.sms.api.sid}")
    private String smsApiSid;

    @Value("${ific.sms.api.url}")
    private String smsApiUrl;

    public SmsLog setValue(SMSLogDto smsLogDto, String tnxId, String accNo, String sms, String deal){
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == null)
            principal.setUsername("System");

        SmsLog smsLog = new SmsLog();

        smsLog.setMsisdn(smsLogDto.getMsisdn());
        smsLog.setPermitted(smsLogDto.getPermitted().get("0"));
        smsLog.setPushapi(smsLogDto.getPushapi().get("0"));
        smsLog.setParameter(smsLogDto.getParameter().get("0"));
        smsLog.setLogin(smsLogDto.getLogin().get("0"));
        smsLog.setStakeholderid(smsLogDto.getStakeholderid().get("0"));
        smsLog.setRefid(smsLogDto.getRefid().get("0"));
        smsLog.setTnxId(tnxId);
        smsLog.setAccNo(accNo);
        smsLog.setMsg(sms);
        smsLog.setDealReference(deal);

        smsLog.setCreatedDate(new Date());
        smsLog.setCreatedBy(principal.getUsername());

        return smsLog;
    }

    public String sendBulksms(List<GeneratedSMS> generatedSMSes) {

        for(int i = 0; i<generatedSMSes.size(); i++){
            try {
                Gson gson = new Gson();

                GeneratedSMS generatedSMS = generatedSMSes.get(i);

                String tnxId =  UUID.randomUUID().toString();
                SMSDto smsDto = new SMSDto(generatedSMS.getMobileNo(), generatedSMS.getMassege(), smsApiUsername, smsApiPassword, smsApiSid, tnxId);

                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(smsApiUrl);
                post.addHeader("Content-type", "application/json");
                post.addHeader("Accept", "application/json");
                StringEntity postingString = new StringEntity(gson.toJson(smsDto), ContentType.APPLICATION_JSON);

                post.setEntity(postingString);
                HttpResponse response = httpClient.execute(post);
                String jsonString = EntityUtils.toString(response.getEntity());

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
                SMSLogDto sms = mapper.readValue(jsonStr, SMSLogDto.class);

                AccountInformationEntity accountInformationEntity = accountInformationRepository.getOne(generatedSMSes.get(i).getId());
                accountInformationEntity.setIsSmsSent("Y");

                accountInformationRepository.save(accountInformationEntity);

                SmsLog smsLog = this.setValue(sms,tnxId,generatedSMS.getAccountNo(),generatedSMS.getMassege(),generatedSMS.getDealReference());

                smsLogRepository.save(smsLog);
            } catch (Exception ex) {
                ex.printStackTrace();
                return "500";
            }

        }
        return "OK";
    }
}
