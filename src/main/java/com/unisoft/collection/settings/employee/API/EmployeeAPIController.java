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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee/api/")
public class EmployeeAPIController {

    @Autowired
    private EmployeeAPIService employeeAPIService;
    
    @Value("${ific.employee.api.user}")
    private String employeeAPIUsername;
    
    @Value("${ific.employee.api.pass}")
    private String employeeAPIPass;

    @GetMapping("getEmployee")
    public EmployeeDetails callApi(@RequestParam("username") String username){
       return employeeAPIService.getEmployeeInfo(new EmployeeApiPayload(employeeAPIUsername,employeeAPIPass.substring(2,employeeAPIPass.length()-2),username+"@ificbankbd.com","",""));
    }

}
