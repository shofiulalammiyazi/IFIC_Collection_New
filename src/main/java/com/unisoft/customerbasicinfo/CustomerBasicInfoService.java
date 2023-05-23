package com.unisoft.customerbasicinfo;

import com.unisoft.user.UserService;
import com.unisoft.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerBasicInfoService {

    private final CustomerBasicInfoEntityRepository repository;

    public List<CustomerBasicInfoEntity> getCustomerBasicInfoList() {

        return repository.findAll();
    }

    public CustomerBasicInfoEntity findById(Long id) {
        return repository.getOne(id);
    }

    public CustomerBasicInfoEntity saveOrUpdate(@NonNull CustomerBasicInfoEntity entity) {
        return saveOrUpdate(entity, UserService.getSessionUsername());
    }

    public CustomerBasicInfoEntity saveOrUpdate(@NonNull CustomerBasicInfoEntity entity, @NonNull String username) {
        if (!isValidCustomer(entity)) return null;
        CustomerBasicInfoEntity oldCustomerInfo = findByAccountNo(entity.getAccountNo());
        if (oldCustomerInfo == null) {
            entity.setCreatedDate(new Date());
            entity.setCreatedBy(username);
        } else {
            entity.setId(oldCustomerInfo.getId());
            entity.setCreatedDate(oldCustomerInfo.getCreatedDate());
            entity.setCreatedBy(oldCustomerInfo.getCreatedBy());
            entity.setModifiedDate(new Date());
            entity.setModifiedBy(username);
        }
        return repository.save(entity);
    }

    public CustomerBasicInfoEntity findOrSave(@NonNull CustomerBasicInfoEntity entity) {
        if (!isValidCustomer(entity)) return null;
        entity = Optional.ofNullable(repository.findByAccountNo(entity.getAccountNo())).orElse(entity);
        if (entity.getId() == null) {
            String username = UserService.getSessionUsername();
            entity.setCreatedDate(new Date());
            entity.setCreatedBy(username);
            repository.save(entity);
        }
        return entity;
    }

    public CustomerBasicInfoEntity findOrSave(String accountNumber) {
        if (!StringUtils.hasText(accountNumber)) return null;
        return findOrSave(new CustomerBasicInfoEntity(accountNumber));
    }

    public CustomerBasicInfoEntity findByAccountNo(String accountNo) {
        return StringUtils.hasText(accountNo) ? repository.findByAccountNo(accountNo) : null;
    }

    public CustomerBasicInfoEntity findByContractId(String contractId) {
        return StringUtils.hasText(contractId) ? repository.findByContractId(contractId) : null;
    }

    public CustomerBasicInfoEntity findByContractIdAndClientId(String accountNo, String clientId) {
        return StringUtils.hasText(accountNo) ? repository.findByContractIdAndClientId(accountNo, clientId) : null;
    }

    public CustomerBasicInfoEntity findByClientId(String clientId) {
        return StringUtils.hasText(clientId) ? repository.findByClientId(clientId) : null;
    }

    public boolean isExistingCustomer(CustomerBasicInfoEntity customer) {
        return isValidCustomer(customer) &&
                customer.getId() != null;
    }

    public boolean isValidCustomer(CustomerBasicInfoEntity customer) {
        return customer != null &&
                StringUtils.hasText(customer.getAccountNo())
                && customer.getAccountNo().length() == 16;
    }

    public List<CustomerBasicInfoEntity> findAllByClientId(String accountNo) {
        return repository.findAllByClientId(accountNo);
    }

    public List<CustomerBasicInfoEntity> findAllByContractId(String accountNo) {
        return repository.findAllByContractId(accountNo);
    }

    public CustomerBasicInfoEntity findFirstByContractIdAndCardType(String contractId, String cardType){

        return repository.findFirstByContractIdAndCardType(contractId,cardType);
    }


    public CustomerBasicInfoEntity save(CustomerBasicInfoEntity customerBasicInfoEntity){
        return repository.save(customerBasicInfoEntity);
    }

    public CustomerBasicInfoEntity getFirstByAccountNoOrderByAccountNoSubStr(String accNo){

        return repository.findFirstByAccountNoOrderByAccountNoSubStr(accNo);
    }
}
