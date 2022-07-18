package com.csinfotechbd.legal.report.managerial.statementOfMonthlyNewCases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatementOfMonthlyNewCasesService {

    @Autowired
    private StatementOfMonthlyNewCasesRepository repository;

    public Map<String, List<StatementOfMonthlyNewCasesDto>> getReport(List<Long> caseTypeIds, Date startDate, Date endDate) {
        Map<String, List<StatementOfMonthlyNewCasesDto>> summary = new HashMap<>();
        List<Tuple> list = repository.getReport(caseTypeIds, startDate, endDate);

        for (Tuple data : list) {
            StatementOfMonthlyNewCasesDto dto = new StatementOfMonthlyNewCasesDto(data);
            List<StatementOfMonthlyNewCasesDto> dtoList = summary.getOrDefault(dto.getCaseType(), new LinkedList<>());
            dtoList.add(dto);
            summary.put(dto.getCaseType(), dtoList);
        }
        return summary;
    }
}
