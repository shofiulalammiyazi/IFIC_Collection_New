package com.unisoft.collection.samd.setup.dpdSamd;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DpdSamdEntityService {
    
    @Autowired
    private DpdSamdEntityRepo dpdSamdEntityRepo;
    
    @Autowired
    private AuditTrailService auditTrailService;
    
    public List<DpdSamdEntity> getActiveList(){
        return dpdSamdEntityRepo.findByEnabledOrderByDpdNameAsc(true);
    }
    
    public List<DpdSamdEntity> getAll(){
        return dpdSamdEntityRepo.findAll();
    }
    
    public String save(DpdSamdEntity dpdSamdEntity){
        boolean isNewEntity = false;
        DpdSamdEntity previousEntity = new DpdSamdEntity();
    
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dpdSamdEntity.getId() == null) {
            if (alreadyExists(dpdSamdEntity)) return "DPD name already exist";
            dpdSamdEntity.setCreatedBy(user.getUsername());
            dpdSamdEntity.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            DpdSamdEntity oldEntity = dpdSamdEntityRepo.getOne(dpdSamdEntity.getId());
            Hibernate.unproxy(oldEntity);
        
            dpdSamdEntity.setModifiedBy(user.getUsername());
            dpdSamdEntity.setModifiedDate(new Date());
        }
        dpdSamdEntityRepo.save(dpdSamdEntity);
    
        if (isNewEntity)
            auditTrailService.saveCreatedData("DPD - SAMD", dpdSamdEntity);
        else
            auditTrailService.saveUpdatedData("DPD - SAMD", previousEntity, dpdSamdEntity);
        return "1";
    }
    
    public DpdSamdEntity getById(Long id){
        return dpdSamdEntityRepo.findById(id).orElse(new DpdSamdEntity());
    }
    
    private boolean alreadyExists(DpdSamdEntity dpdSamdEntity) {
        String bucketName = dpdSamdEntity.getDpdName();
        if (dpdSamdEntity.getId() == null) {
            return dpdSamdEntityRepo.existsByDpdName(bucketName);
        } else {
            DpdSamdEntity oldEntry = dpdSamdEntityRepo.findById(dpdSamdEntity.getId()).orElse(new DpdSamdEntity());
            return (StringUtils.hasText(bucketName) &&
                    !bucketName.equals(oldEntry.getDpdName()) &&
                    dpdSamdEntityRepo.existsByDpdName(bucketName));
        }
    }
    
}
