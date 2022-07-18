package com.csinfotechbd.collection.reasonDelinquency;

import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface ReasonDelinquencyWiseDtoRepository extends JpaRepository<LoanAccountDistributionInfo, Long> {

    @Query(value = "SELECT RD.ACCOUNT_NO                                         as accountNo, " +
            "       RD.REA_DELIN_NAME                                        as rfdName, " +
            "        LADI.OUT_STANDING AS outstanding, " +
            " RD.DEALER_NAME AS dealerName, " +
            " LABI.ACCOUNT_NAME as accountName " +
            "FROM REASON_DELINQUENCY RD " +
            " LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON RD.CUSTOMER_BASIC_INFO_ENTITY_ID = CBIE.ID " +
            " LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
            " LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = LADI.DEALER_PIN " +
            " LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY T ON PAL.TEAM_LEAD_ID = T.ID "+
            "WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  AND ladi.SUPERVISOR_PIN = ?3 " +
            "  AND LOWER(RD.REA_DELIN_NAME) = ?4 ", nativeQuery = true)
    List<Tuple> findCurrentMonthReasonDelinquencyWiseByDealerPin(Date startDate, Date endDate, String pin, String delinquency);


    @Query(value = "SELECT  COUNT(RD.ACCOUNT_NO)                                         as accountNo, " +
            "       RD.REA_DELIN_NAME                                        as rfdName, " +
            "        SUM(LADI.OUT_STANDING) AS outstanding, " +
            " RD.DEALER_NAME AS dealerName " +
            "FROM REASON_DELINQUENCY RD " +
            " LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON RD.CUSTOMER_BASIC_INFO_ENTITY_ID = CBIE.ID " +
            " LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
            " LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = LADI.DEALER_PIN " +
            " LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY T ON PAL.TEAM_LEAD_ID = T.ID "+
            "WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  AND ladi.SUPERVISOR_PIN = ?3 " +
            "  AND LADI.DEALER_PIN IN (?4) "+
            "  GROUP BY RD.REA_DELIN_NAME, RD.DEALER_NAME", nativeQuery = true)
    List<Tuple> getDealerWiseRfdBySupervisor(Date startDate, Date endDate,String supervisorPin, List<Long> dealerPin);

    @Query(value = "SELECT  COUNT(RD.ACCOUNT_NO)                                         as accountNo, " +
            "       RD.REA_DELIN_NAME                                        as rfdName, " +
            "        SUM(LADI.OUT_STANDING) AS outstanding, " +
            " RD.DEALER_NAME AS dealerName " +
            "FROM REASON_DELINQUENCY RD " +
            " LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON RD.CUSTOMER_BASIC_INFO_ENTITY_ID = CBIE.ID " +
            " LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
            " LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = LADI.DEALER_PIN " +
            " LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY T ON PAL.TEAM_LEAD_ID = T.ID "+
            "WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  AND LADI.DEALER_PIN IN (?3) "+
            "  GROUP BY RD.REA_DELIN_NAME, RD.DEALER_NAME", nativeQuery = true)
    List<Tuple> getDealerWiseRfdByManager(Date startDate, Date endDate, List<Long> dealerPin);

}
