package com.unisoft.collection.samd.dataEntry.visitReport.reportOne;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportOneServiceImpl implements ReportOneService{

    @Autowired
    private ReportOneRepository reportOneRepository;


    @Override
    public ReportOne saveReportOne(ReportOne reportOne) {
        ReportOne reportOne1 = reportOneRepository.save(reportOne);
        return reportOne1;
    }

    @Override
    public ReportOne findReportOneByCustomerId(String customerId) {
        ReportOne reportOne = reportOneRepository.findReportOneByCustomerId(customerId);
        return reportOne;
    }
}
