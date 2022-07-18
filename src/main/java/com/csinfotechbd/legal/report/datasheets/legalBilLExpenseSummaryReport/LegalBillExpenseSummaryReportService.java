package com.csinfotechbd.legal.report.datasheets.legalBilLExpenseSummaryReport;


import com.csinfotechbd.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class LegalBillExpenseSummaryReportService {

    @Autowired
    private LegalBillExpenseSummaryRepository legalBillExpenseSummaryRepository;
    @Autowired
    private DateUtils dateUtils;

    public List<LegalBillExpenseSummaryReportDto> searchBranchLawarWiseData(String date, String branchName, String lawyerName) {
        String previousMonth = this.getPreviousMonth(date);
        List<Tuple> expenseList = legalBillExpenseSummaryRepository.branchAndLawerWiseLegalBillExpense(date, branchName, lawyerName, previousMonth);

        int index = 1;
        List<LegalBillExpenseSummaryReportDto> legalBillExpenseDtoList = new ArrayList<>();
        for (Tuple tuple : expenseList) {
            LegalBillExpenseSummaryReportDto legalDto = new LegalBillExpenseSummaryReportDto();
            legalDto.setSerial(index);
            index +=1;
            legalDto.setBranchCode(tuple.get("branchCode"));
            legalDto.setBranchName(tuple.get("branchName"));
            legalDto.setLawarId(tuple.get("lawarId"));
            legalDto.setLawarName(tuple.get("lawarName"));
            legalDto.setDistrict(tuple.get("district"));
            legalDto.setCourtName(tuple.get("courtName"));
            legalDto.setLawyerBillTypeCase(tuple.get("lawyerBillTypeCase"));
            legalDto.setPreviousMonthLawyersBillAmount(tuple.get("previousMonthLawyersBillAmount"));
            legalDto.setCurrentMonthLawyersBillAmount(tuple.get("currentMonthLawyersBillAmount"));
            legalDto.setTotalLawyersBillAmount(tuple.get("totalLawyersBillAmount"));
            legalDto.setCurrentMonthOthersBillAmount(tuple.get("currentMonthOthersBillAmount"));
            legalDto.setTotalOthersBillAmount(tuple.get("totalOthersBillAmount"));
            legalDto.setCumulativeLegalBillAmount(tuple.get("cumulativeLegalBillAmount"));
            legalDto.setCurrentMonthLegalBillRecovery(tuple.get("currentMonthLegalBillRecovery"));
            legalDto.setRecoveryBeforeCaseFiling(tuple.get("recoveryBeforeCaseFiling"));
            legalDto.setRecoveryAfterCaseFiling(tuple.get("recoveryAfterCaseFiling"));

            legalBillExpenseDtoList.add(legalDto);

        }
        return legalBillExpenseDtoList;
    }

    public List<LegalBillExpenseSummaryReportDto> searchSegmentWiseData(String segment, String month) {
        String previousMonth = this.getPreviousMonth(month);
        List<Tuple>segmentWiseList = legalBillExpenseSummaryRepository.segmentWishLegalBillExpense(segment, previousMonth);
        List<LegalBillExpenseSummaryReportDto> segmentLegalBillExpenseDtoList = new ArrayList<>();
        for (Tuple tuple: segmentWiseList){
            LegalBillExpenseSummaryReportDto legalSegmentDto = new LegalBillExpenseSummaryReportDto();
            legalSegmentDto.setSegment(tuple.get("segment"));
            legalSegmentDto.setDateOfFiling(tuple.get("dateOfFiling"));
            legalSegmentDto.setTotalWOffAmt(tuple.get("totalWOffAmt"));
            legalSegmentDto.setTotalWOffRecovery(tuple.get("totalWOffRecovery"));
            legalSegmentDto.setTotalRecoveryBeoreFilling(tuple.get("totalRecoveryBeoreFilling"));
            legalSegmentDto.setTotalRecoveryAfterFilling(tuple.get("TotalRecoveryAfterFilling"));
            legalSegmentDto.setOutstanding(tuple.get("outstanding"));
            legalSegmentDto.setLawyerBillTypeCase(tuple.get("lawyerBillTypeCase"));
            legalSegmentDto.setPreviousMonthLawyersBillAmount(tuple.get("previousMonthLawyersBillAmount"));
            legalSegmentDto.setCurrentMonthLawyersBillAmount(tuple.get("currentMonthLawyersBillAmount"));
            legalSegmentDto.setTotalLawyersBillAmount(tuple.get("totalLawyersBillAmount"));
            legalSegmentDto.setCurrentMonthOthersBillAmount(tuple.get("currentMonthOthersBillAmount"));
            legalSegmentDto.setTotalOthersBillAmount(tuple.get("totalOthersBillAmount"));
            legalSegmentDto.setCumulativeLegalBillAmount(tuple.get("cumulativeLegalBillAmount"));
            legalSegmentDto.setCurrentMonthLegalBillRecovery(tuple.get("currentMonthLegalBillRecovery"));
            legalSegmentDto.setRecoveryBeforeCaseFiling(tuple.get("recoveryBeforeCaseFiling"));
            legalSegmentDto.setRecoveryAfterCaseFiling(tuple.get("recoveryAfterCaseFiling"));

            segmentLegalBillExpenseDtoList.add(legalSegmentDto);

        }
        return segmentLegalBillExpenseDtoList;
    }


    public String getPreviousMonth (String month) {
        Integer prevMonth = Integer.valueOf(month.substring(5, 7))-1;
        Integer prevYear = Integer.valueOf(month.substring(0, 4));

        prevYear = prevMonth == 0 ? prevYear - 1 : prevYear;
        prevMonth = prevMonth == 0 ? 12 : prevMonth;

        return prevYear+"-"+(prevMonth < 10 ? "0"+prevMonth : prevMonth);
    }


    public LegalBillExpenseSummaryReportDto getTotal(List<LegalBillExpenseSummaryReportDto> segmentList){
        LegalBillExpenseSummaryReportDto segmentWiseTotalDto = new LegalBillExpenseSummaryReportDto();
        double totalWOfAmt= 0.0;
        double totalWOffRecovery= 0.0;
        double totalRecoveryBeoreFilling= 0.0;
        double totalRecoveryAfterFilling= 0.0;
        double outstanding= 0.0;
        double lawyerBillTypeCase= 0.0;
        double previousMonthLawyerBillAmt= 0.0;
        double currentMonthLawyerBillAmount= 0.0;
        double totalLawyerBillAmount= 0.0;
        double currentMonthOthersBillAmount= 0.0;
        double totalOthersBillAmount= 0.0;
        double cumulativeLegalBillExpenseAmt= 0.0;
        double currentMonthLegalBillExpRecovery= 0.0;
        double previousMonthLegalBillExpRecovery= 0.0;
        double cumulativeLegalBillExpRecovery= 0.0;

        for (LegalBillExpenseSummaryReportDto dto: segmentList){

             totalWOfAmt +=  ((Number) dto.getTotalWOffAmt()).doubleValue();
            segmentWiseTotalDto.setTotalWOffAmt(totalWOfAmt);

            totalWOffRecovery += ((Number) dto.getTotalWOffRecovery()).doubleValue();
            segmentWiseTotalDto.setTotalWOffRecovery(totalWOffRecovery);

            totalRecoveryBeoreFilling += ((Number) dto.getTotalRecoveryBeoreFilling()).doubleValue();
            segmentWiseTotalDto.setTotalRecoveryBeoreFilling(totalRecoveryBeoreFilling);

            totalRecoveryAfterFilling += ((Number) dto.getTotalRecoveryAfterFilling()).doubleValue();
            segmentWiseTotalDto.setTotalRecoveryAfterFilling(totalRecoveryAfterFilling);

            outstanding += ((Number) dto.getOutstanding()).doubleValue();
            segmentWiseTotalDto.setOutstanding(outstanding);
            previousMonthLawyerBillAmt += ((Number) dto.getPreviousMonthLawyersBillAmount()).doubleValue();
            segmentWiseTotalDto.setPreviousMonthLawyersBillAmount(previousMonthLawyerBillAmt);

            currentMonthLawyerBillAmount += ((Number) dto.getCurrentMonthLawyersBillAmount()).doubleValue();
            segmentWiseTotalDto.setCurrentMonthLawyersBillAmount(currentMonthLawyerBillAmount);

            totalLawyerBillAmount += ((Number) dto.getTotalLawyersBillAmount()).doubleValue();
            segmentWiseTotalDto.setTotalLawyersBillAmount(totalLawyerBillAmount);

            currentMonthOthersBillAmount += ((Number) dto.getCurrentMonthOthersBillAmount()).doubleValue();
            segmentWiseTotalDto.setCurrentMonthOthersBillAmount(currentMonthOthersBillAmount);

            totalOthersBillAmount += ((Number) dto.getTotalOthersBillAmount()).doubleValue();
            segmentWiseTotalDto.setTotalOthersBillAmount(totalOthersBillAmount);

            cumulativeLegalBillExpenseAmt += ((Number) dto.getCumulativeLegalBillAmount()).doubleValue();
            segmentWiseTotalDto.setCumulativeLegalBillAmount(cumulativeLegalBillExpenseAmt);

            currentMonthLegalBillExpRecovery +=  ((Number) dto.getCurrentMonthLegalBillRecovery()).doubleValue();
            segmentWiseTotalDto.setCurrentMonthLegalBillRecovery(currentMonthLegalBillExpRecovery);

            previousMonthLegalBillExpRecovery += ((Number) dto.getRecoveryBeforeCaseFiling()).doubleValue();
            segmentWiseTotalDto.setRecoveryBeforeCaseFiling(previousMonthLegalBillExpRecovery);

            cumulativeLegalBillExpRecovery += ((Number) dto.getRecoveryAfterCaseFiling()).doubleValue();
            segmentWiseTotalDto.setRecoveryAfterCaseFiling(cumulativeLegalBillExpRecovery);

        }
        return segmentWiseTotalDto;
    }


    public List<String> findBranchName() {
        return legalBillExpenseSummaryRepository.getBranchName();
    }

    public List<String> findLawyerList() {
        return legalBillExpenseSummaryRepository.getLawyerName();
    }
}
