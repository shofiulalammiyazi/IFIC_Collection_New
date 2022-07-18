package com.csinfotechbd.legal.report.managerial.statementOfCourtCases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementOfCourtCasesService {

    @Autowired
    private StatementOfCourtCasesRepository repository;

    public List<StatementOfCourtCasesDto> getReport(String branchName) {
        return repository.getReport(branchName).stream().map(StatementOfCourtCasesDto::new).collect(Collectors.toList());
    }
}
