package com.unisoft.collection.settings.ivrSetupAndConfig;


import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;

@Service
public class IVRService {

    public void call(String callId) throws Exception {
        //            URL url = new URL("https://www.twilio.com/blog/5-ways-to-make-http-requests-in-java");
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//            int res = httpURLConnection.getResponseCode();
//            System.out.println(res);

        String accId = "234556";
        String ageId = "2222";
        String mobileNo = "+8801737829182";
        Date date = new Date();
        System.out.println(date.getTime());

        System.out.println(this.getMD5Value(accId, ageId, mobileNo, date.getTime()));
    }


    private String getMD5Value(String accId, String ageId, String mobileNo, Long milis) throws Exception {

        String s = accId.concat(ageId).concat(mobileNo).concat(String.valueOf(milis));
        System.out.println(s);

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] message = md.digest(s.getBytes());

        BigInteger no = new BigInteger(1, message);
        String ss = no.toString(16);

        System.out.println(ss.length());

        while (ss.length() < 32) {
            ss = "0" + ss;
        }

        return Base64.getEncoder().encodeToString(ss.getBytes());
    }
}
