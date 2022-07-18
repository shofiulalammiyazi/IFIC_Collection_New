package com.csinfotechbd.legal.report.datasheets.highCourtSuitCaseMonitoringReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class HighCourtSuitCaseMonitoringService {

    @Autowired
    private HighCourtSuitCaseMonitoringRepository highCourtSuitCaseMonitoringRepository;


    public List<HighCourtSuitCaseMonitoringDto> findReportData() {
        List<Tuple> caseList=highCourtSuitCaseMonitoringRepository.findHighCourtSuitReportData();
        int index=1;
        List<HighCourtSuitCaseMonitoringDto>highCourtSuitCaseMonitoringDtoList = new ArrayList<>();
        for (Tuple data: caseList){
            HighCourtSuitCaseMonitoringDto newCase = new HighCourtSuitCaseMonitoringDto();
            newCase.setSerial(index);
            index += 1;
            newCase.setBranchCode(data.get("branchCode"));
            newCase.setBranchName(data.get("branchName"));
            newCase.setLdNumber(data.get("ldNumber"));
            newCase.setDivision(data.get("division"));
            newCase.setCifNumber(data.get("cifNumber"));
            newCase.setAccountNumber(data.get("accountNo"));
//            newCase.setAccountName(data.get("accountName"));
            newCase.setDefendantName(data.get("defendantName"));
            newCase.setDefendantNumber(data.get("defendantNumber"));
            newCase.setSegment(data.get("segment"));
            newCase.setOutstanding(data.get("outstanding"));
            newCase.setClStatus(data.get("clStatus"));
            newCase.setPetitionerName(data.get("petitionerName"));
            newCase.setOppsitePartyName(data.get("oppsitePartyName"));
//            newCase.setCaseName(data.get("caseName"));
            newCase.setCaseNumber(data.get("caseNumber"));
            newCase.setDateOfFiling(data.get("dateOfFiling"));
            newCase.setSubjectMatter(data.get("subjectMatter"));
            newCase.setByWhomFiled(data.get("byWhomFiled"));
            newCase.setLegalExpense(data.get("legalExpense"));
            newCase.setFirstOrderDate(data.get("firstOrderDate"));
            newCase.setAmountInvolved(data.get("amountInvolved"));
            newCase.setSubjectMatterOfCase(data.get("subjectMatterOfCase"));
            newCase.setHearingDate(data.get("hearingDate"));
            newCase.setCourseOfName(data.get("courseOfName"));
            newCase.setLawyerName(data.get("lawyerName"));
            newCase.setCellNumber(data.get("cellNumber"));
            newCase.setNameOfOfficer(data.get("nameOfOfficer"));
            newCase.setLd(data.get("ld"));
            newCase.setCourtName(data.get("courtName"));
            newCase.setStatus(data.get("status"));
            newCase.setRemarks(data.get("remarks"));


            highCourtSuitCaseMonitoringDtoList.add(newCase);
        }
     return highCourtSuitCaseMonitoringDtoList;
    }
}
