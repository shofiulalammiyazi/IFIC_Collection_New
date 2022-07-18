package com.csinfotechbd.legal.report.managerial.statementOfHighCourtCases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class StatementOfHighCourtCasesService {

    @Autowired
    private StatementOfHighCourtCasesRepository repository;

    public Map<String, List<StatementOfHighCourtCasesDto>> getReport(List<Long> caseTypeIds, String byWhomeFiled, String startDate, String endDate) {
        Map<String, List<StatementOfHighCourtCasesDto>> summary = new HashMap<>();
        List<Tuple> list = repository.getReport(caseTypeIds, byWhomeFiled, startDate, endDate);

        for (Tuple data : list) {
            StatementOfHighCourtCasesDto dto = new StatementOfHighCourtCasesDto(data);
            List<StatementOfHighCourtCasesDto> dtoList = summary.getOrDefault(dto.getCaseType(), new LinkedList<>());
            dtoList.add(dto);
            summary.put(dto.getCaseType(), dtoList);
        }
        return summary;
    }



}
