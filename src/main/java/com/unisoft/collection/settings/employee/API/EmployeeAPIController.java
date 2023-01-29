package com.unisoft.collection.settings.employee.API;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.unisoft.collection.settings.SMS.smslog.SMSLogDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee/api/")
public class EmployeeAPIController {

    @Value("ific.employee.api.ip")
    private String employeeApiIp;

    private String getEmployeeApiUrl = "/apigateway/api/employeeinfo";

    @PostMapping("getEmployee")
    public String getEmployeeInfo(@RequestBody EmployeeApiPayload employeeApiPayload) {

        List<EmployeeApiPayload> employeeApiPayloads = new ArrayList<>();

        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(employeeApiIp+getEmployeeApiUrl);
            //post.setEntity(new UrlEncodedFormEntity(employeeApiPayload));
            post.addHeader("Content-type", "application/json");
            post.addHeader("Accept", "application/json");
            StringEntity postingString = new StringEntity(gson.toJson(employeeApiPayload), ContentType.APPLICATION_JSON);

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
        } catch (Exception ex) {
            ex.printStackTrace();
            return "500";
        }
        return "OK";
    }
}
