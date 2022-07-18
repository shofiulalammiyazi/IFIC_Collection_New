package com.csinfotechbd.retail.card.reporttextfileupload;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table

public class AgingHistory extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractNo;
    private String opDate;
    private String aging;
    private String operation;
    private String comments;
    private String recNo;
    private String status;
}
