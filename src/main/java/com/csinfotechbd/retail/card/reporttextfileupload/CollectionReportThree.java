package com.csinfotechbd.retail.card.reporttextfileupload;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class CollectionReportThree extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractNo;
    private String loanNo;
    private String startDate;
    private String lastPayDate;
    private String totalNoOfIns;
    private String paidNoOfInst;
    private String loanAmt;
    private String paidAmt;
    private String unpaidAmt;
}
