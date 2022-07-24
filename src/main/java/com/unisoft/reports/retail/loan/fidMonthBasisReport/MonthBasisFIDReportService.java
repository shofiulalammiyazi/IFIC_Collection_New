package com.unisoft.reports.retail.loan.fidMonthBasisReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Service
public class MonthBasisFIDReportService {

    @Autowired
    private MonthBasisFIDReportRepository repository;

    public MonthBasisFIDReportDto getReport(Date startDate) {
        List<Tuple> data = repository.getReport(startDate);

        MonthBasisFIDReportDto monthBasisFIDReportDto = new MonthBasisFIDReportDto();

        for(Tuple t: data ) {
            monthBasisFIDReportDto.setTotalCount(t.get("TOTAL").toString());
            monthBasisFIDReportDto.setReportFidCount(t.get("REPORT_FID").toString());
            monthBasisFIDReportDto.setFidSum(t.get("FID_SUM").toString());
            monthBasisFIDReportDto.setSalaried(t.get("SALARIED").toString());
            monthBasisFIDReportDto.setBusinessman(t.get("BUSINESSMAN").toString());
            monthBasisFIDReportDto.setLandlord(t.get("LANDLORD").toString());
        }

        return monthBasisFIDReportDto;
    }


}
