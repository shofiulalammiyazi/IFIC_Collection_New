package com.unisoft.customerbasicinfo;

import com.unisoft.cardprofile.cardOtherAccountInfo.CardOtherAccountInfoRepository;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountRepository;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicRepository;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.detailsOfCollection.cardviewmodels.AccountInformation;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountInfo;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.user.UserService;
import com.unisoft.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerBasicInfoService {

    private final CustomerBasicInfoEntityRepository repository;
    private final LoanAccountRepository loanAccountRepository;
    private final LoanAccountBasicRepository loanAccountBasicRepository;

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

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired private LoanAccountBasicService loanAccountBasicService;

    //@Scheduled(cron = "0 0 10 * * *")
    @Scheduled(cron = "0 5 13 * * *")
    public void updateCustomer(){
        Map<String,CustomerBasicInfoEntity> map = new HashMap<>();

        List<CustomerBasicInfoEntity> customerBasicInfoEntities1;
        List<LoanAccountBasicInfo> loanAccountBasicInfos = new ArrayList<>();
        List<LoanAccountInfo> loanAccountInfos = new ArrayList<>();
        List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>();
        Map<String, CustomerBasicInfoEntity> cbieMap = new HashMap<>();
        Map<String, LoanAccountBasicInfo>labiMap = new HashMap<>();
        Map<String, LoanAccountInfo> laiMap = new HashMap<>();

        accountInformationRepository.findAllByNotClosedAccount().stream().forEach(accountInformationEntity -> {
            CustomerBasicInfoEntity customerBasicInfoEntity;
            LoanAccountBasicInfo loanAccountBasicInfo;
            LoanAccountInfo loanAccountInfo;

            customerBasicInfoEntity = repository.findByCustomerId(accountInformationEntity.getCustomerId());

            if(customerBasicInfoEntity == null) {
                customerBasicInfoEntity = new CustomerBasicInfoEntity(accountInformationEntity);
                customerBasicInfoEntity.setCreatedBy("System");
                customerBasicInfoEntity.setCreatedDate(new Date());
            }
            else{
                customerBasicInfoEntity = new CustomerBasicInfoEntity(customerBasicInfoEntity, accountInformationEntity);
                customerBasicInfoEntity.setModifiedBy("System");
                customerBasicInfoEntity.setModifiedDate(new Date());
            }
            if(!cbieMap.containsKey(accountInformationEntity.getCustomerId())) {
                customerBasicInfoEntities.add(customerBasicInfoEntity);
                cbieMap.put(accountInformationEntity.getCustomerId(), customerBasicInfoEntity);
            }

            //if(cbieMap.containsKey(accountInformationEntity.getCustomerId()))
               // System.out.println(cbieMap.get(accountInformationEntity.getCustomerId()));


            loanAccountBasicInfo = loanAccountBasicRepository.findOneByAccountNoNew(accountInformationEntity.getLoanAccountNew());

            if(loanAccountBasicInfo == null){
                loanAccountBasicInfo = new LoanAccountBasicInfo(customerBasicInfoEntity.getCustomerName(), customerBasicInfoEntity);
                loanAccountBasicInfo.setCreatedBy("System");
                loanAccountBasicInfo.setCreatedDate(new Date());
            }
            else{
                loanAccountBasicInfo = new LoanAccountBasicInfo(loanAccountBasicInfo, customerBasicInfoEntity);
                loanAccountBasicInfo.setModifiedBy("System");
                loanAccountBasicInfo.setModifiedDate(new Date());
            }

            if(!labiMap.containsKey(accountInformationEntity.getCustomerId())) {
                loanAccountBasicInfos.add(loanAccountBasicInfo);
                labiMap.put(accountInformationEntity.getCustomerId(), loanAccountBasicInfo);
            }

            if(loanAccountBasicInfo.getId() == null)
                loanAccountInfo = null;
            else
                loanAccountInfo = loanAccountRepository.findFirstByLoanAccountBasicInfo(loanAccountBasicInfo);
            if(loanAccountInfo == null){
                loanAccountInfo = new LoanAccountInfo(accountInformationEntity, loanAccountBasicInfo);
                loanAccountInfo.setCreatedBy("System");
                loanAccountInfo.setCreatedDate(new Date());
                loanAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
            }
            else {
                loanAccountInfo = new LoanAccountInfo(accountInformationEntity, loanAccountBasicInfo, loanAccountInfo);
                loanAccountInfo.setModifiedBy("System");
                loanAccountInfo.setModifiedDate(new Date());
            }

            if(!laiMap.containsKey(accountInformationEntity.getCustomerId())) {
                loanAccountInfos.add(loanAccountInfo);
                laiMap.put(accountInformationEntity.getCustomerId(), loanAccountInfo);
            }
            if(cbieMap.size() == 1000){

//                customerBasicInfoEntities.parallelStream().forEach(customerBasicInfoEntity1 -> {
//                    try {
//                        repository.save(customerBasicInfoEntity1);
//                    }catch (Exception e){
//                        System.out.println(customerBasicInfoEntity1);
//                        System.out.println(e.getMessage());
//                    }
//                });

                repository.saveAll(new ArrayList<>(cbieMap.values()));
                cbieMap.clear();
                customerBasicInfoEntities.clear();
                loanAccountBasicRepository.saveAll(new ArrayList<>(labiMap.values()));
                labiMap.clear();
                loanAccountBasicInfos.clear();
                loanAccountRepository.saveAll(new ArrayList<>(laiMap.values()));
                laiMap.clear();
                loanAccountInfos.clear();
            }

//            if (loanAccountBasicInfos.size() == 1000 && loanAccountInfos.size() == 1000) {
//                repository.saveAll(new ArrayList<>(cbieMap.values()));
//                customerBasicInfoEntities.clear();
//                loanAccountBasicRepository.saveAll(loanAccountBasicInfos);
//                loanAccountBasicInfos.clear();
//                loanAccountRepository.saveAll(loanAccountInfos);
//                loanAccountInfos.clear();
//            }
//            if(!map.containsKey(accountInformationEntity.getCustomerId())) {
//                CustomerBasicInfoEntity customerBasicInfoEntity = repository.findByCustomerId(accountInformationEntity.getCustomerId());
//                map.put(accountInformationEntity.getCustomerId(), customerBasicInfoEntity == null
//                        ? new CustomerBasicInfoEntity(accountInformationEntity): new CustomerBasicInfoEntity(customerBasicInfoEntity,accountInformationEntity));
//            }
//
//            if(map.size() == 1000){
//                List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>(map.values());
//                repository.saveAll(customerBasicInfoEntities);
//                loanAccountBasicService.updateLoanAccountBasicInfo(customerBasicInfoEntities);
//                customerBasicInfoEntities.clear();
//                map.clear();
//            }
        });

        if (customerBasicInfoEntities.size() > 0) {
            repository.saveAll(new ArrayList<>(cbieMap.values()));
            cbieMap.clear();
            customerBasicInfoEntities.clear();
        }
        if (loanAccountBasicInfos.size() > 0) {
            loanAccountBasicRepository.saveAll(new ArrayList<>(labiMap.values()));
            labiMap.clear();
            loanAccountBasicInfos.clear();
        }
        if (loanAccountInfos.size() > 0) {
            loanAccountRepository.saveAll(new ArrayList<>(laiMap.values()));
            laiMap.clear();
            loanAccountInfos.clear();
        }
    }
//    @Scheduled(cron = "0 30 14 * * *")
//    public void updateCustomer(){
//        Map<String,CustomerBasicInfoEntity> map = new HashMap<>();
//        List<CustomerBasicInfoEntity> customerBasicInfoEntities1;
//        accountInformationRepository.findAllByNotClosedAccount().stream().forEach(accountInformationEntity -> {
//            if(!map.containsKey(accountInformationEntity.getCustomerId()))
//                map.put(accountInformationEntity.getCustomerId(),new CustomerBasicInfoEntity(accountInformationEntity));
//
//            if(map.size() == 1000){
//                List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>(map.values());
//                repository.saveAll(customerBasicInfoEntities);
//                loanAccountBasicService.updateLoanAccountBasicInfo(customerBasicInfoEntities);
//                customerBasicInfoEntities.clear();
//            }
//        });
//
//        customerBasicInfoEntities1 = new ArrayList<>(map.values());
//        repository.saveAll(customerBasicInfoEntities1);
//        loanAccountBasicService.updateLoanAccountBasicInfo(customerBasicInfoEntities1);
//        customerBasicInfoEntities1.clear();
//    }
}
