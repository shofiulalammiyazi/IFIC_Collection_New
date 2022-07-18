package com.csinfotechbd.legal.setup.collateralSecurity;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CollateralSecurityServiceImpl implements CollateralSecurityService {

    @Autowired
    CollateralSecurityRepository collateralSecurityRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public String save(CollateralSecurity collateralSecurity) {
        if (exists(collateralSecurity)) return "0";
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (collateralSecurity.getId() == null) {
            collateralSecurity.setCreatedBy(user.getUsername());
            collateralSecurity.setCreatedDate(new Date());
            collateralSecurityRepository.save(collateralSecurity);
            auditTrailService.saveCreatedData("Collateral Security", collateralSecurity);
        } else {
            CollateralSecurity oldEntity = collateralSecurityRepository.getOne(collateralSecurity.getId());
            CollateralSecurity previousEntity = new CollateralSecurity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            collateralSecurity.setModifiedBy(user.getUsername());
            collateralSecurity.setModifiedDate(new Date());
            collateralSecurityRepository.save(collateralSecurity);

            auditTrailService.saveUpdatedData("Collateral Security", previousEntity, collateralSecurity);
        }
        return "1";
    }

    @Override
    public List<CollateralSecurity> findAll() {
        return collateralSecurityRepository.findAll();
    }

    @Override
    public List<CollateralSecurity> findByEnabled(boolean enabled) {
        return collateralSecurityRepository.findByEnabledOrderBySequence(enabled);
    }

    @Override
    public List<CollateralSecurity> findByCaseType(Long caseTypeId) {
        return collateralSecurityRepository.findByCaseType(caseTypeId);
    }

    @Override
    public CollateralSecurity findById(Long id) {
        return collateralSecurityRepository.findById(id).orElse(new CollateralSecurity());
    }

    @Override
    public boolean existsByType(String type) {
        return collateralSecurityRepository.existsByName(type);
    }

    @Override
    public void deleteById(Long id) {
        collateralSecurityRepository.deleteById(id);
    }

    private boolean exists(CollateralSecurity collateralSecurity){
        if (collateralSecurity.getId() == null){
            return collateralSecurityRepository.existsByName(collateralSecurity.getName());
        }else {
            CollateralSecurity oldEntry = collateralSecurityRepository.findById(collateralSecurity.getId()).orElse(new CollateralSecurity());
            if (collateralSecurity.getName().equals(oldEntry.getName())) return false;
            else {
                return collateralSecurityRepository.existsByName(collateralSecurity.getName());
            }
        }
    }

}
