package com.csinfotechbd.legal.report.bangladeshBankReports.statementOfSuitsFiledByAgainstTheBank;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Date;

@Service
public class StatementOfSuitsFiledByAgainstTheBankService {

    @Autowired
    private StatementOfSuitsFiledByAgainstTheBankRepository repository;

    public StatementOfSuitsFiledByAgainstTheBankDto getReport(Date endDate) {
        Tuple data = repository.getReport(endDate);
        return new StatementOfSuitsFiledByAgainstTheBankDto(data);
    }
}
