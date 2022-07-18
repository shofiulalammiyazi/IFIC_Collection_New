package com.csinfotechbd.collection.distribution.samAccountHandover;
/*
Created by Monirul Islam at 8/1/2019
*/

import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SamAccountHandoverRepository extends JpaRepository<SamAccountHandoverInfo, Long> {

    List<SamAccountHandoverInfo> findBySamAccount(String sam);

    List<SamAccountHandoverInfo> findBySamAccountAndProductUnitAndLatest(String sam, String productUnit, String latest);

    SamAccountHandoverInfo findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatest(Date startDate, Date endDate, LoanAccountBasicInfo loanAccountBasicInfo, String latest);

    SamAccountHandoverInfo findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatest(Date startDate, Date endDate, CardAccountBasicInfo cardAccountBasicInfo, String latest);

    List<SamAccountHandoverInfo> findByCreatedDateIsBetweenAndProductUnitAndLatest(Date startDate, Date endDate, String productUnit, String latest);
}
