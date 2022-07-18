package com.csinfotechbd.collection.distribution.loan;
/*
Created by Monirul Islam at 7/22/2019
*/

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanAccountBasicInfo, Long> {

    List<LoanAccountBasicInfo> findByCreatedDateIsBetween(Date startDate, Date endDate);

    @Query(value = "SELECT * from LOAN_ACCOUNT_BASIC_INFO WHERE ACCOUNT_NO LIKE ?1%", nativeQuery = true)
    List<LoanAccountBasicInfo> findByAccountCardNumberLike(String accountNumber);

}
