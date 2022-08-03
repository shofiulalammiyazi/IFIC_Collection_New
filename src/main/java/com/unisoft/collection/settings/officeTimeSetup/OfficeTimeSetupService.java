package com.unisoft.collection.settings.officeTimeSetup;

import com.unisoft.audittrail.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class OfficeTimeSetupService {

//    private final OfficeTimeSetupDao officeTimeSetupDao;
    private final OfficeTimeSetupRepository repository;
    private final AuditTrailService auditTrailService;

    public OfficeTimeSetupInfo getOfficeTimeSetup() {
        OfficeTimeSetupInfo officeTimeSetupInfo = repository.findFirstByEnabledOrderByCreatedDateDesc(true);
        return officeTimeSetupInfo != null ? officeTimeSetupInfo : save(new OfficeTimeSetupInfo());
    }

    public OfficeTimeSetupInfo save(OfficeTimeSetupInfo officeTimeSetupInfo) {
        boolean isNewEntity = false;
        OfficeTimeSetupInfo previousEntity = new OfficeTimeSetupInfo();

        if (officeTimeSetupInfo.getId() == null)
            isNewEntity = true;
        else{
            OfficeTimeSetupInfo oldEntity = repository.getOne(officeTimeSetupInfo.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
        }

        if (officeTimeSetupInfo.getOfficeHour() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            officeTimeSetupInfo.setOfficeHour(calendar.getTime());
        }
        if (officeTimeSetupInfo.getLogoutHour() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            officeTimeSetupInfo.setLogoutHour(calendar.getTime());
        }

        repository.save(officeTimeSetupInfo);
        if (isNewEntity)
            auditTrailService.saveCreatedData("Office Time Setup", officeTimeSetupInfo);
        else
            auditTrailService.saveUpdatedData("Office Time Setup", previousEntity, officeTimeSetupInfo);
        return officeTimeSetupInfo;
    }

//    public OfficeTimeSetupInfo getOfficeTimeSetup() {
//        return officeTimeSetupDao.getOfficeTimeSetupInfo();
//    }
//
//    public boolean saveOfficeSetup(OfficeTimeSetupInfo officeTimeSetupInfo) {
//        return officeTimeSetupDao.saveOfficeTime(officeTimeSetupInfo);
//    }
//
//    public boolean updateOfficeSetup(OfficeTimeSetupInfo officeTimeSetupInfo) {
//        return officeTimeSetupDao.updateOfficeTime(officeTimeSetupInfo);
//    }


}
