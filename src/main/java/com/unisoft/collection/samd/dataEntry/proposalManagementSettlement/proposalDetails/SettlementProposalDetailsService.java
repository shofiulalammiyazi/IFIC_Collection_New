package com.unisoft.collection.samd.dataEntry.proposalManagementSettlement.proposalDetails;

import com.unisoft.cryptography.CryptoException;
import com.unisoft.dms.DmsFileSaver;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SettlementProposalDetailsService {

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private SettlementProposalDetailsFilesRepository settlementProposalDetailsFilesRepository;


    private final SettlementProposalDetailsRepository settlementProposalDetailsRepository;

    public SettlementProposalDetails save(SettlementProposalDetails proposalDetails) {
        proposalDetails = settlementProposalDetailsRepository.save(proposalDetails);

        MultipartFile file = proposalDetails.getFile();
        String fileType = file.getContentType();
        if (proposalDetails.getFile() != null) {
            try {
                String filePath = "settlement_proposal_details/" + proposalDetails.getCustomerId();
                String fileName = file.getOriginalFilename();
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, session);

                SettlementProposalDetailsFiles settlementProposalDetailsFiles = new SettlementProposalDetailsFiles();
                settlementProposalDetailsFiles.setFileName(fileName);
                settlementProposalDetailsFiles.setDmsFileId(dmsFileId);
                settlementProposalDetailsFiles.setDmsFileType(fileType);
                settlementProposalDetailsFiles.setSettlementProposalDetails(proposalDetails);
                settlementProposalDetailsFiles.setSettlementProposalDetailsId(proposalDetails.getId());
                settlementProposalDetailsFilesRepository.save(settlementProposalDetailsFiles);

                System.out.println("test");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CryptoException e) {
                e.printStackTrace();
            }

        }

        return proposalDetails;
    }

    public SettlementProposalDetails findProposalDetailsByCustomerId(String customerId) {
        return settlementProposalDetailsRepository.findProposalDetailsByCustomerId(customerId);
    }

    public SettlementProposalDetailsDto getByCustomerId(String customerId) {
        Tuple settlementDetailDto = settlementProposalDetailsRepository.getBySettleMentDetailsCustomerId(customerId);

        SettlementProposalDetailsDto dto = new SettlementProposalDetailsDto();

        dto.setId(settlementDetailDto.get("id"));
        dto.setProposalType(settlementDetailDto.get("proposalType"));
        dto.setBranchName(settlementDetailDto.get("branchName"));
        dto.setLoanAccountNo(settlementDetailDto.get("loanAccountNo"));
        dto.setLoanAccountName(settlementDetailDto.get("loanAccountName"));
        dto.setFacilityNature(settlementDetailDto.get("facilityNature"));
        dto.setCLStatus(settlementDetailDto.get("cLStatus"));
        dto.setCLStatusId(settlementDetailDto.get("cLStatusId"));
        dto.setProposalPlacedToId(settlementDetailDto.get("proposalPlacedToId"));
        dto.setPropPlaceName(settlementDetailDto.get("propPlaceName"));
        dto.setApprovalLevelName(settlementDetailDto.get("approvalLevelName"));
        dto.setApprovalLevelId(settlementDetailDto.get("approvalLevelId"));
        dto.setProposalStatusId(settlementDetailDto.get("proposalStatusId"));
        dto.setProposalStatus(settlementDetailDto.get("proposalStatus"));

        dto.setDisbursementDate(settlementDetailDto.get("disbursementDate"));
        dto.setExpiryDate(settlementDetailDto.get("expiryDate"));
        dto.setProposalSendBy(settlementDetailDto.get("proposalSendBy"));
        dto.setMemoNotePreparedBy(settlementDetailDto.get("memoNotePreparedBy"));
        dto.setTotalRecovery(settlementDetailDto.get("totalRecovery"));
        dto.setFillingAmount(settlementDetailDto.get("fillingAmount"));
        dto.setTypeOfCase(settlementDetailDto.get("typeOfCase"));
        dto.setValueEligibleSecurity(settlementDetailDto.get("valueEligibleSecurity"));
        dto.setProvision(settlementDetailDto.get("provision"));
        dto.setPrincipleAmount(settlementDetailDto.get("principleAmount"));
        dto.setInterestTaken(settlementDetailDto.get("interestTaken"));
        dto.setInterestKept(settlementDetailDto.get("interestKept"));
        dto.setLegalBillOrOtherExpense(settlementDetailDto.get("legalBillOrOtherExpense"));
        dto.setSubTotal(settlementDetailDto.get("subTotal"));
        dto.setTotalRecovery(settlementDetailDto.get("totalRecovery"));
        dto.setLedgerOutstanding(settlementDetailDto.get("ledgerOutstanding"));
        dto.setUnappliedInterest(settlementDetailDto.get("unappliedInterest"));
        dto.setTotalClaimAmount(settlementDetailDto.get("totalClaimAmount"));
        dto.setExistingCaseStatus(settlementDetailDto.get("existingCaseStatus"));
        dto.setDynamicCalculator(settlementDetailDto.get("dynamicCalculator"));
        dto.setProposedWaiverUnappliedInterest(settlementDetailDto.get("proposedWaiverUnappliedInterest"));
        dto.setProposedWaiverInterestSuspense(settlementDetailDto.get("proposedWaiverInterestSuspense"));
        dto.setProposedWaiverIncome(settlementDetailDto.get("proposedWaiverIncome"));
        dto.setProposedTotalWaiver(settlementDetailDto.get("proposedTotalWaiver"));
        dto.setBorrowerWantsToPay(settlementDetailDto.get("borrowerWantsToPay"));
        dto.setTentativeSettlementDate(settlementDetailDto.get("tentativeSettlementDate"));
        dto.setInterestIncome(settlementDetailDto.get("interestIncome"));
        dto.setCapitalRelease(settlementDetailDto.get("capitalRelease"));
        dto.setNpLReduced(settlementDetailDto.get("npLReduced"));
        dto.setRemarks(settlementDetailDto.get("remarks"));
        dto.setLegalDetailStatus(settlementDetailDto.get("legalDetailStatus"));
        dto.setTotalLegalAndOtherExpense(settlementDetailDto.get("totalLegalAndOtherExpense"));


        dto.setFileName(settlementDetailDto.get("fileName"));
        dto.setDmsFileId(settlementDetailDto.get("dmsFileId"));
        dto.setDmsFileType(settlementDetailDto.get("dmsFileType"));

        return dto;

    }
}
