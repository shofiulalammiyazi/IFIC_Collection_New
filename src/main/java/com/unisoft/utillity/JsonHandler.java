package com.unisoft.utillity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonHandler {

    public HttpHeaders setHeader(Map<String, String> headers) {
        HttpHeaders header = new HttpHeaders();
        for (Entry<String, String> e : headers.entrySet()) {
            header.set(e.getKey(), e.getValue());
        }
        header.setContentType(MediaType.APPLICATION_JSON);
        return header;
    }

    public static String convertToJSON(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String str = mapper.writeValueAsString(obj);
            return str;
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String convertWebRequestToJSON(WebRequest request) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, String[]> map = request.getParameterMap();
        for (Entry<String, String[]> m : map.entrySet()) {
            if (m.getValue().length > 1) {
                json.put(m.getKey(), m.getValue());
            } else {
                json.put(m.getKey(), m.getValue()[0]);
            }
        }
        return convertToJSON(json);
    }

    public Map<String, Object> convertJSONToMap(String str) {
        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(str, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    public Map<String, String> exposeJsonDataContaines(List<String> strList, String JSON) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodes = mapper.readTree(JSON);
        for (String s : strList) {
            if (nodes.get(s) == null)
                map.put(s, null);
            else
                map.put(s, nodes.get(s).toString());
        }
        return map;
    }

    public String exposeJsonDataContaines(String targerProperty, String JSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodes = mapper.readTree(JSON);
        if (nodes.get(targerProperty) == null)
            return null;
        return nodes.get(targerProperty).toString();
    }

    public String toDelimiteredString(List<String> list) {
        if (list != null) {
            StringBuilder builder = new StringBuilder();
            list.stream().forEach(s -> {
                builder.append(s);
                builder.append(",");
            });
            return builder.deleteCharAt(builder.length()).toString();
        }
        return null;
    }

    public String unwrapJson(String req) {
        StringBuilder b = new StringBuilder(req);
        String s = b.substring(1, b.length() - 1);
        String result = s.replace("\\", "");
        return result;
    }
}
