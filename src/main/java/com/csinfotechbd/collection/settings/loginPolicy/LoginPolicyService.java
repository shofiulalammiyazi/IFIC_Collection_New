package com.csinfotechbd.collection.settings.loginPolicy;
/*
Created by Monirul Islam at 7/3/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginPolicyService {

    private final LoginPolicyRepository repository;
    private final AuditTrailService auditTrailService;

    public String save(LoginPolicyEntity entity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Ensure only one login policy exists
        LoginPolicyEntity oldEntry = getPolicy();
        entity.setId(oldEntry.getId());
        boolean isNewEntry = false;
        LoginPolicyEntity previousEntry = new LoginPolicyEntity();
        BeanUtils.copyProperties(oldEntry, previousEntry);

        if (entity.getId() == null) {
            entity.setCreatedBy(user.getUsername());
            entity.setCreatedDate(new Date());
            isNewEntry = true;
        } else {
            entity.setModifiedBy(user.getUsername());
            entity.setModifiedDate(new Date());
        }
        repository.save(entity);

        if (isNewEntry) auditTrailService.saveCreatedData("Login Policy", entity);
        else auditTrailService.saveUpdatedData("Login Policy", previousEntry, entity);
        return "1";
    }

    public LoginPolicyEntity getPolicy() {
        List<LoginPolicyEntity> list = repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return (list.isEmpty()) ? new LoginPolicyEntity() : list.get(0);
    }
}
