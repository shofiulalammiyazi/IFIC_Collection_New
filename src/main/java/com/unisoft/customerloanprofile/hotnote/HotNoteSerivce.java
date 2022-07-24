package com.unisoft.customerloanprofile.hotnote;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotNoteSerivce {

    @Autowired
    private HotNoteDao hotNoteDao;

    @Autowired
    private HotNoteRepository hotNoteRepository;
    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<HotNoteEntity> getHotNoteList(Long cusId) {
        return hotNoteDao.getList(cusId);
    }

    public boolean saveHotNoteInfo(HotNoteEntity hotNoteEntity) {
        boolean isNewEntity = true;

        HotNoteEntity oldEntity = new HotNoteEntity();
        if (hotNoteEntity.getId() != null){
            HotNoteEntity entity = hotNoteDao.getById(hotNoteEntity.getId());
            BeanUtils.copyProperties(entity, oldEntity);

            isNewEntity = false;
        }

        boolean response = hotNoteDao.save(hotNoteEntity);
        if (isNewEntity)
            auditTrailService.saveCreatedData("Hot Note", hotNoteEntity);
        else
            auditTrailService.saveUpdatedData("Hot Note", oldEntity, hotNoteEntity);
        return response;
    }

    public HotNoteEntity findHotNoteEntityById(Long id) {
        HotNoteEntity hotNoteEntity = hotNoteDao.getById(id);
        return hotNoteEntity;
    }

    public List<HotNoteEntity> findHoteNoteBycustomerId(Long customerId) {
        return hotNoteRepository.findHotNoteEntitiesByCustomerId(customerId);
    }
}
