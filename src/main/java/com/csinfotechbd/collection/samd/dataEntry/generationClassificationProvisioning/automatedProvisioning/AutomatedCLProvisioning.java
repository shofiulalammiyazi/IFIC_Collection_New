package com.csinfotechbd.collection.samd.dataEntry.generationClassificationProvisioning.automatedProvisioning;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "automated_cl_provisioning")
public class AutomatedCLProvisioning extends CommonEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String branchCode;
    private String branchName;
    private String loanAccountNo;
    private String loanAccountName;
    private String facilityNature;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date disbursementDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date expiryDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date overdueMonth;
    private Double overdueAmount;
    private Double outstanding;
    private Double interestSuspense;
    private Double eligibleSecurity;
    private String category;
    private String cLFile;
    private String actualCLStatus;
    private String actualBaseProvision;
    private String actualProvisionRequirement;
    private String provisionKeptSystem;
    private String customerId;

}
