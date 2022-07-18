package com.csinfotechbd.legal.setup.artharinCourtReport;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ArtharinCourtReportEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String artharinNumberOne;
    private String  artharinClaimAmountOne;
    private String artharinNumberTwo;
    private String  artharinClaimAmountTwo;
    private String  artharinActualRealization;
    private String  artharinClaimAmountOfCollection;

    private String bankruptcyNumberOne;
    private String  bankruptcyClaimAmountOne;
    private String bankruptcyNumberTwo;
    private String  bankruptcyClaimAmountTwo;
    private String  bankruptcyActualRealization;
    private String  bankruptcyClaimAmountOfCollection;

    private String othersNumberOne;
    private String  othersClaimAmountOne;
    private String othersNumberTwo;
    private String  othersClaimAmountTwo;
    private String  othersActualRealization;
    private String  othersClaimAmountOfCollection;

    private String totalNumberOne;
    private String  totalClaimAmountOne;
    private String totalNumberTwo;
    private String  totalClaimAmountTwo;
    private String  totalActualRealization;
    private String  totalClaimAmountOfCollection;
}
