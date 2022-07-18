package com.csinfotechbd.legal.report.datasheets.writFileMonitoringReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class WritFileMonitoringReportService {

    @Autowired
    private WritFileMonitoringReportRepository writFileMonitoringReportRepository;

    public List<WritFileMonitoringReportDto> searchData(String branch) {
        List<Tuple> reportData = writFileMonitoringReportRepository.findAllByBranch(branch);
        List<WritFileMonitoringReportDto> writFiles = new ArrayList<>();
        Integer index = 1;

        for (Tuple data : reportData) {
            WritFileMonitoringReportDto file = new WritFileMonitoringReportDto();

            file.setSerial(index);
            index += 1;

            file.setBranchCode(data.get("branchCode"));
            file.setBranchName(data.get("branchName"));

            file.setCif(data.get("cif"));
            file.setAccountNo(data.get("accountNo"));
            file.setAccountName(data.get("accountName"));

            file.setDefendantName(data.get("defendantName"));
            file.setDefendantMobile(data.get("defendantMobile"));

            file.setSegment(data.get("segment"));
            file.setPetitionersName(data.get("petitionerName"));

            file.setWritNumber(data.get("writNumber"));
            file.setDateOfFiling(data.get("dateOfFiling"));

            file.setNatureOfWrit("-");
            file.setRespondentsName(data.get("respondantsName"));

            file.setAmountInvolved(data.get("amountInvolved"));
            file.setSubjectMatterOfWrit(data.get("subjectMatterOfWrit"));

            file.setFirstOrderDate(data.get("firstOrderDate"));
            file.setByWhomFiled(data.get("byWhomFiled"));

            file.setLawyersName(data.get("lawyers"));
            file.setLawyersCellNumber(data.get("layersCellNum"));

            file.setCourtsName(data.get("courtName"));
            file.setLegalExpense(data.get("legalExpense"));

            file.setArtharinSuitNo(data.get("artharinSuitNo"));
            file.setNiActCaseNo(data.get("niActCaseNumber"));

            file.setOtherSuitCase(data.get("otherSuitCase"));
            file.setAgainstBank("-");

            file.setArExSuitValue(data.get("arExSuitValue"));
            file.setNiActChequeValue(data.get("niActChequeAmount"));

            file.setArtharinSuitStayed("-");
            file.setNiActCaseStayed("-");
            file.setStatus(data.get("caseStatus"));
            file.setRemarks("-");

            writFiles.add(file);
        }

        return writFiles;
    }
}
