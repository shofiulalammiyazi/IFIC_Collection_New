package com.unisoft.reports.retail.loan.vintageAnalysisReport;

import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VintageAnalysisReportService {

    private final VintageAnalysisReportRepository reportRepository;
    private final LoanAccountDistributionRepository distributionRepository;


    public List<String> findDistributedProductGroups() {

        return distributionRepository.findDistributedProductGroups();
    }

    public List<VintageAnalysisReportDto> getReportData(String productName) {
        List<Tuple> tuples = reportRepository.getReportData(productName);

        List<VintageAnalysisReportDto> vintageAnalysisList = new ArrayList<>();

        for (Tuple tuple: tuples){
            VintageAnalysisReportDto dto = new VintageAnalysisReportDto();
            dto.setDisburseYear((String) tuple.get("disburseDate"));
            dto.setGrandTotalOutstanding(tuple.get("segWiseOs") == null ? 0D : ((Number)tuple.get("segWiseOs")).doubleValue());
            dto.setGrandTotalAccount(tuple.get("segWisetotalAcc") == null ? 0L : ((Number)tuple.get("segWisetotalAcc")).longValue());
            dto.setUcOutstanding(tuple.get("ucOs") == null ? 0D : ((Number)tuple.get("ucOs")).doubleValue());
            dto.setUcAccount(tuple.get("ucAcc") == null? 0L :((Number)tuple.get("ucAcc")).longValue());
            dto.setUcPercentage((dto.getUcOutstanding()*100)/dto.getGrandTotalOutstanding());
            dto.setSmaOutstanding(tuple.get("smsOs") == null ? 0D : ((Number)tuple.get("smsOs")).doubleValue());
            dto.setSmaAccount(tuple.get("smaAcc") == null ? 0L : ((Number)tuple.get("smaAcc")).longValue());
            dto.setSmaPercentage((dto.getSmaOutstanding()*100)/dto.getGrandTotalOutstanding());
            dto.setSsOutstanding(tuple.get("ssOs") == null ? 0D : ((Number)tuple.get("ssOs")).doubleValue());
            dto.setSsAccount(tuple.get("ssAcc") == null ? 0L : ((Number)tuple.get("ssAcc")).longValue());
            dto.setSsPercentage((dto.getSsOutstanding()*100)/dto.getGrandTotalOutstanding());
            dto.setDfOutstanding(tuple.get("dfOs") == null ? 0D : ((Number)tuple.get("dfOs")).doubleValue());
            dto.setDfAccount(tuple.get("dfAcc") == null ? 0L : ((Number)tuple.get("dfAcc")).longValue());
            dto.setDfPercentage((dto.getDfOutstanding()*100)/dto.getGrandTotalOutstanding());
            dto.setBlOutstanding(tuple.get("blOs") == null ? 0D : ((Number)tuple.get("blOs")).doubleValue());
            dto.setBlAccount(tuple.get("blAcc") == null ? 0L : ((Number)tuple.get("blAcc")).longValue());
            dto.setBlPercentage((dto.getBlOutstanding()*100)/dto.getGrandTotalOutstanding());
            dto.setTotalParOutstanding(tuple.get("parOsTotal") == null ? 0D : ((Number)tuple.get("parOsTotal")).doubleValue());
            dto.setTotalParAccount(tuple.get("parAccTotal") == null ? 0L : ((Number)tuple.get("parAccTotal")).longValue());
            dto.setTotalParPercentage((dto.getTotalParOutstanding()*100)/dto.getGrandTotalOutstanding());
            dto.setTotalClOutstanding(tuple.get("clOsTotal") == null ? 0D : ((Number)tuple.get("clOsTotal")).doubleValue());
            dto.setTotalClAccount(tuple.get("clAccTotal") == null ? 0L : ((Number)tuple.get("clAccTotal")).longValue());
            dto.setTotalClPercentage((dto.getTotalClOutstanding()*100)/dto.getGrandTotalOutstanding());


            vintageAnalysisList.add(dto);
        }

        return vintageAnalysisList;
    }




}
