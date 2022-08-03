package com.unisoft.collection.settings.employee;
/*
Created by   Islam at 7/2/2019

Regex validation by    on June 24, 2021
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.department.DepartmentEntity;
import com.unisoft.collection.settings.designation.DesignationEntity;
import com.unisoft.collection.settings.division.DivisionEntity;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusEntity;
import com.unisoft.collection.settings.jobRole.JobRoleEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.user.User;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Data
@Table(name = "EMPLOYEE_INFO_ENTITY")
public class EmployeeInfoEntity extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    @Column(unique = true)
    @Pattern(regexp = "[\\w]{4,10}", message = "Employee Id must be 4 to 10 characters. Only alphabet and numbers allowed.")
    private String pin;
    //private String name;

    @NotNull(message = "Please select designation")
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "designation_id")
    private DesignationEntity designation;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "location_id")
    private LocationEntity location;


    @Pattern(regexp = "|[\\d]{11}", message = "Please insert 11 digit phone number")
    private String homePhone;
    @Pattern(regexp = "[\\d]{11}", message = "Please insert 11 digit phone number")
    private String officePhone;
    @Pattern(regexp = "[a-zA-Z ,\\-_()]*", message = "Only alphabet and characters like ,-_() allowed")
    private String jobNature;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "job_role_id")
    private JobRoleEntity jobRole;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joiningDate;

    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date DOB;

    private String bloodGroup;
    @Pattern(regexp = "[a-zA-Z .\\-]*", message = "Please insert valid words")
    private String emergencyContactPerson;
    @Pattern(regexp = "|[\\d]{11}", message = "Please insert 11 digit phone number")
    private String emergencyContactNo;
    @Pattern(regexp = "[a-zA-Z ]*", message = "Please insert valid words")
    private String emergencyContactRel;
    private String presentAddress;
    private String permanentAddress;
    private String officeAddress;
    @Pattern(regexp = "([a-zA-Z]+|([a-zA-Z]*\\.?[a-zA-Z]+)+)@ucb\\.com\\.bd", message = "Please insert valid email address with suffix 'ucb.com.bd'")
    private String email;
    @Pattern(regexp = "|(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])",
            message = "Please insert valid IP address")
    private String ipAddress;
    @Pattern(regexp = "|[\\d]{4}|[\\d]{6}", message = "Please insert 4 or 6 digit number")
    private String ipPhoneNo;
    private double mobileLimit;
    @NotBlank(message = "Please check Unit")
    private String unit;


    private String otherDivision;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "division_id")
    private DivisionEntity division;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee_status_id")
    private EmployeeStatusEntity employeeStatus;

    @Lob
    private String signature;

    private String adviceLetter;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date adviceLetterDate;

    @Pattern(regexp = "[a-zA-Z.]*", message = "Please insert valid domain ID")
    private String domainId;
    @Pattern(regexp = "|[a-zA-Z0-9]{16}", message = "Please insert valid account number")
    private String loanAccountNo;
    @Pattern(regexp = "|[\\d]{14,16}", message = "Please insert 14 to 16 digits")
    private String creditCardNo;
    @Pattern(regexp = "|[\\d]{6}", message = "Please insert 6 digits")
    private String clientId;
    @Pattern(regexp = "|[a-zA-Z0-9]{16}", message = "Please insert valid account number")
    private String accountNo;
    @Pattern(regexp = "|[0-9]{9}", message = "Please insert valid CIF")
    private String cif;
    @Pattern(regexp = "[a-zA-Z .\\-]*", message = "Please insert valid words")
    private String fatherName;
    @Pattern(regexp = "[a-zA-Z .\\-]*", message = "Please insert valid words")
    private String motherName;
    @Pattern(regexp = "|.{4,32}", message = "Please insert valid Service Tag")
    private String serviceTag;
    @Pattern(regexp = "|[a-zA-Z0-9]{16}", message = "Please insert valid asset tag")
    private String assetTag;
    @Pattern(regexp = "[a-zA-Z .\\-]*", message = "Please insert valid words")
    private String spouseName;
    @Pattern(regexp = "|[\\d]{11}", message = "Please insert 11 digit phone number")
    private String spousePhone;
    private String lastEducation;
    private String domicileAddress;
    private String pcBrand;
    private String pcModel;
    private String[] trainingDetails;
    @Pattern(regexp = "|[a-zA-Z]-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}", message = "Please insert valid host name")
    private String hostName;
    @Pattern(regexp = "|((?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]))",
            message = "Please insert valid email address")
    private String pemail;
    @Pattern(regexp = "|[\\d]{10}|[\\d]{13}|[\\d]{17}", message = "Please insert valid NID")
    private String nid;
    @Pattern(regexp = "|[\\d]{12}", message = "Please insert valid eTIN")
    private String eTin;
    private String maritalStatus;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Lob
    private String photo;

    @Expose
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    @Valid
    private User user;

    public EmployeeInfoEntity() {

    }

    public EmployeeInfoEntity(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.toLowerCase() : null;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail != null ? pemail.toLowerCase() : null;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId != null ? domainId.toLowerCase() : null;
    }

    public void setLoanAccountNo(String loanAccountNo) {
        this.loanAccountNo = loanAccountNo != null ? loanAccountNo.toUpperCase() : null;
    }
}
