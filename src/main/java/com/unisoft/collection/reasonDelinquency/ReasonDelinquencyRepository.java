package com.unisoft.collection.reasonDelinquency;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReasonDelinquencyRepository extends JpaRepository<ReasonDelinquency, Long> {

    @Query(value = "SELECT * FROM REASON_DELINQUENCY WHERE CUSTOMER_BASIC_INFO_ENTITY_ID = ? ORDER BY CREATED_DATE DESC", nativeQuery = true)
    List<ReasonDelinquency> findReasonDelinquenciesByCustomerId(Long id);

    @Query(value = "SELECT * FROM REASON_DELINQUENCY where ACCOUNT_NO=?", nativeQuery = true)
    List<ReasonDelinquency> findReasonDelinquencyByAccountNo(String accountNo);
}
