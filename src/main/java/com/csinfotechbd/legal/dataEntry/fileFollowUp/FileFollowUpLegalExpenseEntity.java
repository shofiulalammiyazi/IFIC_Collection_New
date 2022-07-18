package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import com.csinfotechbd.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name = "FILE_FOLLOW_UP_LEGAL_EXPENSE")
public class FileFollowUpLegalExpenseEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "file_follow_up_legal_expense_id")
    //@JsonBackReference
    //@JsonIgnore
    private LitigationFileFollowUp litigationFileFollowUp;

    private Long legalExpenseId;

    private String legalExpenseName;

    /*professional fee*/
    private String typeOfFee;
    private Double numberOfLegalNoticeServed;
    private Double totalPayableAmount;
    private Double payingNow;

    /*Court Fee*/
    private Double procurementCost;

    /*Paper Publication*/
    private String typeOfPublication;
    private String sectionOfAra;
    private String paperName;
    private String agencyBlaName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date publicationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date paperPublicationAuctionDate;

    /*Collection of certified copy*/
    private String nameOfDocument;

    /*Utility Bill*/
    private String serviceName;


    /*commit field*/
    private Double legalVat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date legalExpenseDate;
    private Double legalAmount;
    private Double legalAitFee;
    private String legalRemarks;

    /*Mediation*/
    private String mediationName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date mediationSubmissionDate;
    private String resultOfMediation;
}
