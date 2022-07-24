package com.unisoft.reports.retail.loan.paymentTrendMonthBasisReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;

@Service
public class MonthBasisPaymentTrendReportService {

    @Autowired
    private MonthBasisPaymentTrendReportRepository monthBasisPaymentTrendReportRepository;

    public MonthBasisPaymentTrendReportDto searchData(double lowerLimit, double upperLimit, String month) {
        List<Tuple> reportData = monthBasisPaymentTrendReportRepository.findAllByMonth(lowerLimit, upperLimit, month);
        MonthBasisPaymentTrendReportDto finalizedData = new MonthBasisPaymentTrendReportDto();
        finalizedData.setLimitSegment(lowerLimit + " - " + upperLimit);

        for (Tuple data : reportData){

            finalizedData.setGreaterThanHundred(data.get("greaterThanHundred"));
            finalizedData.setGreaterThanFifty(data.get("greaterThanFifty"));
            finalizedData.setLessThanFifty(data.get("lessThanFifty"));
            finalizedData.setNoPayment(data.get("noPayment"));
            finalizedData.setTotalAccount(data.get("totalAccount"));

            finalizedData.setPercentageOfGreaterThanHundred(data.get("percentageGreaterThanHundred"));
            finalizedData.setPercentageOfGreaterThanFifty(data.get("percentageGreaterThanFifty"));
            finalizedData.setPercentageOfLessThanFifty(data.get("percentageLessThanFifty"));
            finalizedData.setPercentageOfNoPayment(data.get("percentageNoPayment"));

        }

        return finalizedData;
    }
}
