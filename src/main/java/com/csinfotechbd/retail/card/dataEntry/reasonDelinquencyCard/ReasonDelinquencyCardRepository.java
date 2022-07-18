package com.csinfotechbd.retail.card.dataEntry.reasonDelinquencyCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReasonDelinquencyCardRepository extends JpaRepository<ReasonDelinquencyCard, Long> {

    @Query(value = "SELECT * FROM REASON_DELINQUENCY_CARD WHERE CUSTOMER_BASIC_INFO_ID = ? ORDER BY CREATED_DATE DESC", nativeQuery = true)
    List<ReasonDelinquencyCard> findReasonDelinquenciesCardByCustomerId(Long id);
}
