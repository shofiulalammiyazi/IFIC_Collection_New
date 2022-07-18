package com.csinfotechbd.legal.report.datasheets.pendingCourtCasesMonitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaseMonitoringPendingCourtCasesReportService {

    @Autowired
    private CaseMonitoringPendingCourtCasesReportRepository caseMonitoringPendingCourtCasesReportRepository;

    public List<CaseMonitoringPendingCourtCasesReportDto> searchData(){
        List<CaseMonitoringPendingCourtCasesReportDto> pendingCases = new ArrayList<>();
        Object[][] allData = caseMonitoringPendingCourtCasesReportRepository.findAllByCaseType();
        Integer serial = 1;
        for (Object[] data : allData){
            CaseMonitoringPendingCourtCasesReportDto caseMonitor = new CaseMonitoringPendingCourtCasesReportDto();

            caseMonitor.setSerial(serial);
            serial += 1;

            caseMonitor.setBranchCode("-");
            caseMonitor.setBranchName(data[0]);

            caseMonitor.setNosOfARS(data[1]);
            caseMonitor.setArtharinRelatedSuitValue(data[2]);

            caseMonitor.setNosOfExCase(data[3]);
            caseMonitor.setExCaseRelatedSuitValue(data[4]);

            caseMonitor.setTotalNumberOfArtharinExecutionSuit(data[5]);
            caseMonitor.setTotalSuitValue(data[6]);

            caseMonitor.setNosOfNIAct(data[7]);
            caseMonitor.setChequeAmount(data[8]);

            caseMonitor.setNosOfMoneySuitAgainstBank(data[9]);
            caseMonitor.setAmountAgainstBank(data[10]);

            caseMonitor.setNumberOfWritPetition(data[11]);
            caseMonitor.setAmount(data[12]);
            caseMonitor.setRemarks("-");

            pendingCases.add(caseMonitor);
        }

        return pendingCases;
    }
}
