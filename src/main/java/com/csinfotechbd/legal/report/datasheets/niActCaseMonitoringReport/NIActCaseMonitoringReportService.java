package com.csinfotechbd.legal.report.datasheets.niActCaseMonitoringReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class NIActCaseMonitoringReportService {

    @Autowired
    private NIActCaseMonitoringReportRepository niActCaseMonitoringReportRepository;

    public List<NIActCaseMonitoringDto> searchData(String branch){
        List<Tuple> reportData = niActCaseMonitoringReportRepository.findAllByBranch(branch);
        List<NIActCaseMonitoringDto> finalizedData = new ArrayList<>();
        Integer index = 1;

        for (Tuple data : reportData){
            NIActCaseMonitoringDto niActCaseMonitoringDto = new NIActCaseMonitoringDto();

            niActCaseMonitoringDto.setSerial(index);
            index += 1;

            niActCaseMonitoringDto.setBranchCode(data.get("branchCode"));
            niActCaseMonitoringDto.setBranchName(data.get("branchName"));

            niActCaseMonitoringDto.setLdNo(data.get("ldNo"));
            niActCaseMonitoringDto.setDivision(data.get("division"));

            niActCaseMonitoringDto.setCifNumber(data.get("cifNumber"));
            niActCaseMonitoringDto.setAccountNo(data.get("accountNo"));
            niActCaseMonitoringDto.setAccountName(data.get("accountName"));

            niActCaseMonitoringDto.setDefendantName(data.get("defendantName"));
            niActCaseMonitoringDto.setDefendantMobile(data.get("defendantMobile"));
            niActCaseMonitoringDto.setBusinessSegment(data.get("businessSegment"));
            niActCaseMonitoringDto.setOutstanding(data.get("outstanding"));

            niActCaseMonitoringDto.setClStatus(data.get("clStatus"));
            niActCaseMonitoringDto.setNiActCaseNumber(data.get("niActCaseNumer"));
            niActCaseMonitoringDto.setFilingDate(data.get("filingDate"));
            niActCaseMonitoringDto.setCaseAmount(data.get("caseAmount"));

            niActCaseMonitoringDto.setPreviousDate("-");
            niActCaseMonitoringDto.setCourseOfAction(data.get("courseOfAction"));
            niActCaseMonitoringDto.setNextDate(data.get("nextDate"));
            niActCaseMonitoringDto.setCourtsName(data.get("courtName"));

            niActCaseMonitoringDto.setPetitionersName(data.get("petitionersName"));
            niActCaseMonitoringDto.setPetitionersCellNumber("-");
            niActCaseMonitoringDto.setAccusedName(data.get("accusedName"));
            niActCaseMonitoringDto.setAccusedCellNumber(data.get("accusedCellNumber"));

            niActCaseMonitoringDto.setLawyersName(data.get("lawyersName"));
            niActCaseMonitoringDto.setLawyersCellNumber(data.get("layersCellNumber"));

            niActCaseMonitoringDto.setArtharinExecutionCaseNumber(data.get("artharinExecutionCaseNumber"));
            niActCaseMonitoringDto.setWritNumber(data.get("writNumber"));
            niActCaseMonitoringDto.setCriminalCase("-");

            niActCaseMonitoringDto.setOtherCase(data.get("otherCase"));
            niActCaseMonitoringDto.setAgainstBank("-");

            niActCaseMonitoringDto.setWrittenOffStatus(data.get("writenOfStatus"));
            niActCaseMonitoringDto.setTotalRecoveryAmount(data.get("totalRecoveryAmount"));

            niActCaseMonitoringDto.setLegalExpense(data.get("legalExpense"));
            niActCaseMonitoringDto.setCollateralSecurity("-");

            niActCaseMonitoringDto.setMarketValue(data.get("marketValue"));
            niActCaseMonitoringDto.setForcedCellValue(data.get("forcedSaleValue"));
            niActCaseMonitoringDto.setAssessedBy(data.get("assessedBy"));
            niActCaseMonitoringDto.setStatus(data.get("status"));
            niActCaseMonitoringDto.setRemarks(data.get("remarks"));

            finalizedData.add(niActCaseMonitoringDto);
        }

        return  finalizedData;
    }
}
