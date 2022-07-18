package com.csinfotechbd.legal.report.datasheets.judgementAwardedAdjustedAccountReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class JudgementAwardedAdjustedAccountReportService {

    @Autowired
    private JudgementAwardedAdjustmentAccountReportRepository judgementAwardedAdjustmentAccountReportRepository;

    public List<JudgementAwardedAdjustedAccountDto> searchData(String month){
        List<Tuple> reportData = judgementAwardedAdjustmentAccountReportRepository.findAllByMonth(month);
        List<JudgementAwardedAdjustedAccountDto> finalizedData = new ArrayList<>();
        Integer index = 1;

        for (Tuple data: reportData){
            JudgementAwardedAdjustedAccountDto awardedAccount = new JudgementAwardedAdjustedAccountDto();

            awardedAccount.setSerial(index);
            index += 1;
            awardedAccount.setBranchCode(data.get("branchCode"));
            awardedAccount.setBranchName(data.get("branchName"));

            awardedAccount.setLdNumber(data.get("ldNumber"));
            awardedAccount.setCif(data.get("cif"));

            awardedAccount.setAccountNo(data.get("accountNo"));
            awardedAccount.setAccountName(data.get("accountName"));

            awardedAccount.setDefendantName(data.get("defendantName"));
            awardedAccount.setDefendantMobile(data.get("defendantMobile"));

            awardedAccount.setSegment(data.get("segment"));
            awardedAccount.setPlaintiffName(data.get("plaintiffName"));
            awardedAccount.setPlaintiffMobile(data.get("plaintiffMobile"));

            awardedAccount.setLawyerName(data.get("lawyerName"));
            awardedAccount.setLawyerMobileNo(data.get("lawyerMobile"));
            awardedAccount.setDistrictName(data.get("districtName"));

            awardedAccount.setCourtName(data.get("courtName"));
            awardedAccount.setCaseType(data.get("caseType"));

            awardedAccount.setSuitNumber(data.get("suitNumber"));
            awardedAccount.setDateOfFiling(data.get("dateOfFiling"));

            awardedAccount.setCaseAmount(data.get("caseAmount"));
            awardedAccount.setClStatus(data.get("clStatus"));

            awardedAccount.setTotalRecovery(data.get("totalRecovery"));
            awardedAccount.setOutstanding(data.get("outstanding"));
            awardedAccount.setCaseStatus(data.get("caseStatus"));
            awardedAccount.setAdjustedDate("-");
            awardedAccount.setRemarks("-");

            finalizedData.add(awardedAccount);
        }

        return finalizedData;
    }
}
