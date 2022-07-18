package com.csinfotechbd.legal.report.bangladeshBankReports.bankBimaPendingCases;


import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BankBimaPendingCasesService {

    private final BankBimaPendingCasesRepository repository;
    private final DateUtils dateUtils;
    private final NumberUtils numberUtils;

    public BankBimaPendingCasesDto getReport(String startDate, String endDate) {
        Tuple data = repository.getReport(startDate, endDate);
        BankBimaPendingCasesDto dto = new BankBimaPendingCasesDto(data);

        String[] endDateDetails = endDate.split("-");
        String day = numberUtils.convertToBanglaNumber(Integer.valueOf(endDateDetails[2]));
        String month = numberUtils.convertToBanglaNumber(Integer.valueOf(endDateDetails[1]));
        String year = numberUtils.convertToBanglaNumber(Integer.valueOf(endDateDetails[0])).replace(",", "");
        dto.setDateOfReportGeneration(day + "-" + month + "-" + year);

        return dto;
    }
}
