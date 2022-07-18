package com.csinfotechbd.reports.retail.loan.productWiseClReport;

import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductWiseCLReportService {

    private final ProductWiseCLReportRepository reportRepository;
    private final DateUtils dateUtils;

    public List<ProductWiseClReport> getReport(String month){
        return reportRepository.getProductWiseClReport(month);
    }

//    public JSONObject getReport(String month) {
//
//        JSONObject data = new JSONObject();
//        data.put("data", getLoanAndCardReport(month));
//        return data;
//
//    }
//
//    public List<ProductWiseCLReportDto> getLoanAndCardReport(String month) {
//
//        Object[][] report = reportRepository.getLoanAndCardReport(month);
//        List<ProductWiseCLReportDto> reportDtos = new LinkedList<>();
//        for (Object[] data : report) {
//            if (data[0] == null || data[0].toString().equalsIgnoreCase("null"))
//                continue;
//            ProductWiseCLReportDto dto = new ProductWiseCLReportDto(data);
//            reportDtos.add(dto);
//        }
//
//        return reportDtos;
//    }
//
//    public List<ProductWiseCLReportDto> getLoanReport(String month) {
//
//        Object[][] report = reportRepository.getLoanAndCardReport(month);
//        List<ProductWiseCLReportDto> reportDtos = new LinkedList<>();
//        for (Object[] data : report) {
//            ProductWiseCLReportDto dto = new ProductWiseCLReportDto(data);
//            reportDtos.add(dto);
//        }
//
//        return reportDtos;
//    }


}
