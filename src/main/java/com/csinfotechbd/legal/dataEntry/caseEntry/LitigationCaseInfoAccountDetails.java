package com.csinfotechbd.legal.dataEntry.caseEntry;

import com.csinfotechbd.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
public class LitigationCaseInfoAccountDetails extends BaseInfo{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "litigation_case_info_id")
    @JsonBackReference
    @JsonIgnore
    private LitigationCaseInfo litigationCaseInfo;

    private String ldNo;
    private String zone;
    private String branchName;
    private String branchCode;
    private String customerCifNo;
    private String customerAccNum;
}
