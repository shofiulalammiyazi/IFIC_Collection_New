package com.unisoft.customerloanprofile.referenceinfo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceInoRepository extends JpaRepository<ReferenceInfoEntity, Long> {


    @Query(value = "SELECT * FROM REFERENCE_INFO_ENTITY where CUSTOMER_ID=? ", nativeQuery = true)
    List<ReferenceInfoEntity> findReferenceInfoEntitiesByCustomerId(String customerId);


    @Query(value = "SELECT * FROM REFERENCE_INFO_ENTITY WHERE (CUSTOMER_ID=? AND STATUS = ?) ", nativeQuery = true)
    List<ReferenceInfoEntity> findReferenceInfoEntitiesByCustomerIdAAndStatus(String customerId, String pending);


    @Query(value = "SELECT * FROM REFERENCE_INFO_ENTITY WHERE (DEALER_PIN = ? AND STATUS = ?) ", nativeQuery = true)
    List<ReferenceInfoEntity> findReferenceInfoEntityByDealerPinBndStatus(String pin, String pending);


    List<ReferenceInfoEntity> findByLoanAccountNo(String loanAccountNo);
}
