package com.csinfotechbd.collection.samd.dataEntry.detailsOfHeadOfficeApproval;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class DetailsOfHeadOfficeApproval extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sanctionDetails;
    private String sl;
    private String sanctionNo;
    private String detailsDate;
    private String approvalAuthority;
    private String facilityType;
    private String limitTaka;
    private String validity;
    private String dealerPin;
    private String loanAccountNo;
}
