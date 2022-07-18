package com.csinfotechbd.legal.report.bangladeshBankReports.cibWritPetition;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CibWritPetitionService {

    @Autowired
    private CibWritPetitionRepository repository;

    public List<CibWritPetitionDto> getReport(String startDate, String endDate) {
        List<Tuple> data = repository.getReport(startDate, endDate);
        return data.stream().map(CibWritPetitionDto::new).collect(Collectors.toList());
    }
}
