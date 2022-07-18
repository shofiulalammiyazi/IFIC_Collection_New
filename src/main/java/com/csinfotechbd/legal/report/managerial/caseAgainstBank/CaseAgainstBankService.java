package com.csinfotechbd.legal.report.managerial.caseAgainstBank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaseAgainstBankService {
    
    @Autowired
    private CaseAgainstBankRepository caseAgainstBankRepository;
    
    public List<CaseAgainstBankDto> getAgentBankCases(List<Long> caseTypeIds){
        List<Tuple> tupleList= caseAgainstBankRepository.getReport(caseTypeIds);
        return tupleList.stream().map(CaseAgainstBankDto::new).collect(Collectors.toList());
    }
}
