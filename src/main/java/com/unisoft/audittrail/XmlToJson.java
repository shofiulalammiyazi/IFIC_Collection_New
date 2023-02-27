package com.unisoft.audittrail;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class XmlToJson {
    
    public static Document loadXMLString(String response) throws Exception
    {
        DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(response));
        
        return db.parse(is);
    }
    
    public static List<String> getFullNameFromXml(String response, String tagName) throws Exception {
        Document xmlDoc = loadXMLString(response);
        NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
        List<String> ids = new ArrayList<>(nodeList.getLength());
        for(int i=0;i<nodeList.getLength(); i++) {
            Node x = nodeList.item(i);
            ids.add(x.getFirstChild().getNodeValue());
        }
        return ids;
    }
    
    public static void main(String[] srg){
        String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soapenvelope\" xmlns:m0=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\"\n" +
                "xmlns:m1=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\">\n" +
                "<env:Body>\n" +
                "<m1:GetBackOfficeInfoRp >\n" +
                "<m1:Response Response=\"1\" TranId=\"60963154\" Ver=\"16.1\" Product=\"FIMI\">\n" +
                "<m0:Value>NDA2NDc0fFVDQjkyLTAwMDAwMDA3MDkyM3wyMS1GRUItMjAxN3xPa3xNQyBQTEFUSU5VTSBF\n" +
                "TVBMT1lFRVsxMF0KNDA2NDc0fDQ0MDAwMDQwNjQ3NDAwMDAwMDY2fDA2LURFQy0yMDE4fE9rfFZJU0EgUExBVElOVU0gRU1QTE\n" +
                "9ZRUUgUEFZIFdBVkVbMTBdCg==</m0:Value>\n" +
                "</m1:Response>\n" +
                "</m1:GetBackOfficeInfoRp>\n" +
                "</env:Body>\n" +
                "</env:Envelope>\n";
        try {
            List<String> output = getFullNameFromXml(response, "m0:Value");
            String[] strarray = new String[output.size()];
            output.toArray(strarray);
    
            Base64.Decoder decoder = Base64.getDecoder();
            String data = strarray[0].replaceAll("\n", "");
            String dStr = new String(decoder.decode(data));
    
            Scanner scanner = new Scanner(dStr);
            //List<ContractDetails> contractList = new ArrayList<>();
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] c = line.split("\\|");
//                ContractDetails contractDetails =new ContractDetails();
//                for(int i=0; i<c.length; i++){
//                    contractDetails.setClientId(c[0]);
//                    contractDetails.setContractNo(c[1]);
//                    contractDetails.setCreatedDate(c[2]);
//                    contractDetails.setContractStatus(c[3]);
//                    contractDetails.setContractType(c[4]);
//                }
                //contractList.add(contractDetails);
            }
            //System.out.println("contract details : "+contractList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
