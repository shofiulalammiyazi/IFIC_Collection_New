package com.csinfotechbd.legal.report.datasheets.supremeCourtBangladeshCaseReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupremeCourtBangladeshCaseReportService {

    @Autowired
    private SupremeCourtBangladeshCaseReportRepository supremeCourtBangladeshCaseReportRepository;

    public List<SupremeCourtBangladeshCaseReportDto> searchData(String caseFiledSubType, String reportName){
        List<Tuple> reportData = null;

        reportData = reportName.equals("Type of Case") ? supremeCourtBangladeshCaseReportRepository.findAllCaseTypeWise(caseFiledSubType) :
                        reportName.equals("Course of Action") ? supremeCourtBangladeshCaseReportRepository.findAllCourseOfActionWise(caseFiledSubType) :
                        reportName.equals("Case Status") ? supremeCourtBangladeshCaseReportRepository.fillAllCaseStatusWise(caseFiledSubType) : new ArrayList<>();
        List<SupremeCourtBangladeshCaseReportDto> finalizedData = new ArrayList<>();

        for (Tuple data : reportData) {
            SupremeCourtBangladeshCaseReportDto dto = new SupremeCourtBangladeshCaseReportDto();
            dto.setCaseLabel(data.get("caseLabel"));
            dto.setCourseOfActionOthers(reportName.equals("Course of Action") ? data.get("courseOfActionOthers") : "");
            dto.setNameOfReport(reportName);

            dto.setTotalAc(data.get("totalAccNo"));
            dto.setTotalWOffAmt(data.get("totalWOffAmt"));
            dto.setTotalRecovery(data.get("totalRecovery"));
            dto.setTotalFillingAmt(data.get("totalFillingAmt"));
            dto.setTotalSettledAmt(data.get("totalSettledAmt"));
            dto.setTotalOutstanding(data.get("totalOutStanding"));

            dto.setCbdAc(data.get("cbdAccNo"));
            dto.setCbdWOffAmt(data.get("cbdWOffAmt"));
            dto.setCbdTotalRecovery(data.get("cbdTotalRecovery"));
            dto.setCbdFillingAmt(data.get("cbdFillingAmt"));
            dto.setCbdSettledAmt(data.get("cbdSettledAmt"));
            dto.setCbdOutstanding(data.get("cbdOutStanding"));

            dto.setSmeAc(data.get("smeAccNo"));
            dto.setSmeWOffAmt(data.get("smeWOffAmt"));
            dto.setSmeTotalRecovery(data.get("smeTotalRecovery"));
            dto.setSmeFillingAmt(data.get("smeFillingAmt"));
            dto.setSmeSettledAmt(data.get("smeSettledAmt"));
            dto.setSmeOutstanding(data.get("smeOutStanding"));

            dto.setRbdAc(data.get("rbdAccNo"));
            dto.setRbdWOffAmt(data.get("rbdWOffAmt"));
            dto.setRbdTotalRecovery(data.get("rbdTotalRecovery"));
            dto.setRbdFillingAmt(data.get("rbdFillingAmt"));
            dto.setRbdSettledAmt(data.get("rbdSettledAmt"));
            dto.setRbdOutstanding(data.get("rbdOutStanding"));

            dto.setCardAc(data.get("cardAccNo"));
            dto.setCardWOffAmt(data.get("cardWOffAmt"));
            dto.setCardTotalRecovery(data.get("cardTotalRecovery"));
            dto.setCardFillingAmt(data.get("cardFillingAmt"));
            dto.setCardSettledAmt(data.get("cardSettledAmt"));
            dto.setCardOutstanding(data.get("cardOutStanding"));

            finalizedData.add(dto);
        }

        return finalizedData;
    }
}
