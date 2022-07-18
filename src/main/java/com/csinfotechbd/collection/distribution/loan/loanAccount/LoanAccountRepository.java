package com.csinfotechbd.collection.distribution.loan.loanAccount;

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
Created by Monirul Islam at 8/27/2019
*/
@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccountInfo, Long> {

    @Query(value = "SELECT * from LOAN_ACCOUNT_INFO i where i.LOAN_ACCOUNT_BASIC_INFO_ID IN (?1)", nativeQuery = true)
    List<LoanAccountInfo> findNumberoFStock(List<Long> id);

    LoanAccountInfo findFirstByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo);

    Optional<LoanAccountInfo> findByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo);

    List<LoanAccountInfo> findByEnabled(boolean enabled);

}
