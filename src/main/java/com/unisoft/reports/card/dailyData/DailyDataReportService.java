package com.unisoft.reports.card.dailyData;

import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

;

@Service
public class DailyDataReportService {


    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private CardAccountDistributionRepository cardAccountDistributionRepository;

    public List<DailyDataReportDto> getDailyReportData(){

        List<DailyDataReportDto> dailyDataReportDtoList = new ArrayList<>();

        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
       // List<String> stringList = cardAccountDistributionRepository.findDistributionByUserPinAndDateRange( startDate, endDate);
       /* for (String contractId: stringList){
            DailyDataReportDto dailyDataReportDto = new DailyDataReportDto();


            dailyDataReportDtoList.add(dailyDataReportDto);
        }*/

        return dailyDataReportDtoList;
    }

}
