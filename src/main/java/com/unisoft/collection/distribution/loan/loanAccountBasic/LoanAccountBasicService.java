package com.unisoft.collection.distribution.loan.loanAccountBasic;
/*
Created by   Islam at 7/17/2019
*/

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanAccountBasicService {

    private final LoanAccountBasicRepository repository;
    private final CustomerBasicInfoService customerBasicInfoService;

    public List<LoanAccountBasicInfo> getAll() {
        return repository.findAll();
    }

    public boolean saveNew(LoanAccountBasicInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public boolean updateAgency(LoanAccountBasicInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public LoanAccountBasicInfo getById(Long id) {
        return repository.findById(id).orElse(new LoanAccountBasicInfo());
    }

    public LoanAccountBasicInfo getByAccountNo(String accountNO) {
        return repository.findByAccountNo(accountNO).orElse(new LoanAccountBasicInfo());
    }

    public LoanAccountBasicInfo getByAccountNoNew(String accountNO) {
        //return repository.findByAccountNoNew(accountNO).orElse(new LoanAccountBasicInfo());
        return repository.findOneByAccountNoNew(accountNO);
    }

    public List<LoanAccountBasicInfo> getActiveList() {
        return repository.findByEnabled(true);
    }

    public boolean isValidAccount(LoanAccountBasicInfo loanAccountBasicInfo) {
        return loanAccountBasicInfo != null &&
                customerBasicInfoService.isExistingCustomer(loanAccountBasicInfo.getCustomer()) &&
                loanAccountBasicInfo.getAccountNo() != null &&
                loanAccountBasicInfo.getAccountNo().length() == 16;
    }

    public boolean isExistingAccount(LoanAccountBasicInfo loanAccountBasicInfo) {
        return isValidAccount(loanAccountBasicInfo) && loanAccountBasicInfo.getId() != null;
    }

    public LoanAccountBasicInfo saveOrUpdate(@NonNull LoanAccountBasicInfo entity) {
        return saveOrUpdate(entity, UserService.getSessionUsername());
    }

    public LoanAccountBasicInfo saveOrUpdate(@NonNull LoanAccountBasicInfo entity, @NonNull String username) {
        if (!isValidAccount(entity)) return null;
        LoanAccountBasicInfo previousEntity = repository.findByAccountNo(entity.getAccountNo()).orElse(null);
        if (previousEntity == null) {
            entity.setCreatedBy(username);
            entity.setCreatedDate(new Date());
        } else {
            entity.setId(previousEntity.getId());
            entity.setCreatedBy(previousEntity.getCreatedBy());
            entity.setCreatedDate(previousEntity.getCreatedDate());
            entity.setModifiedBy(username);
            entity.setModifiedDate(new Date());
        }
        return repository.save(entity);
    }

    public LoanAccountBasicInfo findOrSave(@NonNull LoanAccountBasicInfo entity) {
        if (!isValidAccount(entity)) return null;
        entity = repository.findByAccountNo(entity.getAccountNo()).orElse(entity);
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
            entity.setCreatedBy(UserService.getSessionUsername());
            repository.save(entity);
        }
        return entity;
    }


    public LoanAccountBasicInfo save(LoanAccountBasicInfo loanAccountBasicInfo){
        return repository.save(loanAccountBasicInfo);
    }

    public void updateLoanAccountBasicInfo(List<CustomerBasicInfoEntity> customerBasicInfoEntities){
        List<LoanAccountBasicInfo> loanAccountBasicInfos = new ArrayList<>();
        customerBasicInfoEntities.stream().forEach(customerBasicInfoEntity -> {
            loanAccountBasicInfos.add(new LoanAccountBasicInfo(customerBasicInfoEntity.getCustomerName(), customerBasicInfoEntity));

            if(loanAccountBasicInfos.size() == 1000) {
                repository.saveAll(loanAccountBasicInfos);
                loanAccountBasicInfos.clear();
            }
        });
        repository.saveAll(loanAccountBasicInfos);
    }
}
