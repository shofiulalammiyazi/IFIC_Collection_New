package com.csinfotechbd.collection.samd.dataEntry.visitReport.statusReport;

import java.util.List;

public interface StatusReportService {
    StatusReport save(StatusReport statusReport);

    List<StatusReport> findStatusReportByCustomerId(String customerId);

    StatusReport findStatusReportById(Long id);
}
