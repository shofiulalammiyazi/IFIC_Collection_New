package com.csinfotechbd.collection.agency.agencyStatus;

import com.csinfotechbd.audittrail.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyStatusService {

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private AgencyStatusDao agencyStatusDao;

    @Autowired
    private AgencyStatusRepository agencyStatusRepository;


    public List<AgencyStatusEntity> getAllStatus()
    {
        return agencyStatusDao.getList();
    }

    public AgencyStatusEntity getById(Long Id)
    {
        return agencyStatusDao.getById(Id);
    }

    public boolean saveStat(AgencyStatusEntity currentStatus)
    {
        auditTrailService.saveCreatedData("Agency Status", currentStatus);

        return agencyStatusDao.saveNew(currentStatus);
    }

    public boolean updateStat(AgencyStatusEntity currentStatus)
    {
        AgencyStatusEntity previousData = new AgencyStatusEntity();
        auditTrailService.saveUpdatedData("Agency Status", previousData, currentStatus);
        return agencyStatusDao.updateStat(currentStatus);
    }

    public List<AgencyStatusEntity> getAllActive()
    {
        return agencyStatusDao.getActiveOnly();
    }


    public AgencyStatusEntity saveAgencyStatusEntity(AgencyStatusEntity agencyStatusEntity){
        AgencyStatusEntity agencyStatusEntity1 = agencyStatusRepository.save(agencyStatusEntity);
        return agencyStatusEntity1;
    }
}
