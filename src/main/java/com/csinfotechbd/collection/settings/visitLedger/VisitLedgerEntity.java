package com.csinfotechbd.collection.settings.visitLedger;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*Created by NABIL on 1 August,2019*/

@Entity
@Data
public class VisitLedgerEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Transient
    private String visitDate;

    private String visitTime;

    private String accountCardNumber;

    private String location;

    private String remark;

    private String productUnit;

    private String username;

    private String firstName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfVisit;

    @Enumerated(EnumType.STRING)
    private VisitLedgerStatus status;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "visit_ledger_visitors",
            joinColumns = @JoinColumn(name = "visit_ledger_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "visitor_id"))
    private List<EmployeeInfoEntity> visitors = new ArrayList<>();

    @Transient
    private List<String> memberIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getAccountCardNumber() {
        return accountCardNumber;
    }

    public void setAccountCardNumber(String accountCardNumber) {
        this.accountCardNumber = accountCardNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
}
