package com.csinfotechbd.legal.dataEntry.caseEntry;

import com.csinfotechbd.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "LITIGATION_CASE_INFO_RECOVERY_BEFORE_CASE")
public class RecoveryBeforeCaseDetails extends BaseInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "litigation_case_info_id")
    private LitigationCaseInfo litigationCaseInfo;

    private Double recoveryBeforeCaseAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date recoveryBeforeCaseDate;

    private Long recoveryBeforeCaseIds;
}
