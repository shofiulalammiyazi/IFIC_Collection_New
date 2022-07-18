package com.csinfotechbd.legal.report.managerial.branchWiseLegalExpenses;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class BranchWiseLegalExpensesService {

    @Autowired
    private BranchWiseLegalExpensesRepository repository;

    public Map<String, List<BranchWiseLegalExpensesDto>> getReport(List<Long> caseTypeIds, String branchCode) {
        Map<String, List<BranchWiseLegalExpensesDto>> summary = new HashMap<>();
        List<Tuple> list = repository.getReport(caseTypeIds, branchCode);

        for (Tuple data : list) {
            BranchWiseLegalExpensesDto dto = new BranchWiseLegalExpensesDto(data);
            List<BranchWiseLegalExpensesDto> dtoList = summary.getOrDefault(dto.getCaseType(), new LinkedList<>());
            dtoList.add(dto);
            summary.put(dto.getCaseType(), dtoList);
        }
        return summary;
    }
}
