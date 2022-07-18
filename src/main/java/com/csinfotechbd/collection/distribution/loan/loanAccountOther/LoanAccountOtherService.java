package com.csinfotechbd.collection.distribution.loan.loanAccountOther;
/*
Created by Monirul Islam at 7/17/2019
*/

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.csinfotechbd.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanAccountOtherService {

    private final LoanAccountOtherRepository repository;

    private final LoanAccountBasicService loanAccountBasicService;

    public List<LoanAccountOtherInfo> getAll() {
        return repository.findAll();
    }

    public boolean saveNew(LoanAccountOtherInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public boolean updateAgency(LoanAccountOtherInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public LoanAccountOtherInfo getById(Long id) {
        return repository.getOne(id);
    }

    public List<LoanAccountOtherInfo> getActiveList() {
        return repository.findByEnabled(true);
    }

    public LoanAccountOtherInfo saveOrUpdate(LoanAccountOtherInfo entity) {
        return saveOrUpdate(entity, UserService.getSessionUsername());
    }

    public LoanAccountOtherInfo saveOrUpdate(@NonNull LoanAccountOtherInfo entity, @NonNull String username) {
        if (!loanAccountBasicService.isExistingAccount(entity.getLoanAccountBasicInfo())) return null;
        LoanAccountOtherInfo oldEntity = repository.findFirstByLoanAccountBasicInfo(entity.getLoanAccountBasicInfo());
        if (oldEntity != null && oldEntity.getId() != null) {
            entity.setId(oldEntity.getId());
            entity.setModifiedBy(username);
            entity.setModifiedDate(new Date());
            entity.setCreatedBy(oldEntity.getCreatedBy());
            entity.setCreatedDate(oldEntity.getCreatedDate());
        } else {
            entity.setCreatedBy(username);
            entity.setCreatedDate(new Date());
        }
        return repository.save(entity);
    }

    public LoanAccountOtherInfo findOrSave(@NonNull LoanAccountOtherInfo entity) {
        if (!loanAccountBasicService.isExistingAccount(entity.getLoanAccountBasicInfo())) return null;
        entity = Optional.ofNullable(repository.findFirstByLoanAccountBasicInfo(entity.getLoanAccountBasicInfo())).orElse(entity);
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
            entity.setCreatedBy(UserService.getSessionUsername());
            repository.save(entity);
        }
        return entity;
    }

}
