package com.csinfotechbd.customerloanprofile.dailynote;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.customerloanprofile.guarantorinfo.GuarantorInfoDao;
import com.csinfotechbd.customerloanprofile.guarantorinfo.GuarantorInfoEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyNoteService {
    @Autowired
    private DailyNoteDao dailyNoteDao;

    @Autowired
    private DailyNoteRepository dailyNoteRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<DailyNoteEntity> getDailyNoteList(Long cusId) {
        return dailyNoteDao.getList(cusId);
    }

    public List<DailyNoteEntity> getDailyNoteAccList(String accountNo) {
        return dailyNoteRepository.findDailyNoteEntitiesByAccountNo(accountNo);
    }

    public DailyNoteEntity save(DailyNoteEntity dailyNoteEntity){
        return dailyNoteRepository.save(dailyNoteEntity);
    }

    public boolean saveDailyNoteInfo(DailyNoteEntity dailyNoteEntity) {
        boolean isNewEntity = true;

        DailyNoteEntity oldEntity = new DailyNoteEntity();
        if (dailyNoteEntity.getId() != null){
            DailyNoteEntity entity = dailyNoteDao.getById(dailyNoteEntity.getId());
            BeanUtils.copyProperties(entity, oldEntity);

            isNewEntity = false;
        }
        boolean response = dailyNoteDao.save(dailyNoteEntity);
        if (isNewEntity)
            auditTrailService.saveCreatedData("Note Details", dailyNoteEntity);
        else
            auditTrailService.saveUpdatedData("Note Details", oldEntity, dailyNoteEntity);
        return response;

    }
}
