package com.csinfotechbd.legal.report.datasheets.arthorinSuitAndArthorinExecutionCaseMonitoringReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArthorinSuitArthorinExecutionCaseMonitoringReportService {

    @Autowired
    private ArthorinSuitArthorinExecutionCaseMonitoringReportRepository arthorinSuitArthorinExecutionCaseMonitoringReportRepository;

    public List<ArthorinSuitArthorinExecutionCaseMonitoringDto> searchData(String branch){
        List<Tuple> reportData = arthorinSuitArthorinExecutionCaseMonitoringReportRepository.findAllByBranch(branch);
        List<ArthorinSuitArthorinExecutionCaseMonitoringDto> finalizedData = new ArrayList<>();
        Integer index = 1;

        for (Tuple data : reportData){
            ArthorinSuitArthorinExecutionCaseMonitoringDto caseMonitoringDto = new ArthorinSuitArthorinExecutionCaseMonitoringDto();

            caseMonitoringDto.setSerial(index);
            index += 1;

            caseMonitoringDto.setBranchCode(data.get("branchCode"));
            caseMonitoringDto.setBranchName(data.get("branchName"));
            caseMonitoringDto.setLdNo(data.get("ldNo"));
            caseMonitoringDto.setDivision(data.get("division"));

            caseMonitoringDto.setCifNumber(data.get("cifNumber"));
            caseMonitoringDto.setAccountNo(data.get("accountNo"));
            caseMonitoringDto.setAccountName(data.get("accountName"));

            caseMonitoringDto.setDefendantName(data.get("defendantName"));
            caseMonitoringDto.setDefendantMobile(data.get("defendantMobile"));
            caseMonitoringDto.setBusinessSegment(data.get("businessSegment"));
            caseMonitoringDto.setOutstanding(data.get("outstanding"));
            caseMonitoringDto.setClStatus(data.get("clStatus"));

            caseMonitoringDto.setArtharinSuitNumber(data.get("artharinSuitNumber"));
            caseMonitoringDto.setArtharinFilingDate(data.get("artharinFilingDate"));
            caseMonitoringDto.setArtharinSuitValue(data.get("artharinSuitValue"));

            caseMonitoringDto.setExecutionCaseNumber(data.get("executionCaseNumber"));
            caseMonitoringDto.setExecutionFilingDate(data.get("executionFilingDate"));
            caseMonitoringDto.setExecutionCaseValue(data.get("executionCaseValue"));

            caseMonitoringDto.setPreviousDate("-");
            caseMonitoringDto.setCourseOfAction(data.get("courseOfAction"));
            caseMonitoringDto.setNextDate(data.get("nextDate"));

            caseMonitoringDto.setCourtsName(data.get("courtName"));
            caseMonitoringDto.setPlaintiffsName(data.get("plaintiffsName"));
            caseMonitoringDto.setPlaintiffsCellNumber(data.get("plaintiffPhoneNo"));

            caseMonitoringDto.setDefendantsName(data.get("defendantsName"));
            caseMonitoringDto.setDefendantsCellNumber(data.get("defendantsNumber"));
            caseMonitoringDto.setLawyersName(data.get("lawyersName"));
            caseMonitoringDto.setLawyersCellNumber(data.get("layersCellNumber"));

            caseMonitoringDto.setNiActCaseNumber(data.get("niActCaseNumber"));
            caseMonitoringDto.setWritNumber(data.get("writNumber"));
            caseMonitoringDto.setCriminalCase("-");
            caseMonitoringDto.setOtherCase(data.get("otherCase"));

            caseMonitoringDto.setAgainstBank("-");
            caseMonitoringDto.setWrittenOffStatus(data.get("writenOfStatus"));

            caseMonitoringDto.setTotalRecoveryAmount(data.get("totalRecoveryAmount"));
            caseMonitoringDto.setLegalExpense(data.get("legalExpense"));
            caseMonitoringDto.setUnderSolenama(data.get("underSolenama"));
            caseMonitoringDto.setCollateralSecurity("-");
            caseMonitoringDto.setMarketValue(data.get("marketValue"));

            caseMonitoringDto.setForcedCellValue(data.get("forcedSaleValue"));
            caseMonitoringDto.setAssessedBy(data.get("assessedBy"));
            caseMonitoringDto.setStatus(data.get("status"));
            caseMonitoringDto.setRemarks(data.get("remarks"));

            finalizedData.add(caseMonitoringDto);
        }

        return finalizedData;
    }
}
