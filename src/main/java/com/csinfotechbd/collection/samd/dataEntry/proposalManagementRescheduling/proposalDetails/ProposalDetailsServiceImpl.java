package com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails;


import com.csinfotechbd.cryptography.CryptoException;
import com.csinfotechbd.dms.DmsFileSaver;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProposalDetailsServiceImpl implements ProposalDetailsService {


    @Autowired
    private ProposalDetailsRepository proposalDetailsRepository;

    @Autowired
    private ProposalDetailsFileRepository proposalDetailsFileRepository;

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Override
    public ProposalDetails save(ProposalDetails proposalDetails) {
        ProposalDetails proposalDetails1 = proposalDetailsRepository.save(proposalDetails);

        MultipartFile file = proposalDetails.getFile();
        String fileType = file.getContentType();
        if (proposalDetails.getFile() != null) {
            try {
                String filePath = "proposal_details/" + proposalDetails1.getCustomerId();
                String fileName = file.getOriginalFilename();
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, session);

                ProposalDetailsFiles proposalDetailsFiles = new ProposalDetailsFiles();
                proposalDetailsFiles.setFileName(fileName);
                proposalDetailsFiles.setDmsFileId(dmsFileId);
                proposalDetailsFiles.setDmsFileType(fileType);
                proposalDetailsFiles.setProposalDetails(proposalDetails);
                proposalDetailsFiles.setProposalDetailsId(proposalDetails1.getId());
                proposalDetailsFileRepository.save(proposalDetailsFiles);

                System.out.println("test");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CryptoException e) {
                e.printStackTrace();
            }

        }
        return proposalDetails1;
    }

    @Override
    public ProposalDetails findProposalDetailsByCustomerId(String customerId) {
        return proposalDetailsRepository.findProposalDetailsByCustomerId(customerId);
    }


    @Override
    public ProposalDetailsDto getByCustomerId(String customerId) {
        Tuple proposalDetailsDtoList = proposalDetailsRepository.getByProposalDetailCustomerId(customerId);

        ProposalDetailsDto dto = new ProposalDetailsDto();

        dto.setId(proposalDetailsDtoList.get("id"));
        dto.setProposalType(proposalDetailsDtoList.get("proposalType"));
        dto.setBranchName(proposalDetailsDtoList.get("branchName"));
        dto.setLoanAccountNo(proposalDetailsDtoList.get("loanAccountNo"));
        dto.setLoanAccountName(proposalDetailsDtoList.get("loanAccountName"));
        dto.setFacilityNature(proposalDetailsDtoList.get("facilityNature"));
        dto.setCLStatus(proposalDetailsDtoList.get("cLStatus"));
        dto.setCLStatusId(proposalDetailsDtoList.get("cLStatusId"));
        dto.setDisbursementDate(proposalDetailsDtoList.get("disbursementDate"));
        dto.setExpiryDate(proposalDetailsDtoList.get("expiryDate"));
        dto.setProposalSendBy(proposalDetailsDtoList.get("proposalSendBy"));
        dto.setMemoNotePreparedBy(proposalDetailsDtoList.get("memoNotePreparedBy"));
        dto.setProposalPlacedToId(proposalDetailsDtoList.get("proposalPlacedToId"));
        dto.setPropPlaceName(proposalDetailsDtoList.get("propPlaceName"));
        dto.setApprovalLevelName(proposalDetailsDtoList.get("approvalLevelName"));
        dto.setApprovalLevelId(proposalDetailsDtoList.get("approvalLevelId"));
        dto.setProposalStatusId(proposalDetailsDtoList.get("proposalStatusId"));
        dto.setProposalStatus(proposalDetailsDtoList.get("proposalStatus"));
        dto.setTotalRecovery(proposalDetailsDtoList.get("totalRecovery"));
        dto.setOverdue(proposalDetailsDtoList.get("overdue"));
        dto.setOutstanding(proposalDetailsDtoList.get("outstanding"));
        dto.setFillingAmount(proposalDetailsDtoList.get("fillingAmount"));
        dto.setTypeOfCase(proposalDetailsDtoList.get("typeOfCase"));
        dto.setInterestSuspense(proposalDetailsDtoList.get("interestSuspense"));
        dto.setProvision(proposalDetailsDtoList.get("provision"));
        dto.setUnappliedInterest(proposalDetailsDtoList.get("unappliedInterest"));
        dto.setTotalClaimAmount(proposalDetailsDtoList.get("totalClaimAmount"));
        dto.setValueEligibleSecurity(proposalDetailsDtoList.get("valueEligibleSecurity"));
        dto.setProposedWaiverUnappliedInterest(proposalDetailsDtoList.get("proposedWaiverUnappliedInterest"));
        dto.setProposedWaiverInterestSuspense(proposalDetailsDtoList.get("proposedWaiverInterestSuspense"));
        dto.setProposedWaiverIncome(proposalDetailsDtoList.get("proposedWaiverIncome"));
        dto.setProposedTotalWaiver(proposalDetailsDtoList.get("proposedTotalWaiver"));
        dto.setOutstandingBeforeDownPayment(proposalDetailsDtoList.get("outstandingBeforeDownPayment"));
        dto.setDownPaymentDeterminingCriteria(proposalDetailsDtoList.get("downPaymentDeterminingCriteria"));
        dto.setBasisObtainingDownPayment(proposalDetailsDtoList.get("basisObtainingDownPayment"));
        dto.setRequiredDownPayment(proposalDetailsDtoList.get("requiredDownPayment"));
        dto.setRealizedDownPayment(proposalDetailsDtoList.get("realizedDownPayment"));
        dto.setRemainingDownPayment(proposalDetailsDtoList.get("remainingDownPayment"));
        dto.setOutstandingAfterDownPayment(proposalDetailsDtoList.get("outstandingAfterDownPayment"));
        dto.setExistingCaseStatus(proposalDetailsDtoList.get("existingCaseStatus"));
        dto.setDynamicCalculator(proposalDetailsDtoList.get("dynamicCalculator"));
        dto.setProposedRescheduleTimesId(proposalDetailsDtoList.get("proposedRescheduleTimesId"));
        dto.setProposedRescheduleTimes(proposalDetailsDtoList.get("proposedRescheduleTimes"));
        dto.setAllowableMaxRescheduleMonths(proposalDetailsDtoList.get("allowableMaxRescheduleMonths"));
        dto.setAllowedTimeReshedulement(proposalDetailsDtoList.get("allowedTimeReshedulement"));
        dto.setRelevantSubjectMemo(proposalDetailsDtoList.get("relevantSubjectMemo"));
        dto.setDateLastCIBReport(proposalDetailsDtoList.get("dateLastCIBReport"));
        dto.setBBApprovalRequirement(proposalDetailsDtoList.get("bBApprovalRequirement"));
        dto.setBBApprovalRequirementId(proposalDetailsDtoList.get("bBApprovalRequirementId"));
        dto.setLetterIssuingDateBBApproval(proposalDetailsDtoList.get("letterIssuingDateBBApproval"));
        dto.setBBApprovalReceivingDate(proposalDetailsDtoList.get("bBApprovalReceivingDate"));
        dto.setSanctionIssuingDate(proposalDetailsDtoList.get("sanctionIssuingDate"));
        dto.setRescheduleExecutionDate(proposalDetailsDtoList.get("rescheduleExecutionDate"));
        dto.setRescheduledAccountNo(proposalDetailsDtoList.get("rescheduledAccountNo"));
        dto.setInterestIncome(proposalDetailsDtoList.get("interestIncome"));
        dto.setCapitalRelease(proposalDetailsDtoList.get("capitalRelease"));
        dto.setNpLReduced(proposalDetailsDtoList.get("npLReduced"));
        dto.setRemarks(proposalDetailsDtoList.get("remarks"));
        dto.setTotalLegalAndOtherExpense(proposalDetailsDtoList.get("totalLegalAndOtherExpense"));


        dto.setFileName(proposalDetailsDtoList.get("fileName"));
        dto.setDmsFileId(proposalDetailsDtoList.get("dmsFileId"));
        dto.setDmsFileType(proposalDetailsDtoList.get("dmsFileType"));

        return dto;

    }
}
