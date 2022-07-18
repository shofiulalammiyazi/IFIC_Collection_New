package com.csinfotechbd.collection.distribution.agencyAllocation.loan;


import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoanAgencyDistributionRepository extends JpaRepository<LoanAgencyDistributionInfo, Long> {

    List<LoanAgencyDistributionInfo> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(Date startDate, Date endDate);

    LoanAgencyDistributionInfo findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(LoanAccountBasicInfo loanAccountBasicInfo);

    List<LoanAgencyDistributionInfo> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLoanAccountBasicInfo(Date startDate, Date endDate, LoanAccountBasicInfo loanAccountBasicInfo);

    List<LoanAgencyDistributionInfo> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLatest(Date startDate, Date endDate, String latest);

    LoanAgencyDistributionInfo findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLoanAccountBasicInfoOrderByCreatedDateDesc(Date startDate, Date endDate, LoanAccountBasicInfo loanAccountBasicInfo);

    LoanAgencyDistributionInfo findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(Date startDate, Date endDate, LoanAccountBasicInfo loanAccountBasicInfo, String latest);

    LoanAgencyDistributionInfo findFirstByCreatedDateLessThanAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(Date startDate, LoanAccountBasicInfo loanAccountBasicInfo, String s);


    @Query(value = "UPDATE LOAN_AGENCY_DISTRIBUTION_INFO l SET l.latest = '0' WHERE l.LOAN_ACCOUNT_BASIC_INFO_ID = :loanAccountBasicInfoId and l.CREATED_DATE >= trunc((sysdate),'MM') and l.latest = '1' ",nativeQuery = true)
    void updateLoanAccountDistributionLatestStatus(@Param("loanAccountBasicInfoId") Long loanAccountBasicInfoId);

    @Query(value = "SELECT * FROM LOAN_AGENCY_DISTRIBUTION_INFO " +
            "WHERE LOAN_ACCOUNT_BASIC_INFO_ID IN (SELECT ID FROM LOAN_ACCOUNT_BASIC_INFO WHERE ACCOUNT_NO IN :accountNos) " +
            "  AND LATEST = 1 ", nativeQuery = true)
    List<LoanAgencyDistributionInfo> findByAccountNo(@Param("accountNos") List<String> accountNos);
}
