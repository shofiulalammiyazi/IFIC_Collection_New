package com.csinfotechbd.legal.report.managerial.statementOfAppellateDivisionCases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class StatementOfAppellateDivisionCasesService {

    @Autowired
    private StatementOfAppellateDivisionCasesRepository repository;

    public Map<String, List<StatementOfAppellateDivisionCasesDto>> getReport(List<Long> caseTypeIds, String byWhomeFiled, String startDate, String endDate) {
        Map<String, List<StatementOfAppellateDivisionCasesDto>> summary = new HashMap<>();
        List<Tuple> list = repository.getReport(caseTypeIds, byWhomeFiled, startDate, endDate);

        for (Tuple data : list) {
            StatementOfAppellateDivisionCasesDto dto = new StatementOfAppellateDivisionCasesDto(data);
            List<StatementOfAppellateDivisionCasesDto> dtoList = summary.getOrDefault(dto.getCaseType(), new LinkedList<>());
            dtoList.add(dto);
            summary.put(dto.getCaseType(), dtoList);
        }
        return summary;
    }



}
