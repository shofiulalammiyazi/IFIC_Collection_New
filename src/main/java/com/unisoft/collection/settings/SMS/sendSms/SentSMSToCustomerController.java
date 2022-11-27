package com.unisoft.collection.settings.SMS.sendSms;

import com.unisoft.collection.distribution.loan.LoanController;
import com.unisoft.collection.distribution.loan.LoanViewModel;
import com.unisoft.collection.settings.SMS.smsType.SMSEntity;
import com.unisoft.collection.settings.SMS.smsType.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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


    @GetMapping("/get-sms")
    public static String sendSMS(String msisdn, String sms_string)
    {
        try
        {
            sms_string = "dfvdsd";
            msisdn = "01618811202";
            String url = "http://sms.sslwireless.com/pushapi/dynamic/server.php";
            String args = "?msisdn="+msisdn+"&sms="+URLEncoder.encode(sms_string)+"&user=IFICRECOVERY&pass=88x@6R57&sid=88x@6R57&csmsid=123456789";
            URL obj = new URL(url+args);
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
        }
        catch(Exception ex){
            return "<SMS_STATUS>ERROR</SMS_STATUS>";
        }
    }

    @PostMapping("/send-sms")
    public static String sendsmss(@RequestParam(value = "msisdn") String msisdn,
                                  @RequestParam(value = "sms_string") String sms_string)
    {
        try
        {
            String url = "https://sms.sslwireless.com/pushapi/dynamic/server.php";
            URL obj = new URL(url);
            HttpsURLConnectionImpl con = (HttpsURLConnectionImpl) obj.openConnection();
//add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User‐Agent", "USER_AGENT");
            con.setRequestProperty("Accept‐Language", "en‐US,en;q=0.5");
            String args = "user=IFICRECOVERY"+"&pass=88x@6R57"+"&sid=88x@6R57"+" &sms[0][0]=" + msisdn + "&sms[0][1]=" + sms_string + "&sms[0][2]="+UUID.randomUUID();
// Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(args);
            wr.flush();
            wr.close();
            System.out.println(con.getInputStream());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        catch(Exception ex){
            return "<SMS_STATUS>ERROR</SMS_STATUS>";
        }
    }

    public String convertBanglatoUnicode(String banglaText) {
        StringBuilder sb = new StringBuilder();
        for(char c : banglaText.toCharArray())
        {
            sb.append("{1:x4}", c, (int)c);
        }
        String unicode = sb.toString().toUpperCase();
        return unicode;
    }
}

