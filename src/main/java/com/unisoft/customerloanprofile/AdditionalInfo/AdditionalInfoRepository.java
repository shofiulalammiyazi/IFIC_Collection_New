package com.unisoft.customerloanprofile.AdditionalInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalInfoRepository extends JpaRepository<AdditionalInfo,Long> {

//    @Query(value = "SELECT * FROM ADDITIONAL_INFO where CUSTOMER_ID=?", nativeQuery = true)
//    List<AdditionalInfo> findAdditionalInfoByCustomerId(String customerId);

    @Query(value = "SELECT * FROM ADDITIONAL_INFO where ACCOUNT_NO=?", nativeQuery = true)
    List<AdditionalInfo> findAdditionalInfoByAccountNo(String accountNo);

    @Query(value = "SELECT * FROM ADDITIONAL_INFO WHERE (CUSTOMER_ID=? AND STATUS=?)",nativeQuery = true)
    List<AdditionalInfo> findAdditionalInfoByCustomerIdAndStatus(String customerId, String pending);

    @Query(value = "SELECT * FROM ADDITIONAL_INFO WHERE (DEALER_PIN=? AND STATUS=?)", nativeQuery = true)
    List<AdditionalInfo> findAdditionalInfoByDealerPinAndStatus(String pin, String pending);

//    AdditionalInfo findTopByCustomerIdOrderByIdDesc(String customerId);

    AdditionalInfo findTopByAccountNoOrderByIdDesc(String accountNo);

}
