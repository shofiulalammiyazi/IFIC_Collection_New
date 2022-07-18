package com.csinfotechbd.reports.retail.loan.clReportMonthBasis;

import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanEntity;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanService;
import com.csinfotechbd.dataStore.loanListing.LoanListingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class MonthBasisCLReportService {

    private MonthBasisCLReportRepository monthBasisCLReportRepository;
    private LoanListingService loanListingService;
    private PARaccountRuleLoanService paRaccountRuleLoanService;

    public MonthBasisCLReportViewModel searchData(String month){
        String previousMonth = this.getPreviousMonth(month);
        MonthBasisCLReportViewModel reportData = new MonthBasisCLReportViewModel();

        List<MonthBasisCLReportDto> currtMonthBasisDataList = loanListingService.findCurrentMonthBasisReportData(month);
        List<MonthBasisCLReportDto> preMonthBasisCLReportDtoList = loanListingService.findPreviousMonthBasisReportData(previousMonth);

        double maxBucket = paRaccountRuleLoanService.getMaxBucket();

        MonthBasisCLReportViewModel monthBasisCLReportViewModel = new MonthBasisCLReportViewModel();

        double totalOs = 0;
        int totalAcc = 0;
        double blDfSsGroup = 0;
        double smaStGroup = 0;

        double previousMnthOS = 0;

        double currentParAmt = 0;
        double prevParAmt = 0;

        for (MonthBasisCLReportDto dto: currtMonthBasisDataList){

            if(dto.getStatus().equals("BL")){
                totalOs += ((Number) dto.getOutstanding()).doubleValue();
                blDfSsGroup += ((Number) dto.getOutstanding()).doubleValue();
                totalAcc += ((Number) dto.getNoOfAcc()).intValue();
                monthBasisCLReportViewModel.setBlOutstanding(((Number) dto.getOutstanding()).doubleValue());
                monthBasisCLReportViewModel.setBlNoAcc(((Number) dto.getNoOfAcc()).intValue());
            }
            if(dto.getStatus().equals("DF")){
                totalOs += ((Number) dto.getOutstanding()).doubleValue();
                blDfSsGroup += ((Number) dto.getOutstanding()).doubleValue();
                totalAcc += ((Number) dto.getNoOfAcc()).intValue();
                monthBasisCLReportViewModel.setDfOutstanding(((Number) dto.getOutstanding()).doubleValue());
                monthBasisCLReportViewModel.setDfNoAcc(((Number) dto.getNoOfAcc()).intValue());
            }
            if (dto.getStatus().equals("SS")){
                totalOs += ((Number)dto.getOutstanding()).doubleValue();
                blDfSsGroup += ((Number)dto.getOutstanding()).doubleValue();
                totalAcc += ((Number)dto.getOutstanding()).doubleValue();
                monthBasisCLReportViewModel.setSsOutstanding(((Number)dto.getOutstanding()).doubleValue());
                monthBasisCLReportViewModel.setSsNoAcc(((Number) dto.getNoOfAcc()).intValue());
            }
            if (dto.getStatus().equals("SMA")){
                totalOs += ((Number)dto.getOutstanding()).doubleValue();
                smaStGroup += ((Number)dto.getOutstanding()).doubleValue();
                totalAcc += ((Number)dto.getOutstanding()).doubleValue();
                monthBasisCLReportViewModel.setSmOutstanding(((Number)dto.getOutstanding()).doubleValue());
                monthBasisCLReportViewModel.setSmaNoAcc(((Number) dto.getNoOfAcc()).intValue());
            }
            if (dto.getStatus().equals("STD")){
                totalOs += ((Number)dto.getOutstanding()).doubleValue();
                smaStGroup += ((Number)dto.getOutstanding()).doubleValue();
                totalAcc += ((Number) dto.getNoOfAcc()).intValue();
                monthBasisCLReportViewModel.setStOutstanding(((Number)dto.getOutstanding()).doubleValue());
                monthBasisCLReportViewModel.setStdoAcc(((Number) dto.getNoOfAcc()).intValue());
            }


            if (((Number)dto.getBucket()).doubleValue() >= maxBucket){
                currentParAmt += ((Number)dto.getOutstanding()).doubleValue();
            }


        }

        //previous month data
        for (MonthBasisCLReportDto dto: preMonthBasisCLReportDtoList){

            if(dto.getStatus().equals("BL")){
                previousMnthOS += ((Number) dto.getOutstanding()).doubleValue();
                monthBasisCLReportViewModel.setPrevBlOutstanding(((Number) dto.getOutstanding()).doubleValue());
            }
            if(dto.getStatus().equals("DF")){
                previousMnthOS += ((Number) dto.getOutstanding()).doubleValue();
                monthBasisCLReportViewModel.setPrevDfOutstanding(((Number) dto.getOutstanding()).doubleValue());
            }
            if (dto.getStatus().equals("SS")){
                previousMnthOS += ((Number)dto.getOutstanding()).doubleValue();
                monthBasisCLReportViewModel.setPrevSsOutstanding(((Number)dto.getOutstanding()).doubleValue());
            }
            if (dto.getStatus().equals("SMA")){
                previousMnthOS += ((Number)dto.getOutstanding()).doubleValue();
                monthBasisCLReportViewModel.setPrevSmOutstanding(((Number)dto.getOutstanding()).doubleValue());
            }
            if (dto.getStatus().equals("STD")){
                previousMnthOS += ((Number)dto.getOutstanding()).doubleValue();
                monthBasisCLReportViewModel.setPrevStOutstanding(((Number)dto.getOutstanding()).doubleValue());
            }
            if (((Number)dto.getBucket()).doubleValue() >= maxBucket){
                prevParAmt += ((Number)dto.getOutstanding()).doubleValue();
            }

        }
        monthBasisCLReportViewModel.setTotalOutstanding(totalOs);
        monthBasisCLReportViewModel.setTotalAcc(totalAcc);
        monthBasisCLReportViewModel.setBlDfSsGroup(getGroupPercentage(blDfSsGroup, totalOs));
        monthBasisCLReportViewModel.setSmaStGroup(getGroupPercentage(smaStGroup,totalOs));
        monthBasisCLReportViewModel.setTotalGroupValue(blDfSsGroup + smaStGroup);
        monthBasisCLReportViewModel.setTotalValuePreviousMnth(previousMnthOS);
        monthBasisCLReportViewModel.setTotalPar(currentParAmt - prevParAmt);

        return monthBasisCLReportViewModel;
    }

    private double getGroupPercentage(double blDfSsGroup, double totalOs) {
        double value = blDfSsGroup * 100;
        double t = value / totalOs;
        return t;
    }

    //To get previous month, param format (yyyy-mm) 2021-03
    public String getPreviousMonth (String month) {
        Integer prevMonth = Integer.valueOf(month.substring(5, 7))-1;
        Integer prevYear = Integer.valueOf(month.substring(0, 4));

        prevYear = prevMonth == 0 ? prevYear - 1 : prevYear;
        prevMonth = prevMonth == 0 ? 12 : prevMonth;

        return prevYear+"-"+(prevMonth < 10 ? "0"+prevMonth : prevMonth);
    }
}
