package com.csinfotechbd.legal.report.managerial.statementOfAdjustedCases;


import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatementOfAdjustedCasesService {

    private final StatementOfAdjustedCasesRepository repository;
    private final DateUtils dateUtils;

    public Map<String, List<StatementOfAdjustedCasesDto>> getReport(List<Long> caseTypeIds, String startDate, String endDate) {
        Map<String, List<StatementOfAdjustedCasesDto>> summary = new HashMap<>();
        List<Tuple> list = repository.getReport(caseTypeIds, startDate, endDate);

        for (Tuple data : list) {
            StatementOfAdjustedCasesDto dto = new StatementOfAdjustedCasesDto(data);
            List<StatementOfAdjustedCasesDto> dtoList = summary.getOrDefault(dto.getCaseType(), new LinkedList<>());
            dtoList.add(dto);
            summary.put(dto.getCaseType(), dtoList);
        }
        return summary;
    }
}
