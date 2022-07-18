package com.csinfotechbd.legal.report.managerial.pendingCourtCasesMonitoring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;



@Service
public class PendingCourtCasesMonitoringService {

    @Autowired
    private PendingCourtCasesMonitoringRepository repository;

    public List<PendingCourtCasesMonitoringDto> getReport() {
        List<Tuple> tuples = repository.getReport();
        List<PendingCourtCasesMonitoringDto> dtos = new ArrayList<>();
        for (Tuple tuple : tuples) {
            PendingCourtCasesMonitoringDto obj = new PendingCourtCasesMonitoringDto(tuple);
            dtos.add(obj);
        }

        return dtos;
    }
}
