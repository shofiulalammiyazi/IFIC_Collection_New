package com.unisoft.schedulermonitoringstatus;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerMonitoringStatusService {


    @Autowired
    private SchedulerMonitoringStatusRepository schedulerMonitoringStatusRepository;


    public SchedulerMonitoringStatus saveCreatedData(SchedulerMonitoringStatus schedulerMonitoringStatus){

        return schedulerMonitoringStatusRepository.save(schedulerMonitoringStatus);
    }

    public List<SchedulerMonitoringStatus> findAll(){
        return schedulerMonitoringStatusRepository.findAll();
    }
}
