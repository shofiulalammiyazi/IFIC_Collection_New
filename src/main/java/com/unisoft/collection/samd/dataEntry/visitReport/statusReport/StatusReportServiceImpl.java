package com.unisoft.collection.samd.dataEntry.visitReport.statusReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusReportServiceImpl implements StatusReportService{

    @Autowired
    private StatusReportRepository statusReportRepository;


    @Override
    public StatusReport save(StatusReport statusReport) {
        StatusReport statusReport1 = statusReportRepository.save(statusReport);
        return statusReport1;
    }

    @Override
    public List<StatusReport> findStatusReportByCustomerId(String customerId) {
        List<StatusReport>statusReports = statusReportRepository.findStatusReportByCustomerId(customerId);
        return statusReports;
    }

    @Override
    public StatusReport findStatusReportById(Long id) {
        StatusReport statusReport = statusReportRepository.findStatusReportById(id);
        return statusReport;
    }
}
