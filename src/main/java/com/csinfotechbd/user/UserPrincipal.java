package com.csinfotechbd.user;

import lombok.Data;

@Data
public class UserPrincipal {

    private long id;

    private String username;

    private String firstName;

    private String lastName;

    private String empId;

    private String branchCode;

    private String module;
    private String agencyId;

    public UserPrincipal(long id, String username, String firstName, String lastName, String empId, String branchCode,
                         String module) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.empId = empId;
        this.branchCode = branchCode;
        this.module = module;
    }


    public UserPrincipal(long id, String username, String firstName, String lastName, String module, String agencyId) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.module = module;
        this.agencyId = agencyId;
    }

    public UserPrincipal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
