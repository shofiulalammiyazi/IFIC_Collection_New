package com.csinfotechbd.legal.report.datasheets.accountWiselegalBillExpenseReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountWiseLegalBillExpenseReportService {

    @Autowired
    private AccountWiseLegalBillExpenseReportRepository accountWiseLegalBillExpenseReportRepository;

    public List<AccountWiseLegalBillExpenseDto> searchData(String month){
        String previousMonth = this.getPreviousMonth(month);
        List<Tuple> reportData = accountWiseLegalBillExpenseReportRepository.findAllByMonth(month, previousMonth);
        List<AccountWiseLegalBillExpenseDto> finalizedData = new ArrayList<>();
        Integer index = 1;

        for (Tuple data : reportData){
            AccountWiseLegalBillExpenseDto dto = new AccountWiseLegalBillExpenseDto();

            dto.setSerial(index);
            index += 1;

            dto.setBranchCode(data.get("branchCode"));
            dto.setBranchName(data.get("branchName"));

            dto.setCifId(data.get("cifNo"));
            dto.setAccountNo(data.get("accountNo"));
            dto.setAccountName(data.get("accountName"));
            dto.setSegment(data.get("segment"));

            dto.setOutstanding(data.get("outstanding"));
            dto.setThisMonthRecoveryBeforeFiling(data.get("thisMonthRecoveryBeforeFiling"));
            dto.setThisMonthRecoveryAfterFiling(data.get("thisMonthRecoveryAfterFiling"));
            dto.setCumulativeRecovery(data.get("cumulativeRecovery"));

            dto.setDistrict(data.get("district"));
            dto.setCourtName(data.get("courtName"));

            dto.setLawyersId(data.get("lawyerId"));
            dto.setLawyersName(data.get("lawyerName"));
            dto.setLawyersMobileNo(data.get("lawyerMobileNo"));

            dto.setPlaintiffId(data.get("plainfiffId"));
            dto.setPlaintiffName(data.get("plaintiffName"));
            dto.setPlaintiffMobileNo(data.get("plaintiffMobileNo"));

            dto.setLawyersBillForTypeOfCase(data.get("lawyersBillForTypeOfCase"));
            dto.setCaseStatus(data.get("caseStatus"));

            dto.setPreviousMonthLawyersBillAmount(data.get("previousMonthLawyersBillAmount"));
            dto.setCurrentMonthLawyersBillAmount(data.get("currentMonthLawyersBillAmount"));
            dto.setTotalLawyersBillAmount(data.get("totalLawyersBillAmount"));

            dto.setCurrentMonthOthersBillAmount(data.get("currentMonthOthersBillAmount"));
            dto.setTotalOthersBillAmount(data.get("totalOthersBillAmount"));
            dto.setCumulativeLegalBillAmount(data.get("cumulativeLegalBillAmount"));

            dto.setCurrentMonthLegalBillRecovery(data.get("currentMonthLegalBillRecovery"));
            dto.setCumulativeLegalBillRecovery(data.get("cumulativeLegalBillRecovery"));
            dto.setClStatus(data.get("clStatus"));

            dto.setRecoveryBeforeCaseFiling(data.get("recoveryBeforeCaseFiling"));
            dto.setRecoveryAfterCaseFiling(data.get("recoveryAfterCaseFiling"));

            finalizedData.add(dto);
        }

        return finalizedData;
    }

    public String getPreviousMonth (String month) {
        Integer prevMonth = Integer.valueOf(month.substring(5, 7))-1;
        Integer prevYear = Integer.valueOf(month.substring(0, 4));

        prevYear = prevMonth == 0 ? prevYear - 1 : prevYear;
        prevMonth = prevMonth == 0 ? 12 : prevMonth;

        return prevYear+"-"+(prevMonth < 10 ? "0"+prevMonth : prevMonth);
    }
}
