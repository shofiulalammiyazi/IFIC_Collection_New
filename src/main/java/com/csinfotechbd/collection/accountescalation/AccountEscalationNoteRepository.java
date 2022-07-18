package com.csinfotechbd.collection.accountescalation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountEscalationNoteRepository extends JpaRepository<AccountEscalationNote, Long> {

    @Query(value = "SELECT * FROM ACCOUNT_ESCALATION_NOTE WHERE ACCOUNT_ESCALATION_ID = :escalationId " +
            "order by CREATED_DATE desc ", nativeQuery = true)
    List<AccountEscalationNote> findAccountEscalationNoteByAccountEscalationId(@Param("escalationId") Long accountEscalationId);

    AccountEscalationNote findAccountEscalationNoteByCreatedBy(String createdBy);
}
