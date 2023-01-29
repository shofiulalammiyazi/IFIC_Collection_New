package com.unisoft.collection.settings.employee.API;

import lombok.Data;

@Data
public class EmployeeApiPayload {
    private String username;
    private String password;
    private String email;
    private String emp_id;
    private String cbs_id;

//    public EmployeeApiPayload(String username, String password, String email, String emp_id, String cbs_id) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.emp_id = emp_id;
//        this.cbs_id = cbs_id;
//    }
}
