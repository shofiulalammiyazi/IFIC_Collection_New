package com.csinfotechbd.legal.report.datasheets.moneySuitFiledAgainstTheBankReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoneySuitFiledAgainstTheBankReportService {

    @Autowired
    private MoneySuitFiledAgainstTheBankReportRepository reportRepository;

    public List<MoneySuitFiledAgainstTheBankReportDto> getReport() {
        List<Tuple> report = reportRepository.getReportDtos();
//        List<LitigationCaseInfo> reportJpql = reportRepository.getReportDtosJpql();
        List<MoneySuitFiledAgainstTheBankReportDto> reportDtos = report.stream().map(MoneySuitFiledAgainstTheBankReportDto::new).collect(Collectors.toList());

        MoneySuitFiledAgainstTheBankReportDto totalDto = new MoneySuitFiledAgainstTheBankReportDto();
        int count = 0;
        for (MoneySuitFiledAgainstTheBankReportDto data : reportDtos) {
            double caseAmount = data.getCaseAmount() + totalDto.getCaseAmount();
            double totalRecovery = data.getTotalRecovery() + totalDto.getTotalRecovery();
            double outstanding = data.getOutstanding() + totalDto.getOutstanding();

            data.setSl(++count + "");

            totalDto.setCaseAmount(caseAmount);
            totalDto.setTotalRecovery(totalRecovery);
            totalDto.setOutstanding(outstanding);
        }

        totalDto.setSl("Total");
        reportDtos.add(totalDto);

        return reportDtos;
    }
}
