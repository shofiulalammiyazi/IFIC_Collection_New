package com.unisoft.collection.settings.clstatus;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class CLStatusService {

    @Autowired
    private CLStatusRepository clStatusRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<CLStatus> getClStatusList(){
        return clStatusRepository.findAll();
    }

    public String save(CLStatus clStatus ) {
        boolean isNewEntity = false;
        CLStatus previousEntity = new CLStatus();

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (clStatus.getId() == null) {
            clStatus.setCreatedBy(user.getUsername());
            clStatus.setCreatedDate(new Date());
        } else {
            CLStatus oldData = clStatusRepository.getOne(clStatus.getId());
            BeanUtils.copyProperties(oldData, previousEntity);

            clStatus.setModifiedBy(user.getUsername());
            clStatus.setModifiedDate(new Date());
        }
        clStatusRepository.save(clStatus);

        if (isNewEntity)
            auditTrailService.saveCreatedData("CL Status", clStatus);
        else
            auditTrailService.saveUpdatedData("CL Status", previousEntity, clStatus);
        return "1";
    }

    public CLStatus getById(Long id){
        return clStatusRepository.findById(id).orElse(new CLStatus());
    }

    public List<CLStatus> getActiveList(){
        return clStatusRepository.findByEnabled(true);
    }

}
