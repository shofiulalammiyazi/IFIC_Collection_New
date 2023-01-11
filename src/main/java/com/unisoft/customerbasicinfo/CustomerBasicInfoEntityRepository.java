package com.unisoft.customerbasicinfo;


import com.unisoft.collection.settings.branch.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerBasicInfoEntityRepository extends JpaRepository<CustomerBasicInfoEntity, Long> {

    CustomerBasicInfoEntity findByCustomerId(String customerId);


    CustomerBasicInfoEntity findFirstByAccountNoOrderByIdDesc(String accountNo);

    CustomerBasicInfoEntity findByAccountNo(String accountNo);

    CustomerBasicInfoEntity findByContractIdAndClientId(String accountNo, String clientId);


    CustomerBasicInfoEntity findByClientId(String clientId);

    @Query("SELECT customerId FROM CustomerBasicInfoEntity WHERE accountNo in ?1 AND customerId IS NOT NULL")
    List<String> findCustomerIdsByAccounts(List<String> accounts);

    @Query("SELECT DISTINCT region FROM CustomerBasicInfoEntity")
    List<String> getAvailableRegions();


    @Query("SELECT DISTINCT new Branch(c.branchCode, c.branchName) FROM CustomerBasicInfoEntity c")
    List<Branch> getAvailableBranches();

    CustomerBasicInfoEntity findFirstByAccountNoOrderByAccountNoAsc(String accountNo);

    @Query(value = "SELECT CBIE.* FROM CUSTOMER_BASIC_INFO_ENTITY CBIE WHERE SUBSTR(CBIE.ACCOUNT_NO,0,13) = ?1", nativeQuery = true)
    CustomerBasicInfoEntity findFirstByAccountNoOrderByAccountNoSubStr(String accountNo);

    //added by shanto for card txt file data upload
    CustomerBasicInfoEntity findFirstByClientId(String clientId);

    CustomerBasicInfoEntity findFirstByContractId(String clientId);
    CustomerBasicInfoEntity findByContractIdAndCardType(String contractId, String cardType);

    CustomerBasicInfoEntity findFirstByContractIdAndCardType(String clientId, String cardType);

    List<CustomerBasicInfoEntity> findAllByClientId(String accountNo);

    List<CustomerBasicInfoEntity> findAllByContractId(String accountNo);

    CustomerBasicInfoEntity findByContractId(String contractId);
}
