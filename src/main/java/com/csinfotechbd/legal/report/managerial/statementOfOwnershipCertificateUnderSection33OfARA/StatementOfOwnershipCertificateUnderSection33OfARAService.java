package com.csinfotechbd.legal.report.managerial.statementOfOwnershipCertificateUnderSection33OfARA;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementOfOwnershipCertificateUnderSection33OfARAService {

    @Autowired
    private StatementOfOwnershipCertificateUnderSection33OfARARepository repository;

    public List<StatementOfOwnershipCertificateUnderSection33OfARADto> getReport(String courseOfAction) {
        return repository.getReport(courseOfAction).stream().map(StatementOfOwnershipCertificateUnderSection33OfARADto::new).collect(Collectors.toList());
    }
}
