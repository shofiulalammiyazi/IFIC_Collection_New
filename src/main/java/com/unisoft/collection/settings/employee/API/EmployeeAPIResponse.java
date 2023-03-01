package com.unisoft.collection.settings.employee.API;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeAPIResponse {


    private String statusCode;
    private String message;
    private String error;
    private List<EmployeeDetails> data;

}

