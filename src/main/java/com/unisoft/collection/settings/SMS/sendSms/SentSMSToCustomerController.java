package com.unisoft.collection.settings.SMS.sendSms;

import com.google.gson.Gson;
import com.unisoft.collection.distribution.loan.LoanController;
import com.unisoft.collection.distribution.loan.LoanViewModel;
import com.unisoft.collection.settings.SMS.SMSDto;
import com.unisoft.collection.settings.SMS.smsType.SMSEntity;
import com.unisoft.collection.settings.SMS.smsType.SMSService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

//    @PostMapping("/send-sms")
//    public static String sendsmss(@RequestParam(value = "msisdn") String msisdn,
//                                  @RequestParam(value = "sms_string") String sms_string) {
//        try {
//            //String url = "https://sms.sslwireless.com/pushapi/dynamic/server.php";
//            //String url = "192.168.1.94/sms.php";
//            String userName = "IFICRECOVERY";
//            String password = "8x@6R57";
//            String sid = "IFICRECOVERYENG";
//            Gson gson = new Gson();
//
//            SMSDto smsDto = new SMSDto(msisdn, sms_string, userName, password, sid, UUID.randomUUID().toString());
//
//            String args = gson.toJson(smsDto);
//
//            URL url = new URL("192.168.1.94/sms.php");
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setRequestProperty("Content-Type", "application/json");
//            httpURLConnection.setRequestProperty("Accept", "application/json");
//            httpURLConnection.setDoOutput(true);
//
//
//            OutputStream os = httpURLConnection.getOutputStream();
//            os.write(gson.toJson(smsDto).getBytes("UTF-8"));
//            os.close();
//
//
//            int res = httpURLConnection.getResponseCode();
//
//
//            /*HttpClient httpClient    = HttpClientBuilder.create().build();
//            HttpPost post          = new HttpPost(url);
//            StringEntity postingString = new StringEntity(gson.toJson(smsDto));//gson.tojson() converts your pojo to json
//            post.setEntity(postingString);
//            post.setHeader("Content-type", "application/json");
//            HttpResponse response1 = httpClient.execute(post);
//
//            URL obj = new URL(url);
//
//            HttpsURLConnectionImpl con = (HttpsURLConnectionImpl) obj.openConnection();
////add reuqest header
//            con.setRequestMethod("POST");
//            con.setRequestProperty("User‐Agent", "USER_AGENT");
//            con.setRequestProperty("Accept‐Language", "en‐US,en;q=0.5");
//
//                    //"user=IFICRECOVERY" + "&pass=88x@6R57" + "&sid=IFICRECOVERYENG" + "&sms[0][0]=" + msisdn + "&sms[0][1]=" + sms_string + "&sms[0][2]=" + UUID.randomUUID();
//// Send post request
//            con.setDoOutput(true);
//            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//            wr.writeBytes(args);
//            wr.flush();
//            wr.close();
//            System.out.println(con.getInputStream());
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();*/
//            return response.toString();
//        } catch (Exception ex) {
//            return "<SMS_STATUS>ERROR</SMS_STATUS>";
//        }
//    }

    @PostMapping("/send-sms")
    @ResponseBody
    public static String sendsmss(@RequestParam(value = "msisdn") String msisdn,
                                  @RequestParam(value = "sms_string") String sms_string) {
        try {
            //String url = "https://sms.sslwireless.com/pushapi/dynamic/server.php";
            String url = "http://192.168.1.94/sms.php";
            String userName = "IFICRECOVERY";
            String password = "88x@6R57";
            String sid = "IFICRECOVERYENG";
            Gson gson = new Gson();

            SMSDto smsDto = new SMSDto(msisdn, sms_string, userName, password, sid, UUID.randomUUID().toString());

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Accept", "application/json");
            StringEntity postingString = new StringEntity(gson.toJson(smsDto), ContentType.APPLICATION_JSON);

            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);
            System.out.println(response);

            return response.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "<SMS_STATUS>ERROR</SMS_STATUS>";
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
