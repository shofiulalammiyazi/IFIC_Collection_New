package com.unisoft.common;

import com.unisoft.collection.samd.setup.approvalLevel.ApprovalLevel;
import com.unisoft.collection.samd.setup.approvalLevel.ApprovalLevelRepository;
import com.unisoft.collection.samd.setup.hrPosition.HrPosition;
import com.unisoft.collection.samd.setup.hrPosition.HrPositionService;
import com.unisoft.collection.samd.setup.proposalPreparedFor.ProposalPreparedReason;
import com.unisoft.collection.samd.setup.proposalPreparedFor.ProposalPreparedReasonRepository;
import com.unisoft.collection.samd.setup.proposalPurpose.ProposalPurpose;
import com.unisoft.collection.samd.setup.proposalPurpose.ProposalPurposeRepository;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanRepository;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EnumDataService {

    private AgencyService agencyService;

    private EmployeeService employeeService;

    private HrPositionService hrPositionService;

    private ApprovalLevelRepository approvalLevelRepository;

    private ProposalPurposeRepository proposalPurposeRepository;

    private ProposalPreparedReasonRepository proposalPreparedReasonRepository;

    private AssetClassificationLoanRepository assetClassificationLoanRepository;

    public Map<Long, String> getClStatuses() {
        Map<Long, String> statuses = new HashMap<>();
        List<AssetClassificationLoanEntity> allData = assetClassificationLoanRepository.findAll();
        for (AssetClassificationLoanEntity data : allData)
            statuses.put(data.getId(), data.getType().getName());
        return statuses;
    }

    public List<EmployeeInfoEntity> getDealers() {
        List<EmployeeInfoEntity> dealers = employeeService.getDealerList();
        return dealers;
    }

    public List<AgencyEntity> getAgencies() {
        List<AgencyEntity> agencies = agencyService.getAll();
        return agencies;
    }

    public Map<Long, String> getPositions() {
        Map<Long, String> positions = new HashMap<>();
        List<HrPosition> hrPositions = hrPositionService.findAllData();

        for (HrPosition position : hrPositions)
            positions.put(position.getId(), position.getShortName());
        return positions;
    }

    public Map<Long, String> getApprovalLevel() {
        Map<Long, String> levels = new HashMap<>();
        List<ApprovalLevel> allLevels = approvalLevelRepository.findAll();
        for (ApprovalLevel data : allLevels)
            levels.put(data.getId(), data.getName());
        return levels;
    }

    public Map<Long, String> getProposalPreparedReasons() {
        Map<Long, String> reasons = new HashMap<>();
        List<ProposalPreparedReason> allReasons = proposalPreparedReasonRepository.findAll();
        for (ProposalPreparedReason data : allReasons)
            reasons.put(data.getId(), data.getName());
        return reasons;
    }

    public Map<Long, String> getProposalPurposes() {
        Map<Long, String> purposes = new HashMap<>();
        List<ProposalPurpose> allPurposes = proposalPurposeRepository.findAll();
        for (ProposalPurpose data : allPurposes)
            purposes.put(data.getId(), data.getName());
        return purposes;
    }

    public List<EmployeeInfoEntity> getUnitWiseDealers() {
        List<EmployeeInfoEntity> dealers = employeeService.getUnitWiseDealer("Dealer", "SAM Loan");
        return dealers;
    }
}
