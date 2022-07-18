package com.csinfotechbd.legal.report.managerial.statementOfClientWiseCaseDetails;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatementOfClientWiseCaseDetailsService {

    @Autowired
    private StatementOfClientWiseCaseDetailsRepository repository;

    public List<StatementOfClientWiseCaseDetailsDto> getReport(String clientId, String[] branches) {
        return repository.getReport(clientId, Arrays.asList(branches)).stream().map(StatementOfClientWiseCaseDetailsDto::new).collect(Collectors.toList());
    }

    public Set<String> getCifNumbers() {
        Set<String> cifSet = new HashSet<>();
        List<String> cifList = repository.getCifNumbers();
        for (String cif : cifList) {
            if (cif.contains("'"))
                System.out.println("-");
            List<String> cifNumbers = Arrays.asList(cif.replaceAll("\\D+", ",").split(","));
            cifSet.addAll(cifNumbers);
        }
        return cifSet;
    }
}
