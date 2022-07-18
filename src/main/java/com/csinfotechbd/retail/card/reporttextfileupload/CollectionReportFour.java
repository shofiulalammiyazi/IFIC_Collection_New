package com.csinfotechbd.retail.card.reporttextfileupload;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class CollectionReportFour extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractId;
    private String contractType;
    private String contractStatus;
    private String fdrInformation;
    private String referenceNameFirst;
    private String relationShip;
    private String referenceMobileFirst;
    private String referenceNameSecond;
    private String referenceMobileSecond;
    private String agingDate;
}
