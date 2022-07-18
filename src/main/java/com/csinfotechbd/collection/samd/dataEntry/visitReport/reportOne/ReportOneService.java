package com.csinfotechbd.collection.samd.dataEntry.visitReport.reportOne;

public interface ReportOneService {
    ReportOne saveReportOne(ReportOne reportOne);

    ReportOne findReportOneByCustomerId(String customerId);
}
