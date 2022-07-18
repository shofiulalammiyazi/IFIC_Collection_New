package com.csinfotechbd.legal.report.datasheets.accountWiseCaseReportUnderAraEx;


// Created by Yasir Araphat on 16 February, 2021

import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountWiseCaseReportUnderAraExService {

    private final DateUtils dateUtils;

    private final AccountWiseCaseReportUnderAraExRepository reportRepository;

    public List<AccountWiseCaseReportUnderAraExDto> getReport(String caseType) {

        int year = Calendar.getInstance().get(Calendar.YEAR);

        Date startDate = dateUtils.getFormattedDate(year + "-01-01", "yyyy-MM-dd");
        Date endDate = dateUtils.getFormattedDate(year + "-12-31", "yyyy-MM-dd");


        Object[][] reportData = reportRepository.getReportDtos(caseType, startDate, endDate);

        List<AccountWiseCaseReportUnderAraExDto> reportDtos = new LinkedList<>();

        AccountWiseCaseReportUnderAraExDto totalDto = new AccountWiseCaseReportUnderAraExDto();

        int count = 0;

        for (Object[] data : reportData) {
            AccountWiseCaseReportUnderAraExDto araExDto = new AccountWiseCaseReportUnderAraExDto(data, ++count);
            reportDtos.add(araExDto);

            double claimAmount = araExDto.getCaseClaimAmount() + totalDto.getCaseClaimAmount();
            double totalRecoveryFiveYearsEarlier = araExDto.getTotalRecoveryFiveYearsEarlier() + totalDto.getTotalRecoveryFiveYearsEarlier();
            double totalRecoveryFourYearsEarlier = araExDto.getTotalRecoveryFourYearsEarlier() + totalDto.getTotalRecoveryFourYearsEarlier();
            double totalRecoveryThreeYearsEarlier = araExDto.getTotalRecoveryThreeYearsEarlier() + totalDto.getTotalRecoveryThreeYearsEarlier();
            double totalRecoveryTwoYearsEarlier = araExDto.getTotalRecoveryTwoYearsEarlier() + totalDto.getTotalRecoveryTwoYearsEarlier();
            double totalRecoveryCurrentYear = araExDto.getTotalRecoveryCurrentYear() + totalDto.getTotalRecoveryCurrentYear();

            totalDto.setCaseClaimAmount(claimAmount);
            totalDto.setTotalRecoveryCurrentYear(totalRecoveryCurrentYear);
            totalDto.setTotalRecoveryTwoYearsEarlier(totalRecoveryTwoYearsEarlier);
            totalDto.setTotalRecoveryThreeYearsEarlier(totalRecoveryThreeYearsEarlier);
            totalDto.setTotalRecoveryFourYearsEarlier(totalRecoveryFourYearsEarlier);
            totalDto.setTotalRecoveryFiveYearsEarlier(totalRecoveryFiveYearsEarlier);
        }

        totalDto.setBranchCode("Total");
        reportDtos.add(totalDto);

        return reportDtos;
    }
}
