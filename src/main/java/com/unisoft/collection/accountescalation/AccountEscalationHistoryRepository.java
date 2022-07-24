package com.unisoft.collection.accountescalation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface AccountEscalationHistoryRepository extends JpaRepository<AccountEscalationHistory, Long> {

    @Query(value="SELECT AE.FROM_USER_PIN,AE.FROM_USER_NAME,DE.NAME AS DESIG, N.NOTE, N.STATUS, AE.CREATED_DATE " +
            "       FROM ACCOUNT_ESCALATION AE " +
            "       LEFT JOIN ACCOUNT_ESCALATION_NOTE N ON AE.ID = N.ACCOUNT_ESCALATION_ID " +
            "       LEFT JOIN EMPLOYEE_INFO_ENTITY EIE ON EIE.PIN = AE.FROM_USER_PIN " +
            "       LEFT JOIN DESIGNATION_ENTITY DE ON DE.ID = EIE.DESIGNATION_ID " +
            "      WHERE AE.ACCOUNT_NUMBER = ?1 " + //AND N.STATUS = 'Resolved'
            "      ORDER BY AE.CREATED_DATE DESC", nativeQuery = true)
    List<Tuple> findAllByAccountNumber(String accNo);
}
