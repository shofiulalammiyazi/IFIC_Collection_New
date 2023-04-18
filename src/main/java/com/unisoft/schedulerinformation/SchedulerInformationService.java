package com.unisoft.schedulerinformation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerInformationService {

    @Autowired
    private SchedulerInformationRepository schedulerInformationRepository;

    public SchedulerInformationEntity save(SchedulerInformationEntity schedulerInformationEntity) {

        return schedulerInformationRepository.save(schedulerInformationEntity);
    }

    public List<SchedulerInformationEntity> findAll(){

        return schedulerInformationRepository.findAll();
    }

    public SchedulerInformationEntity getById(Long id)
    {
        return schedulerInformationRepository.getById(id);
    }

    public SchedulerInformationEntity getBySchedulerName(String name)
    {
        return schedulerInformationRepository.findBySchedulerName(name);
    }
}
