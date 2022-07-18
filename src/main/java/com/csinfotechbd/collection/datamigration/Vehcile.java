package com.csinfotechbd.collection.datamigration;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Vehcile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountNo;

    private String accountName;

    private String clStatus;

    @Temporal(TemporalType.DATE)
    private Date repossetionDate;

    private String vechileChesisNo;

    private String vechileEngineNo;

    private String vechileRegistrationNo;

    @Temporal(TemporalType.DATE)
    private Date vechileReleaseDate;

    private String yearManufacture;

    private String vechileType;

    public Vehcile() {
    }

    public Vehcile(String accountNo, String accountName, String clStatus, Date repossetionDate, String vechileChesisNo, String vechileEngineNo, String vechileRegistrationNo, Date vechileReleaseDate, String yearManufacture, String vechileType) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.clStatus = clStatus;
        this.repossetionDate = repossetionDate;
        this.vechileChesisNo = vechileChesisNo;
        this.vechileEngineNo = vechileEngineNo;
        this.vechileRegistrationNo = vechileRegistrationNo;
        this.vechileReleaseDate = vechileReleaseDate;
        this.yearManufacture = yearManufacture;
        this.vechileType = vechileType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getClStatus() {
        return clStatus;
    }

    public void setClStatus(String clStatus) {
        this.clStatus = clStatus;
    }

    public Date getRepossetionDate() {
        return repossetionDate;
    }

    public void setRepossetionDate(Date repossetionDate) {
        this.repossetionDate = repossetionDate;
    }

    public String getVechileChesisNo() {
        return vechileChesisNo;
    }

    public void setVechileChesisNo(String vechileChesisNo) {
        this.vechileChesisNo = vechileChesisNo;
    }

    public String getVechileEngineNo() {
        return vechileEngineNo;
    }

    public void setVechileEngineNo(String vechileEngineNo) {
        this.vechileEngineNo = vechileEngineNo;
    }

    public String getVechileRegistrationNo() {
        return vechileRegistrationNo;
    }

    public void setVechileRegistrationNo(String vechileRegistrationNo) {
        this.vechileRegistrationNo = vechileRegistrationNo;
    }

    public Date getVechileReleaseDate() {
        return vechileReleaseDate;
    }

    public void setVechileReleaseDate(Date vechileReleaseDate) {
        this.vechileReleaseDate = vechileReleaseDate;
    }

    public String getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(String yearManufacture) {
        this.yearManufacture = yearManufacture;
    }

    public String getVechileType() {
        return vechileType;
    }

    public void setVechileType(String vechileType) {
        this.vechileType = vechileType;
    }
}
