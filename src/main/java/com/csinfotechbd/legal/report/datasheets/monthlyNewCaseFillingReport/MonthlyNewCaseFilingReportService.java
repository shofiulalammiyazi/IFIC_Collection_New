package com.csinfotechbd.legal.report.datasheets.monthlyNewCaseFillingReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyNewCaseFilingReportService {

    @Autowired
    private MonthlyNewCaseFilingReportRepository monthlyNewCaseFilingReportRepository;

    public List<MonthlyNewCaseFilingDto> searchData(String month) {
        List<Tuple> reportData = monthlyNewCaseFilingReportRepository.findAllByMonth(month);
        List<MonthlyNewCaseFilingDto> finalizedData = new ArrayList<>();
        Integer index = 1;
        for (Tuple data: reportData){
            MonthlyNewCaseFilingDto newCase = new MonthlyNewCaseFilingDto();

            newCase.setSerial(index);
            index += 1;
            newCase.setBranchCode(data.get("branchCode"));
            newCase.setBranchName(data.get("branchName"));

            newCase.setLdNumber(data.get("ldNumber"));
            newCase.setCif(data.get("cif"));

            newCase.setAccountNo(data.get("accountNo"));
            newCase.setAccountName(data.get("accountName"));

            newCase.setDefendantName(data.get("defendantName"));
            newCase.setDefendantMobile(data.get("defendantMobile"));

            newCase.setSegment(data.get("segment"));
            newCase.setPlaintiffName(data.get("plaintiffName"));
            newCase.setPlaintiffMobile(data.get("plaintiffMobile"));

            newCase.setLawyerName(data.get("lawyerName"));
            newCase.setLawyerMobileNo(data.get("lawyerMobile"));
            newCase.setDistrictName(data.get("districtName"));

            newCase.setCourtName(data.get("courtName"));
            newCase.setCaseType(data.get("caseType"));

            newCase.setSuitNumber(data.get("suitNumber"));
            newCase.setDateOfFiling(data.get("dateOfFiling"));

            newCase.setCaseAmount(data.get("caseAmount"));
            newCase.setClStatus(data.get("clStatus"));

            newCase.setTotalRecovery(data.get("totalRecovery"));
            newCase.setOutstanding(data.get("outstanding"));
            newCase.setCaseStatus(data.get("caseStatus"));
            newCase.setRemarks("-");

            finalizedData.add(newCase);
        }

        return finalizedData;
    }
}
