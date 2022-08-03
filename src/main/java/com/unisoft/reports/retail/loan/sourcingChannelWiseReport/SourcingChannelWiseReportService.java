package com.unisoft.reports.retail.loan.sourcingChannelWiseReport;

import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SourcingChannelWiseReportService {


    private final SourcingChannelWiseReportRepository reportRepository;
    private final LoanAccountDistributionRepository distributionRepository;
    private final DateUtils dateUtils;


//    public List<ChannelWiseSourcingReportDto> getReport(List<String> productGroups, String month) {
//
//        Date startDate = dateManager.getFormattedDate("yyyy-MM-dd", month + "-01");

    public List<String> findDistributedProductGroups() {
        List<String> productList =  distributionRepository.findDistributedProductGroups();
        return productList;
    }

    public List<SourcingChannelWiseReportDto> getReportData(String productGroup, String month) {
        List<Tuple> tuples = reportRepository.getReportData(productGroup,month);
        List<SourcingChannelWiseReportDto> list = new ArrayList<>();
        for (Tuple tuple: tuples){
            SourcingChannelWiseReportDto dto = new SourcingChannelWiseReportDto();
            dto.setSourcingChannel((String) tuple.get("sourceChannel"));
            dto.setTotalExposureOutstanding(((Number) tuple.get("sourceOs")).doubleValue());
            dto.setTotalExposureAccount(((Number) tuple.get("sourceTotalAcc")).longValue());
            dto.setTotalClOutstanding(((Number) tuple.get("clOsTotal")).doubleValue());
            dto.setTotalClAccount(((Number) tuple.get("clAccTotal")).longValue());
            dto.setTotalClPercentage((dto.getTotalClOutstanding()*100)/dto.getTotalExposureOutstanding());
            dto.setTotalParOutstanding(((Number) tuple.get("parOsTotal")).doubleValue());
            dto.setTotalParAccount(((Number) tuple.get("parAccTotal")).longValue());
            dto.setTotalParPercentage((dto.getTotalParOutstanding()*100)/dto.getTotalExposureOutstanding());

            list.add(dto);
        }
        return list;
    }
//
//    public JSONObject getReport(List<String> productGroups, String month) {
//
//        Date startDate = dateUtils.getFormattedDate(month + "-01", "yyyy-MM-dd");
//        Date endDate = dateUtils.getMonthEndDate(startDate);
//
//        JSONObject data = new JSONObject();
//        for (String productGroup : productGroups) {
//            data.put(productGroup, getReportDto(productGroup, startDate, endDate));
//        }
//        return data;
//
//    }
//
//    public JSONObject getReport(String productGroup, String month) {
//
//        Date startDate = dateUtils.getFormattedDate(month + "-01", "yyyy-MM-dd");
//        Date endDate = dateUtils.getMonthEndDate(startDate);
//
//        JSONObject data = new JSONObject();
//        List<SourcingChannelWiseReportDto> list = new ArrayList<>();
//        try {
//            list = getReportDto(productGroup, startDate, endDate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        data.put("data", list);
//
//        return data;
//
//    }
//
//    public List<SourcingChannelWiseReportDto> getReportDto(String productGroup, Date startDate, Date endDate) {
//
//        Object[][] report = reportRepository.getReportDtos(productGroup, startDate, endDate);
//
//        List<SourcingChannelWiseReportDto> reportDtos = new LinkedList<>();
//
//        SourcingChannelWiseReportDto totalDto = new SourcingChannelWiseReportDto();
//
//        for (Object[] data : report) {
//
//            SourcingChannelWiseReportDto dto = new SourcingChannelWiseReportDto(data);
//            reportDtos.add(dto);
//
//            double totalExposureOutstanding = dto.getTotalExposureOutstanding() + totalDto.getTotalExposureOutstanding();
//            long totalExposureAccount = dto.getTotalExposureAccount() + totalDto.getTotalExposureAccount();
//            double totalClOutstanding = dto.getTotalClOutstanding() + totalDto.getTotalClOutstanding();
//            double totalClPercentage = dto.getTotalClPercentage() + totalDto.getTotalClPercentage();
//            long totalClAccount = dto.getTotalClAccount() + totalDto.getTotalClAccount();
//            double totalParOutstanding = dto.getTotalParOutstanding() + totalDto.getTotalParOutstanding();
//            double totalParPercentage = dto.getTotalParPercentage() + totalDto.getTotalParPercentage();
//            long totalParAccount = dto.getTotalParAccount() + totalDto.getTotalParAccount();
//
//            totalDto.setTotalExposureOutstanding(totalExposureOutstanding);
//            totalDto.setTotalExposureAccount(totalExposureAccount);
//            totalDto.setTotalClOutstanding(totalClOutstanding);
//            totalDto.setTotalClPercentage(totalClPercentage);
//            totalDto.setTotalClAccount(totalClAccount);
//            totalDto.setTotalParOutstanding(totalParOutstanding);
//            totalDto.setTotalParPercentage(totalParPercentage);
//            totalDto.setTotalParAccount(totalParAccount);
//        }
//
//        totalDto.setSourcingChannel("Total");
//        reportDtos.add(totalDto);
//
//        return reportDtos;
//    }


}
