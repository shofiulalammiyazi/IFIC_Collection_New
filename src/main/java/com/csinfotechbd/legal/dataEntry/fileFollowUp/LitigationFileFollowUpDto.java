package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import com.csinfotechbd.legal.setup.collateralSecurity.FileFollowCollateralSecurityDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
public class LitigationFileFollowUpDto {

    private Long id;
    private String ldNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fileTransferToSAMD;
    private String branchCode;
    private String branchName;
    private String accountName;
    private String borrowerName;
    private String customerCifNumber;
    private String customerAccountNumber;
    private String customerMobileNumber;
    private String businessSegment;

    //private String legalNoticeServedForArtharinSuitClaimAmount;
    //private String legalNoticeServedForUS138ClaimedAmount;

    /*private boolean legalNoticeServedForArtharinSuit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfLegalNoticeServedForArtharinSuit;*/
    /*private boolean legalNoticeServedForUS138OfNIAct;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfLegalNoticeServedForUS138OfNIAct;*/
    private boolean auctionNoticePublished;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAuctionNoticePublished;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAuctionHeldOn;
    private String newspaperNameForActionHeldOn;
    private boolean biddersParticipated;
    private Long numberOfParticipants;
    private Double highestBidAmount;
    private Double loanOutstanding;
    private Double forceSaleValue;
    private Double marketValue;
    private Long numberOfScheduledLand;
    private String totalLandArea;
    private String biddersParticipation;
    private boolean auctionAcceptedByManagement;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAuctionAcceptedByManagement;
    private boolean propertySoldThroughAuction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfPropertySoldThroughAuction;
    private boolean accountAdjustedBeforeFilingArtharinSuit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAccountAdjustedBeforeFilingArtharinSuit;
    private Double amountAfterPartialAdjustmentThroughAuction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfPartialAdjustmentThroughAuction;
    //private boolean accountRescheduled;
    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAccountRescheduled;*/
    private String remarks;

    private String accountAdjustedAmount;
    private String adjustedWithExcessAmount;


    private List<AuctionNoticePublishedEntity> auctionNoticePublishedEntityList;
    private List<FileFollowUpLegalExpenseEntity> fileFollowUpLegalExpenseEntityList;
    private List<AuctionStayedEntity> auctionStayedEntityList;
    private List<FileFollowUpAccountResheduled> fileFollowUpAccountResheduledList;
    private List<FileFollowUpLegalNoticeServedForArtharinSuit> fileFollowUpLegalNoticeServedForArtharinSuits;
    private List<FileFollowUpLegalNoticeServedNIAct> fileFollowUpLegalNoticeServedNIActs;
    private List<FileFollowCollateralSecurityDetails> fileFollowUpCollateralSecurityDetailsList;

    private boolean auctionStayed;
    private String auctionStayedCaseNo;
    private String zone;
    private String district;
    private String division;
    private boolean callBackNoticeServed;
    private boolean demandNoticeServed;
    private Double claimAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date demandNoticeServedDate;
    private Double outStanding;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date outstandingDate;
    private boolean writtenOff;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date writtenOfDate;

    private Double writtenOffAmount;
    private String numberOfSchedule;
    private String collateralTotalLandArea;
    private Double totalMarketValue;
    private Double totalForcedSaleValue;

    /*private String installmentSize;
    private String numberOfInstallment;
    private String tenure;*/

    private boolean niActCaseFiled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date niActCaseFiledDate;
    private String niActCaseNo;

    private boolean arsFiled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date arsFiledDate;
    private String arsFiledCaseNo;
    private String stayStatus;

    public LitigationFileFollowUpDto() {
    }

    public LitigationFileFollowUpDto(LitigationFileFollowUp fileFollowUp) {
        id = fileFollowUp.getId();
        BeanUtils.copyProperties(fileFollowUp, this);
//        ldNo = fileFollowUp.getLdNo();
//        fileTransferToSAMD = fileFollowUp.getFileTransferToSAMD();
//        branchCode = fileFollowUp.getBranchCode();
//        branchName = fileFollowUp.getBranchName();
//        accountName = fileFollowUp.getAccountName();
//        borrowerName = fileFollowUp.getBorrowerName();
//        customerCifNumber = fileFollowUp.getCustomerCifNumber();
//        customerAccountNumber = fileFollowUp.getCustomerAccountNumber();
//        customerMobileNumber = fileFollowUp.getCustomerMobileNumber();
//        businessSegment = fileFollowUp.getBusinessSegment();
//        legalNoticeServedForArtharinSuit = fileFollowUp.isLegalNoticeServedForArtharinSuit();
//        dateOfLegalNoticeServedForArtharinSuit = fileFollowUp.getDateOfLegalNoticeServedForArtharinSuit();
//        legalNoticeServedForUS138OfNIAct = fileFollowUp.isLegalNoticeServedForUS138OfNIAct();
//        dateOfLegalNoticeServedForUS138OfNIAct = fileFollowUp.getDateOfLegalNoticeServedForUS138OfNIAct();
//        auctionNoticePublished = fileFollowUp.isAuctionNoticePublished();
//        dateOfAuctionNoticePublished = fileFollowUp.getDateOfAuctionNoticePublished();
//        dateOfAuctionHeldOn = fileFollowUp.getDateOfAuctionHeldOn();
//        newspaperNameForActionHeldOn = fileFollowUp.getNewspaperNameForActionHeldOn();
//        biddersParticipated = fileFollowUp.isBiddersParticipated();
//        numberOfParticipants = fileFollowUp.getNumberOfParticipants();
//        highestBidAmount = fileFollowUp.getHighestBidAmount();
//        loanOutstanding = fileFollowUp.getLoanOutstanding();
//        forceSaleValue = fileFollowUp.getForceSaleValue();
//        marketValue = fileFollowUp.getMarketValue();
////        numberOfScheduledLand = fileFollowUp.getNumberOfScheduledLand();
//        totalLandArea = fileFollowUp.getTotalLandArea();
//        biddersParticipation = fileFollowUp.getBiddersParticipation();
//        auctionAcceptedByManagement = fileFollowUp.isAuctionAcceptedByManagement();
//        dateOfAuctionAcceptedByManagement = fileFollowUp.getDateOfAuctionAcceptedByManagement();
//        propertySoldThroughAuction = fileFollowUp.isPropertySoldThroughAuction();
//        dateOfPropertySoldThroughAuction = fileFollowUp.getDateOfPropertySoldThroughAuction();
//        accountAdjustedBeforeFilingArtharinSuit = fileFollowUp.isAccountAdjustedBeforeFilingArtharinSuit();
//        dateOfAccountAdjustedBeforeFilingArtharinSuit = fileFollowUp.getDateOfAccountAdjustedBeforeFilingArtharinSuit();
//        amountAfterPartialAdjustmentThroughAuction = fileFollowUp.getAmountAfterPartialAdjustmentThroughAuction();
//        dateOfPartialAdjustmentThroughAuction = fileFollowUp.getDateOfPartialAdjustmentThroughAuction();
//        accountRescheduled = fileFollowUp.isAccountRescheduled();
//        dateOfAccountRescheduled = fileFollowUp.getDateOfAccountRescheduled();
//        remarks = fileFollowUp.getRemark();
    }


}
