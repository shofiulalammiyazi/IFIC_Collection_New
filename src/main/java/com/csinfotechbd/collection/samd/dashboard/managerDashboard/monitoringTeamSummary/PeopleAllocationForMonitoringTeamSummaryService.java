package com.csinfotechbd.collection.samd.dashboard.managerDashboard.monitoringTeamSummary;


import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleAllocationForMonitoringTeamSummaryService {

    @Autowired
    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    public List<PeopleAllocationForMonitoringSummaryDto> findSupervisorWiseTeamleader(Long id, String unit) {
        List<Tuple> tupleList = peopleAllocationLogicRepository.findSupervisorWiseTeamleader(id, unit);
        List<PeopleAllocationForMonitoringSummaryDto> summaryDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            PeopleAllocationForMonitoringSummaryDto dto = new PeopleAllocationForMonitoringSummaryDto();
            dto.setTeamleaderId(tuple.get("teamlederId"));
            summaryDtoList.add(dto);
        }
        return summaryDtoList;
    }

    public List<PeopleAllocationForMonitoringSummaryDto> findTeamleaderWiseDealer(Long id, String unit) {
        List<Tuple> tupleList = peopleAllocationLogicRepository.findTeamleaderWiseDealer(id, unit);
        List<PeopleAllocationForMonitoringSummaryDto> summaryDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            PeopleAllocationForMonitoringSummaryDto dto = new PeopleAllocationForMonitoringSummaryDto();
            dto.setDealerId(tuple.get("dealerId"));
            summaryDtoList.add(dto);
        }
        return summaryDtoList;
    }
}
