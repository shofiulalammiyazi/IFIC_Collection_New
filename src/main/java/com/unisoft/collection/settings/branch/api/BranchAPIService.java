package com.unisoft.collection.settings.branch.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.unisoft.collection.settings.employee.API.EmployeeAPIResponse;
import com.unisoft.collection.settings.employee.API.EmployeeApiPayload;
import com.unisoft.collection.settings.employee.API.EmployeeDetails;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BranchAPIService {

    @Value("${ific.branch.api.url}")
    private String branchApiUrl;

    public BranchAPIResponse getBranchInfo() {

        BranchAPIResponse branchAPIResponse = new BranchAPIResponse();
        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(branchApiUrl);
            //post.setEntity(new UrlEncodedFormEntity(employeeApiPayload));
            post.addHeader("Content-type", "application/json");
            post.addHeader("Accept", "application/json");
            //StringEntity postingString = new StringEntity(gson.toJson(employeeApiPayload), ContentType.APPLICATION_JSON);

            //post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);
            String jsonString = EntityUtils.toString(response.getEntity());

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            branchAPIResponse = mapper.readValue(jsonStr, BranchAPIResponse.class);
            System.out.println(branchAPIResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new BranchAPIResponse();
        }
        return branchAPIResponse;
    }
}
