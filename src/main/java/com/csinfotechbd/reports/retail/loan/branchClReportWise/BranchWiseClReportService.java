package com.csinfotechbd.reports.retail.loan.branchClReportWise;

import com.csinfotechbd.collection.settings.branch.BranchRepository;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchWiseClReportService {
    private final BranchRepository branchRepository;
    private final BranchWiseClReportRepository reportRepository;
    private final DateUtils dateUtils;


    public List<BranchWiseCLReport> getReport(String date, List<String> branchList){
        List<BranchWiseCLReport> bwclReport = reportRepository.getBWCLReport(date, branchList);
        return bwclReport;
    }


}
