package com.csinfotechbd.legal.report.bangladeshBankReports.bikolpoARDreport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikolpoARDreportService {

    @Autowired
    private BikolpoARDreportRepository repository;

    public BikolpoARDreportDto getReport(Date endDate) {
        Tuple data = repository.getReport(endDate);
        return new BikolpoARDreportDto(data);
    }
}
