package com.csinfotechbd.legal.setup.legalExpense;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "LEGAL_EXPENSE_DETAILS")
public class LegalExpenseDetailsEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   @ManyToOne(cascade = CascadeType.REFRESH)
   @JoinColumn(name = "litigation_case_info_id")
   //@JsonBackReference
   //@JsonIgnore
   private LitigationCaseInfo litigationCaseInfo;

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

    /*miscellaneous cost*/
    /*private Double miscellaneousAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date miscellaneousDate;
    private String remarks;*/

    /*common field*/
    private Double legalVat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date legalExpenseDate;
    private Double billReceivedAmount;
    private Double billPaymentAmount;
    private String billStatus;
    private Double legalAitFee;
    private String legalRemarks;

    /*Mediation*/
    private String mediationName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date mediationSubmissionDate;
    private String resultOfMediation;

}
