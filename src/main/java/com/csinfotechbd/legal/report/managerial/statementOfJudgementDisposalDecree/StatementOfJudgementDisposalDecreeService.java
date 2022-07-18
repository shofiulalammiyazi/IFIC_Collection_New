package com.csinfotechbd.legal.report.managerial.statementOfJudgementDisposalDecree;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatementOfJudgementDisposalDecreeService {

    @Autowired
    private StatementOfJudgementDisposalDecreeRepository repository;


    public Map<String, List<StatementOfJudgementDisposalDecreeDto>> getReport(List<Long> caseTypeIds, String startDate, String endDate) {
        Map<String, List<StatementOfJudgementDisposalDecreeDto>> summary = new HashMap<>();
        List<Tuple> list = repository.getReport(caseTypeIds, startDate, endDate);

        for (Tuple data : list) {
            StatementOfJudgementDisposalDecreeDto dto = new StatementOfJudgementDisposalDecreeDto(data);
            List<StatementOfJudgementDisposalDecreeDto> dtoList = summary.getOrDefault(dto.getCaseType(), new LinkedList<>());
            dtoList.add(dto);
            summary.put(dto.getCaseType(), dtoList);
        }
        return summary;
    }
}
