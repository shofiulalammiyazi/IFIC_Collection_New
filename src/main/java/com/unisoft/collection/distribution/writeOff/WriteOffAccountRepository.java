package com.unisoft.collection.distribution.writeOff;
/*
Created by   Islam at 8/3/2019
*/

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WriteOffAccountRepository extends JpaRepository<WriteOffAccountInfo, Long> {

    WriteOffAccountInfo findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoOrderByCreatedDateDesc(Date startDate, Date endDate, LoanAccountBasicInfo loanAccountBasicInfo);

    WriteOffAccountInfo findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoOrderByCreatedDateDesc(Date startDate, Date endDate, CardAccountBasicInfo cardAccountBasicInfo);
}
