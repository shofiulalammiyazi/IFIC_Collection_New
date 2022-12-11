package com.unisoft.collection.settings.ivrSetupAndConfig;


import com.google.gson.Gson;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final String shareKey = "46b7dc11c0c5292a753457881db298d2";

    @Autowired
    private IVRRepository ivrRepository;

    public String call(String callId) throws Exception {

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date date = new Date();
        Gson gson = new Gson();

        IvrEntity ivrEntity = ivrRepository.findByDealerPin(principal.getUsername());

        String accId = ivrEntity.getIvrAccId();
        String ageId = ivrEntity.getIvrAgentId();
        String time = String.valueOf(date.getTime());
        String skillId = ivrEntity.getSkillId();
        String hashVal = this.MD5Encode(accId.concat(ageId).concat(callId).concat(time).concat(shareKey));

        return this.callUrl(Base64.getEncoder().encodeToString(gson.toJson(new IvrDto(accId, ageId, callId, time, hashVal, skillId)).getBytes()));
    }

    private String MD5Encode(String sourceString) {
        try {
            byte[] bytesOfMessage = sourceString.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest(bytesOfMessage);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < md5.length; i++) {
                stringBuffer.append(Integer.toString((md5[i] & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuffer.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String callUrl(String request) throws IOException {
        URL url = new URL(callUrl + request);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int res = httpURLConnection.getResponseCode();

        return String.valueOf(res);
    }
}
