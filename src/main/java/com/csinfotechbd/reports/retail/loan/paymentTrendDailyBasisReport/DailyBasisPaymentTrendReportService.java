package com.csinfotechbd.reports.retail.loan.paymentTrendDailyBasisReport;

import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DailyBasisPaymentTrendReportService {

    private final DailyBasisPaymentTrendReportRepository reportRepository;
    private final DateUtils dateUtils;

    public Map<String, Object> getReport(String currentDate, String prevDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("firstDateData", getDataByDate(currentDate));
        map.put("secondDateData", getDataByDate(prevDate));

        return map;
    }

    private List<DailyBasisPaymentTrendReportDto> getDataByDate(String pDate) {
        List<Tuple> tuples = reportRepository.getDailyPaymentTreandByDate(pDate);
        List<DailyBasisPaymentTrendReportDto> trendReportDtos = new ArrayList<>();
        for (Tuple tuple: tuples){
            DailyBasisPaymentTrendReportDto dto = new DailyBasisPaymentTrendReportDto();
            dto.setDpdBucket(((Number) tuple.get("bucket")).toString());
            dto.setOutstanding(((Number) tuple.get("os")).doubleValue());
            dto.setOverdue(((Number) tuple.get("overDue")).doubleValue());
            dto.setOverdueAccounts(((Number) tuple.get("noOfacc")).longValue());
            dto.setPayment(((Number) tuple.get("payment")).doubleValue());
            dto.setPaymentAccounts(((Number) tuple.get("countPayAcc")).longValue());
            dto.setZeroOverdueAccounts(((Number) tuple.get("zeroOD")).longValue());

            trendReportDtos.add(dto);
        }

        return trendReportDtos;
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
