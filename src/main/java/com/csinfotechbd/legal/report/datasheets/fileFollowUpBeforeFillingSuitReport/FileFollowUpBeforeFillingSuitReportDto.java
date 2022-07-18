package com.csinfotechbd.legal.report.datasheets.fileFollowUpBeforeFillingSuitReport;


// Created by Yasir Araphat on 15 February, 2021

import com.csinfotechbd.legal.dataEntry.fileFollowUp.LitigationFileFollowUp;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class FileFollowUpBeforeFillingSuitReportDto {
    private long slNo;
    private String ldNo;
    private Date fileTransferToSamdDate;
    private String branchCode;
    private String branchName;
    private String segment;
    private String cif;
    private String accountNo;
    private String accountName;
    private String accusedName;
    private String accusedMobile;
    private String legalNoticeServedFotAR;
    private String legalNoticeServedForNI;
    private String auctionNoticePublished;
    private Date auctionHeldDate;
    private String newspaperName;
    private long participantCount;
    private double highestBidAmount;
    private double outstanding;
    private double forSaleValue;
    private double marketValue;
    private double totalLand;
    private String bidderParticipation;
    private String auctionAccepted; // Y/N
    private String propertySold; // Y/N
    private String accountAdjusted; // Y/N
    private double amountAfterAdjustment;

    private String accountRescheduled; // Y/N
    private String status;
    private String remark;

    public FileFollowUpBeforeFillingSuitReportDto() {
    }

    public FileFollowUpBeforeFillingSuitReportDto(LitigationFileFollowUp followUp, int slNo) {
        BeanUtils.copyProperties(followUp, this);
        this.slNo = slNo;
//        ldNo = Optional.ofNullable(followUp.getLdNo()).orElse(0L);
//        fileTransferToSamdDate = followUp.getFileTransferToSAMD();
//        branchCode = followUp.getBranchCode();
//        branchName = followUp.getBranchName();
//        segment = followUp.getBusinessSegment();
//        cif = followUp.getCustomerCifNumber();
//        accountNo = followUp.getCustomerAccountNumber();
//        accountName = followUp.getAccountName();
//        accusedName = followUp.getBorrowerName();
//        accusedMobile = followUp.getCustomerMobileNumber();
//        legalNoticeServedFotAR = followUp.isLegalNoticeServedForArtharinSuit() ? "Y" : "N";
//        legalNoticeServedForNI = followUp.isLegalNoticeServedForUS138OfNIAct() ? "Y" : "N";
//        auctionNoticePublished = followUp.isAuctionNoticePublished() ? "Y" : "N";
//        auctionHeldDate = followUp.getDateOfAuctionHeldOn();
//        newspaperName = followUp.getNewspaperNameForActionHeldOn();
//        participantCount = Optional.ofNullable(followUp.getNumberOfParticipants()).orElse(0L);
//        highestBidAmount = Optional.ofNullable(followUp.getHighestBidAmount()).orElse(0D);
//        outstanding = Optional.ofNullable(followUp.getLoanOutstanding()).orElse(0D);
//        forSaleValue = Optional.ofNullable(followUp.getForceSaleValue()).orElse(0D);
//        marketValue = Optional.ofNullable(followUp.getMarketValue()).orElse(0D);
////        totalLand = Optional.ofNullable(followUp.getNumberOfScheduledLand()).orElse(0L);
//        bidderParticipation = followUp.getBiddersParticipation();
//        auctionAccepted = followUp.isAuctionAcceptedByManagement() ? "Y" : "N";
//        propertySold = followUp.isPropertySoldThroughAuction() ? "Y" : "N";
//        accountAdjusted = followUp.isAccountAdjustedBeforeFilingArtharinSuit() ? "Y" : "N";
//        amountAfterAdjustment = Optional.ofNullable(followUp.getAmountAfterPartialAdjustmentThroughAuction()).orElse(0D);
//        accountRescheduled = followUp.isAccountRescheduled() ? "Y" : "N";
//        status = followUp.isEnabled() ? "Active" : "Inactive";
//        remark = followUp.getRemark();

    }

}
