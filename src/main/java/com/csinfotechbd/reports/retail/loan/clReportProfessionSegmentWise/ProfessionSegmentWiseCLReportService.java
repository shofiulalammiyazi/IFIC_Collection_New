package com.csinfotechbd.reports.retail.loan.clReportProfessionSegmentWise;

import com.csinfotechbd.collection.distribution.loan.LoanAccountDistributionRepository;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionSegmentWiseCLReportService {

    private final ProfessionSegmentWiseCLReportRepository reportRepository;

//    public List<ProfessionSegmentReport>getReport(List<String>product){
//        return reportRepository.getProfessionSegmentReport(product);
//    }

    public List<ProfessionSegmentReportDto> getReport(String productGroup){
        List<Tuple> tuples = reportRepository.getProfessionSegmentReport(productGroup);

        List<ProfessionSegmentReportDto> professionSegmentReportDtoList = new ArrayList<>();

        for(Tuple tuple: tuples){
            ProfessionSegmentReportDto dto = new ProfessionSegmentReportDto();
            dto.setSegment((String) tuple.get("segment"));
            dto.setTotalOs(tuple.get("totalOs") == null ? 0D : ((Number)tuple.get("totalOs")).doubleValue());
            dto.setTotalAcc(tuple.get("totalAcc") == null ? 0L : ((Number)tuple.get("totalAcc")).longValue());
            dto.setClOsTotal(tuple.get("clOsTotal") == null ? 0D : ((Number)tuple.get("clOsTotal")).doubleValue());
            dto.setClPercent((dto.getClOsTotal()*100)/dto.getTotalOs());
            dto.setClAccTotal(tuple.get("clAccTotal") == null ? 0L : ((Number)tuple.get("clAccTotal")).longValue());
            dto.setParOsTotal(tuple.get("parOsTotal") == null ? 0D : ((Number)tuple.get("parOsTotal")).doubleValue());
            dto.setParPercent((dto.getParOsTotal()*100)/dto.getTotalOs());
            dto.setParAccTotal(tuple.get("parAccTotal") == null ? 0L : ((Number)tuple.get("parAccTotal")).longValue());

            professionSegmentReportDtoList.add(dto);
        }
        return professionSegmentReportDtoList;
    }


}
