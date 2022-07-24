package com.unisoft.collection.settings.previousaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PreviousAccountRepository extends JpaRepository<PreviousAccountEntity,Long> {


    @Query(value = "select * from PREVIOUS_ACCOUNT_ENTITY WHERE LOAN_ACCOUNT_NO = ? ", nativeQuery = true)
    PreviousAccountEntity findPreviousAccountByLoanAccountNo(String accountNo);
}
