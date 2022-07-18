package com.csinfotechbd.legal.report.datasheets.accountWiseCaseReportUnderSupremeCourt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountWiseCaseReportUnderSupremeCourtService {

    @Autowired
    private AccountWiseCaseReportUnderSupremeCourtRepository repository;

    public List<AccountWiseCaseReportUnderSupremeCourtDto> searchData() {
        List<AccountWiseCaseReportUnderSupremeCourtDto> reportDtos = new ArrayList<>();
//        List<TestAwcruspDto> report = repository.getReportDate();
        Object[][] result = repository.getReport();
        int count = 0;
        for (Object[] data : result)
            reportDtos.add(new AccountWiseCaseReportUnderSupremeCourtDto(data, ++count));
        return reportDtos;
    }

//    public Object[][] generateReport() {
//        return repository.getReport();
//    }
}
