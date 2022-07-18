package com.csinfotechbd.reports.retail.loan.vipListReport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VipListReportService {

    private final VipListReportRepository repository;

    public List<VipListReportDto> getVipListByVipStatus(String vipStatus) {
        return repository.getVipListByVipStatus(vipStatus);
    }
}
