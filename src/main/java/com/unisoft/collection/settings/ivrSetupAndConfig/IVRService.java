package com.unisoft.collection.settings.ivrSetupAndConfig;


import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class IVRService {

    private final String callUrl = "http://192.168.1.187/ccpro/click-to-call/?param=";

    public void call(String callId) throws Exception {

        Gson gson = new Gson();
        String accId = "550153";
        String ageId = "9001";
        String mobileNo = "8801618811202";
        Date date = new Date();
        String time = String.valueOf(date.getTime());
        String skillId = "AG";
        String shareKey = "46b7dc11c0c5292a753457881db298d2";
        String hashVal = this.getMD5Value(accId, ageId, mobileNo, date.getTime(),shareKey);

        IvrDto ivrDto = new IvrDto(
                accId,ageId,mobileNo,time,hashVal,skillId);

        System.out.println(gson.toJson(ivrDto).getBytes());

        String requestString = Base64.getEncoder().encodeToString(gson.toJson(ivrDto).getBytes());

        this.callUrl(requestString);
    }


    private String getMD5Value(String accId, String ageId, String mobileNo, Long milis, String shareKey) throws NoSuchAlgorithmException {

        String s = accId.concat(ageId).concat(mobileNo).concat(String.valueOf(milis).concat(shareKey));
        System.out.println(s);

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] message = md.digest(s.getBytes());

        //BigInteger no = new BigInteger(1, message);
        //String ss = no.toString(16);

        //System.out.println(ss.length());

        //System.out.println(Base64.getEncoder().encodeToString(ss.getBytes()));

        return Base64.getEncoder().encodeToString(message);
    }

    private String callUrl(String request) throws IOException {
        URL url = new URL(callUrl+request);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int res = httpURLConnection.getResponseCode();
        System.out.println(res);

        return String.valueOf(res);
    }
}
