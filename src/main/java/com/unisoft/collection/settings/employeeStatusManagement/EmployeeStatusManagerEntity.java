package com.unisoft.collection.settings.employeeStatusManagement;
/*
Created by   Islam on 7/11/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "EMPLOYEE_STATUS_MANAGER")
public class
EmployeeStatusManagerEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "EMP_INFO_ID")
    private EmployeeInfoEntity employeeInfo;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "EMP_STATUS_ID")
    private EmployeeStatusEntity employeeStatus;


    private Long userId;

    private String remarks;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date endDate;


    @Transient
    private String totalDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeInfoEntity getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfoEntity employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public EmployeeStatusEntity getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatusEntity employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }
}
