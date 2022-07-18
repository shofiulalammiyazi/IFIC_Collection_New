package com.csinfotechbd.collection.distribution.writeOff;
/*
Created by Monirul Islam at 8/3/2019
*/

import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WriteOffAccountRepository extends JpaRepository<WriteOffAccountInfo, Long> {

    WriteOffAccountInfo findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoOrderByCreatedDateDesc(Date startDate, Date endDate, LoanAccountBasicInfo loanAccountBasicInfo);

    WriteOffAccountInfo findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoOrderByCreatedDateDesc(Date startDate, Date endDate, CardAccountBasicInfo cardAccountBasicInfo);
}
