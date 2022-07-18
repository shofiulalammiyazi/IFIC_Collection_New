package com.csinfotechbd.reports.ptp;

import com.csinfotechbd.retail.loan.dataEntry.ptp.LoanPtp;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


@Service
public class PtpReportService {

    @Autowired
    public PtpReportsDao ptpdaoImp;

    @Autowired
    public PtpRepository repository;

    public JasperPrint exportPtpPdfFile() throws SQLException, JRException, IOException {
        return ptpdaoImp.exportPdfFile();
    }

    public List<LoanPtp> list(Date start, Date end) {
        return repository.findByCreatedDateIsBetween(start, end);
    }
}
