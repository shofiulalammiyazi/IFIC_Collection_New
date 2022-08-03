package com.unisoft.retail.card.cardAPI;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class CardAPIManager {
    
    private static String API_URL="http://172.25.32.31:21027";
    
    public static Document loadXMLString(String response) throws Exception{
        DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(response));
        return db.parse(is);
    }
    
    public static List<String> getValueFromXml(String response, String tagName) throws Exception {
        Document xmlDoc = loadXMLString(response);
        NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
        List<String> ids = new ArrayList<>(nodeList.getLength());
        for(int i=0;i<nodeList.getLength(); i++) {
            Node x = nodeList.item(i);
            ids.add(x.getFirstChild().getNodeValue());
        }
        return ids;
    }
    
    public static String getValueAsStringAfterDecode(String response){
        try {
            List<String> output = getValueFromXml(response, "m0:Value");
            String[] strArray = new String[output.size()];
            output.toArray(strArray);
    
            Base64.Decoder decoder = Base64.getDecoder();
            String data = strArray[0].replaceAll("\n", "");
            String s = new String(decoder.decode(data));
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String getValueByTagAsStringAfterDecode(String response,String tag){
        try {
            List<String> output = getValueFromXml(response, tag);
            String[] strArray = new String[output.size()];
            output.toArray(strArray);

            Base64.Decoder decoder = Base64.getDecoder();
            String data = strArray[0].replaceAll("\n", "");
            String s = new String(decoder.decode(data));
            return data;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    
    public static String apiRequestProcess(String xml) {
        try {
             URL obj = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(xml);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();
            System.out.println(responseStatus);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String r = response.toString();
            r = r.replace("xmlns"," xmlns");
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSessionId(){
        String request = "<env:Envelope xmlns:env='http://www.w3.org/2003/05/soap-envelope' xmlns:m1='http://schemas.compassplus.com/two/3.6/fimi.xsd' xmlns:m0='http://schemas.compassplus.com/two/1.0/fimi_types.xsd'>\n" +
                "<env:Body>\n" +
                "<m1:InitSessionRq>\n" +
                "<m1:Request Ver='3.1' Product='FIMI' RetAddress='172.25.32.31' Clerk='COLLECTION' Password='Casablanca'>\n" +
                "<m0:NeedDicts>0</m0:NeedDicts>\n" +
                "<m0:AllVendors>0</m0:AllVendors>\n" +
                "<m0:AvoidSession>0</m0:AvoidSession>\n" +
                "</m1:Request>\n" +
                "</m1:InitSessionRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";

        String response = apiRequestProcess(request);

        return response;
    }
}
