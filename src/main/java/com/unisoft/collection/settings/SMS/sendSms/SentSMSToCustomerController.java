package com.unisoft.collection.settings.SMS.sendSms;

import com.unisoft.collection.distribution.loan.LoanController;
import com.unisoft.collection.distribution.loan.LoanViewModel;
import com.unisoft.collection.settings.SMS.SMSEntity;
import com.unisoft.collection.settings.SMS.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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


        List<LoanViewModel> loanViewModels = loanController.getLoanViewModels();
        model.addAttribute("loanviewlist", loanViewModels);

        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("smsEntityList", smsEntityList);

        return "collection/settings/sms/sendSms/smsSendToCustomer";

    }


    @GetMapping("/sms-to-customer")
    public String showSmsToCustomerList() {
        return "collection/settings/sms/sendSms/smsSendToCustomer";
    }


    @PostMapping("/send-sms")
    public static String sendsms(String msisdn, String sms) {
        try {
            String url = "";

            URL obj = new URL(url);
            HttpsURLConnectionImpl con = (HttpsURLConnectionImpl) obj.openConnection();
            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User‐Agent", "USER_AGENT");
            con.setRequestProperty("Accept‐Language", "en‐US,en;q=0.5");
            String args = "user=username&pass=password&sid=SID &sms=" + msisdn + "&sms =" + sms + "&sms=" + UUID.randomUUID();
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(args);
            wr.flush();
            wr.close();

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


    @GetMapping("/get-send-sms")
    public static String getSms(String msisdn, String sms) {
        try {
            String url = "";
            String args = "?msisdn=" + msisdn + "&sms=" + URLEncoder.encode(sms) +
                    "& user = USER NAME & pass = PASSWORD & sid = STAKEHOLDER & csmsid = 123456789 ";

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
}

