package com.csinfotechbd.collection.settings.earlyNotifyEngine;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class EarlyNotifyEngineService {

    @Autowired
    private EarlyNotifyEngineRepository earlyNotifyEngineRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<EarlyNotifyEngine> getList(){
        return earlyNotifyEngineRepository.findAll();
    }

    public String save(EarlyNotifyEngine earlyNotifyEngine){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(earlyNotifyEngine.getId()==null){
            earlyNotifyEngine.setCreatedBy(user.getUsername());
            earlyNotifyEngine.setCreatedDate(new Date());
            earlyNotifyEngineRepository.save(earlyNotifyEngine);
            auditTrailService.saveCreatedData("Early Notification Engine", earlyNotifyEngine);
        }else{
            EarlyNotifyEngine oldEntity = earlyNotifyEngineRepository.getOne(earlyNotifyEngine.getId());
            EarlyNotifyEngine previousEntity = new EarlyNotifyEngine();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            earlyNotifyEngine.setModifiedBy(user.getUsername());
            earlyNotifyEngine.setModifiedDate(new Date());
            earlyNotifyEngineRepository.save(earlyNotifyEngine);
            auditTrailService.saveUpdatedData("Early Notification Engine",previousEntity,earlyNotifyEngine);
        }
        return "1";
    }

    public EarlyNotifyEngine getById(Long id)
    {
        earlyNotifyEngineRepository.getOne(id);
        return earlyNotifyEngineRepository.findById(id).orElse(new EarlyNotifyEngine());
    }

    public List<EarlyNotifyEngine> getActiveList()
    {
        return earlyNotifyEngineRepository.findByEnabled(true);
    }
}
