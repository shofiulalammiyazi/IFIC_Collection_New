package com.csinfotechbd.legal.report.managerial.zoneWiseCases;

import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneDto;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneWiseCaseDetailsService {

    @Autowired
    private ZoneWiseCaseDetailsRepository repository;
    @Autowired
    private LitigationZoneService zoneService;


    public List<ZoneWiseCaseDetailsDto> getReport(String zoneName, List<String> districts) {
        return repository.getReport(zoneName, districts).stream().map(ZoneWiseCaseDetailsDto::new).collect(Collectors.toList());
    }

    public List<LitigationZoneDto> getZonesContainingCases() {
        return zoneService.getZonesContainingCases();
    }
}
