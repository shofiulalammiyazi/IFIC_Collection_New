package com.unisoft.collection.distribution.loan.loanAccount;
/*
Created by   Islam at 7/17/2019
*/

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanAccountService {

    private final LoanAccountRepository repository;

    private final LoanAccountBasicService loanAccountBasicService;

    public List<LoanAccountInfo> getAll() {
        return repository.findAll();
    }

    public boolean saveNew(LoanAccountInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public boolean updateAgency(LoanAccountInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public LoanAccountInfo getById(Long id) {
        return repository.getOne(id);
    }

    public List<LoanAccountInfo> getActiveList() {
        return repository.findByEnabled(true);
    }

    public LoanAccountInfo findByLoanAccountBasicId(LoanAccountBasicInfo loanAccountBasicInfo) {
        return repository.findByLoanAccountBasicInfo(loanAccountBasicInfo).orElse(new LoanAccountInfo());
    }

    public LoanAccountInfo saveOrUpdate(LoanAccountInfo entity) {
        return saveOrUpdate(entity, UserService.getSessionUsername());
    }

    public LoanAccountInfo saveOrUpdate(@NonNull LoanAccountInfo entity, @NonNull String username) {
        if (entity.getLoanAccountBasicInfo() == null) return null;

        LoanAccountInfo oldEntity = repository.findFirstByLoanAccountBasicInfo(entity.getLoanAccountBasicInfo());

        if (oldEntity == null) {
            entity.setCreatedDate(new Date());
            entity.setCreatedBy(username);
        } else {
            entity.setId(oldEntity.getId());
            entity.setCreatedBy(oldEntity.getCreatedBy());
            entity.setCreatedDate(oldEntity.getCreatedDate());
            entity.setModifiedBy(username);
            entity.setModifiedDate(new Date());
        }

        entity.setEnabled(true);

        return repository.save(entity);
    }

    public LoanAccountInfo findOrSave(@NonNull LoanAccountInfo entity) {
        if (!loanAccountBasicService.isExistingAccount(entity.getLoanAccountBasicInfo())) return null;
        entity = repository.findByLoanAccountBasicInfo(entity.getLoanAccountBasicInfo()).orElse(entity);
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
            entity.setCreatedBy(UserService.getSessionUsername());
            repository.save(entity);
        }
        return entity;
    }

}
