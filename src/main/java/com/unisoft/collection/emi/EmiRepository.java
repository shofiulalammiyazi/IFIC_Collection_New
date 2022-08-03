package com.unisoft.collection.emi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EmiRepository extends JpaRepository<EmiEntity, Long> {

    List<EmiEntity> findByContractId(String contractId);


    @Query(value = "SELECT * FROM EMI_ENTITY WHERE CONTRACT_ID = :contractId AND PAYMENT_DATE BETWEEN :startDate AND :endDate ", nativeQuery = true)
    List<EmiEntity> findContractByDateRange(@Param("contractId") String contractId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
