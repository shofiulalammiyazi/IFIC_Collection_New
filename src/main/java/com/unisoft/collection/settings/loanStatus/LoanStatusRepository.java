package com.unisoft.collection.settings.loanStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanStatusRepository extends JpaRepository<LoanStatusEntity, Long> {

    @Query(value = "SELECT * FROM LOAN_STATUS_ENTITY WHERE ID = ?1", nativeQuery = true)
    LoanStatusEntity findByLoanStatusId(Long id);
}
