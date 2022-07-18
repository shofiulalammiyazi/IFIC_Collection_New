package com.csinfotechbd.collection.distribution.loan.loanAccountBasic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanAccountBasicRepository extends JpaRepository<LoanAccountBasicInfo, Long> {

    Optional<LoanAccountBasicInfo> findByAccountNo(String accountNo);

    List<LoanAccountBasicInfo> findByEnabled(boolean enabled);

    @Query("SELECT DISTINCT location FROM LoanAccountBasicInfo WHERE location IS NOT NULL")
    List<String> getAvailableLocatins();
}
