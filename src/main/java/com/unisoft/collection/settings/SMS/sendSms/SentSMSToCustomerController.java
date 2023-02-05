package com.unisoft.collection.settings.SMS.sendSms;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.unisoft.collection.distribution.loan.LoanController;
import com.unisoft.collection.settings.SMS.SMSDto;
import com.unisoft.collection.settings.SMS.generate.GeneratedSMS;
import com.unisoft.collection.settings.SMS.generate.GeneratedSMSRepository;
import com.unisoft.collection.settings.SMS.smsType.SMSEntity;
import com.unisoft.collection.settings.SMS.smsType.SMSService;
import com.unisoft.collection.settings.SMS.smslog.SMSLogDto;
import com.unisoft.collection.settings.SMS.smslog.SMSLogRepository;
import com.unisoft.collection.settings.SMS.smslog.SmsLog;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/collection/smsToCustomer")
public class SentSMSToCustomerController {

    @Autowired
    private LoanController loanController;

    @Autowired
    private SMSService smsService;

    @Autowired
    private GeneratedSMSRepository generatedSMSRepository;

    @Autowired
    private SendSmsToCustomerService sendSmsToCustomerService;

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

    @GetMapping("/get-sms")
    public static String sendSMS(String msisdn, String sms_string) {
        try {
            sms_string = "dfvdsd";
            msisdn = "01618811202";
            String url = "http://sms.sslwireless.com/pushapi/dynamic/server.php";
            String args = "?msisdn=" + msisdn + "&sms=" + URLEncoder.encode(sms_string) + "&user=IFICRECOVERY&pass=88x@6R57&sid=88x@6R57&csmsid=123456789";
            URL obj = new URL(url + args);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User‐Agent", "USER_AGENT");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception ex) {
            return "<SMS_STATUS>ERROR</SMS_STATUS>";
        }
    }

    @PostMapping("/send-sms")
    @ResponseBody
    public String sendsmss(@RequestParam("id") Long id) {
        try {

            Gson gson = new Gson();

            GeneratedSMS generatedSMS = generatedSMSRepository.getOne(id);

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

            SmsLog smsLog = sendSmsToCustomerService.setValue(sms,tnxId,generatedSMS.getAccountNo(),generatedSMS.getMassege());

            smsLogRepository.save(smsLog);

            return "OK";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "500";
        }
    }


    @GetMapping("/sms-actlist")
    public String accountList(Model model) {

        model.addAttribute("loanviewlist", loanController.getLoanViewModelsForSms());

        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("smsEntityList", smsEntityList);

        return "collection/settings/sms/sendSms/smsSendToCustomer";

    }

    @GetMapping("/sms-to-customer")
    public String showSmsToCustomerList() {
        return "collection/settings/sms/sendSms/smsSendToCustomer";
    }

    public String convertBanglatoUnicode(String banglaText) {
        StringBuilder sb = new StringBuilder();
        for (char c : banglaText.toCharArray()) {
            sb.append("{1:x4}", c, (int) c);
        }
        String unicode = sb.toString().toUpperCase();
        return unicode;
    }
}
