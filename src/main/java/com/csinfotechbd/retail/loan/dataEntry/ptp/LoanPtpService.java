package com.csinfotechbd.retail.loan.dataEntry.ptp;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanPtpService {

    private final LoanPtpRepository repository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<LoanPtp> list(boolean a, CustomerBasicInfoEntity customerBasicInfoEntity) {
        return repository.findByEnabledIsAndCustomerBasicInfo(true, customerBasicInfoEntity);
    }

    public List<PtpStatusSummary> getCurrentMonthFollowUpSummmary(String designation, String employeePin) {
        List<PtpStatusSummary> ptpStatusSummaryList = new LinkedList<>();
        switch (designation.replaceAll(" ", "").toLowerCase()) {
            case "teamleader":
                ptpStatusSummaryList = repository.getPtpSummaryByTeamLeader(employeePin);
                break;
            case "manager":
                ptpStatusSummaryList = repository.getPtpSummaryByManager(employeePin);
                break;
            case "supervisor":
                ptpStatusSummaryList = repository.getPtpSummaryBySupervisor(employeePin);
                break;
            default:
                ptpStatusSummaryList = repository.getPtpSummaryByDealer(employeePin);
        }
        setAllPtpTypes(ptpStatusSummaryList);
        return ptpStatusSummaryList;
    }

    public List<PtpStatusSummary> getSamdCurrentMonthFollowUpSummmary(String designation, String employeePin) {
        List<PtpStatusSummary> ptpStatusSummaryList = new LinkedList<>();
        switch (designation.replaceAll(" ", "").toLowerCase()) {
            case "teamleader":
                ptpStatusSummaryList = repository.getPtpSummaryByTeamLeader(employeePin);
                break;
            case "manager":
                ptpStatusSummaryList = repository.getPtpSummaryByManager(employeePin);
                break;
            case "supervisor":
                ptpStatusSummaryList = repository.getPtpSummaryBySupervisor(employeePin);
                break;
            default:
                ptpStatusSummaryList = repository.getSamdPtpSummaryByDealer(employeePin);
        }
        setAllPtpTypes(ptpStatusSummaryList);
        return ptpStatusSummaryList;
    }

    /**
     * Populate ptp summary model list with default value summary model
     * if a business defined ptp status is missing from the query
     *
     * @param ptpStatusSummaryList
     */
    private void setAllPtpTypes(List<PtpStatusSummary> ptpStatusSummaryList) {

        List<String> ptpTypes = new ArrayList<String>(3) {{
            add("kept");
            add("broken");
            add("cured");
        }};

        for (PtpStatusSummary ptp : ptpStatusSummaryList)
            ptpTypes.remove(ptp.getStatus().toLowerCase());

        for (String ptpType : ptpTypes)
            ptpStatusSummaryList.add(new PtpStatusSummary(ptpType, "", 0L, 0D));

    }


    public LoanPtp findbyId(Long id) {
        return repository.findById(id).get();
    }

    public LoanPtp save(LoanPtp loanPtp) {
        boolean isNewEntity = true;
        LoanPtp oldEntity = new LoanPtp();
        if (loanPtp.getId() != null) {
            LoanPtp entity = repository.getOne(loanPtp.getId());
            BeanUtils.copyProperties(entity, oldEntity);

            isNewEntity = false;
        }

        repository.save(loanPtp);
        if (isNewEntity)
            auditTrailService.saveCreatedData("PTP", loanPtp);
        else
            auditTrailService.saveUpdatedData("PTP", oldEntity, loanPtp);
        return loanPtp;
    }

    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


    public List<DealerWisePtpSummary> getDealerWisePtpSummary(List<String> dealerPins) {
        return repository.getDealerWisePtpSummary(dealerPins)
                .stream().map(DealerWisePtpSummary::new).collect(Collectors.toList());
    }

    public List<LoanPtp> getLoanPtpByCustomerIdAndDateRange(String customerId, String startDate, String endDate) {
        List<LoanPtp>loanPtpList = repository.getLoanPtpByCustomerIdAndDateRange(customerId, startDate,endDate);
        return loanPtpList;
    }
}
