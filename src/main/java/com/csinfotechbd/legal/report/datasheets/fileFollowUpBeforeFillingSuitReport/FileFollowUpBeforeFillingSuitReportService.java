package com.csinfotechbd.legal.report.datasheets.fileFollowUpBeforeFillingSuitReport;


// Created by Yasir Araphat on 15 February, 2021

import com.csinfotechbd.legal.dataEntry.fileFollowUp.LitigationFileFollowUp;
import com.csinfotechbd.legal.dataEntry.fileFollowUp.LitigationFileFollowUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileFollowUpBeforeFillingSuitReportService {
    private final LitigationFileFollowUpService litigationFileFollowUpService;

    public List<FileFollowUpBeforeFillingSuitReportDto> getReport(Pageable pageable) {
        Page<LitigationFileFollowUp> followUps = litigationFileFollowUpService.findAll(pageable);

        List<FileFollowUpBeforeFillingSuitReportDto> reportDtos = new LinkedList<>();

        int count = 0;
        for (LitigationFileFollowUp followUp : followUps) {
            reportDtos.add(new FileFollowUpBeforeFillingSuitReportDto(followUp, ++count));
        }

        return reportDtos;

    }
}
