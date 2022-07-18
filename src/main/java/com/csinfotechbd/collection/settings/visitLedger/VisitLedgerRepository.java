package com.csinfotechbd.collection.settings.visitLedger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitLedgerRepository extends JpaRepository<VisitLedgerEntity, Long> {

    @Query(value = "SELECT * from VISIT_LEDGER_ENTITY WHERE ACCOUNT_CARD_NUMBER LIKE ?1%", nativeQuery = true)
    List<VisitLedgerEntity> findByAccountCardNumberLike(String accountNumber);
    List<VisitLedgerEntity> findByAccountCardNumberOrderByIdDesc(String accountNo);

    @Query(value = "SELECT * FROM VISIT_LEDGER_ENTITY WHERE CREATED_BY = ? AND (STATUS = 'PENDING' OR STATUS = 'ACCEPTED') ORDER BY CREATED_DATE DESC ", nativeQuery = true)
    List<VisitLedgerEntity> findVisitLedgerEntityByCreatedByAndStatus(String pin);

    VisitLedgerEntity findVisitLedgerEntityById(Long id);

    @Query(value = "SELECT * FROM VISIT_LEDGER_ENTITY WHERE ACCOUNT_CARD_NUMBER = ? AND (STATUS = 'PENDING' OR STATUS = 'ACCEPTED') ORDER BY CREATED_DATE DESC ", nativeQuery = true)
    List<VisitLedgerEntity> findVisitLedgerEntityByAccountCardNumberAndStatus(String accountNo);


    @Query(value = "SELECT * FROM VISIT_LEDGER_ENTITY WHERE CREATED_BY = ? AND (STATUS = 'PENDING' OR STATUS = 'ACCEPTED' OR STATUS='DECLINE') ORDER BY CREATED_DATE DESC ", nativeQuery = true)
    List<VisitLedgerEntity> findVisitLedgerEntityByCreatedByAndStatusForSuperVisor(String pin);
}
