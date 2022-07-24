package com.unisoft.collection.accountescalation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountEscalationRepository extends JpaRepository<AccountEscalation, Long> {

    List<AccountEscalation> findByAccountNumberOrderByCreatedDateDesc(String accountNubmer);

   /* @Query(value = "SELECT l1.* " +
            "         FROM ACCOUNT_ESCALATION l1 " +
            "    left join ACCOUNT_ESCALATION l2 " +
            "           on l1.ACCOUNT_NUMBER = l2.ACCOUNT_NUMBER " +
            "          and l1.CREATED_DATE < l2.CREATED_DATE " +
            "        WHERE (l1.TO_USER_PIN IN (?1) " +
            "           OR l1.FROM_USER_PIN IN (?1) " +
            "           OR l1.TEAM_LEADER_PIN IN (?1) " +
            "           OR l1.SUPER_VISOR_PIN IN (?1) " +
            "           OR l1.MANAGER_PIN IN (?1)) " +
            "          AND (l1.STATUS = 'Resolved' " +
            "           OR l1.STATUS = 'Pending') " +
            "          AND l2.ID is null " +
            "        ORDER BY l1.CREATED_DATE DESC", nativeQuery = true)*/
   @Query(value = "SELECT * " +
           "         FROM ACCOUNT_ESCALATION " +
           "        WHERE TO_USER_PIN IN (?1) " +
           "           OR TEAM_LEADER_PIN  IN (?1) " +
           "           OR SUPER_VISOR_PIN  IN (?1) " +
           "           OR MANAGER_PIN  IN (?1) " +
           "          AND (STATUS='Solved' " +
           "           OR STATUS='Pending') " +
           "        ORDER BY CREATED_DATE DESC", nativeQuery = true)
    List<AccountEscalation> findByToUserPinAndStatus(List<String> pin);

    AccountEscalation findAccountEscalationById(Long id);


    List<AccountEscalation> findAccountEscalationByDealerPinOrderByCreatedDateDesc(String pin);

    Integer countAllByAndAccountNumber(String accountNumber);

    List<AccountEscalation> findAccountEscalationsByAccountNumber(String accountNo);
}
