package com.unisoft.collection.settings.loanType;

import com.unisoft.collection.settings.loanStatus.LoanStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanTypeEntity,Long> {

    @Query(value = "SELECT * FROM LOAN_TYPE_ENTITY WHERE ID = ?1", nativeQuery = true)
    LoanTypeEntity findByLoanTypeId(Long id);
}
