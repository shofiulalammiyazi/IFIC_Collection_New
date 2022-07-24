package com.unisoft.collection.reasonDelinquency;

import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface ReasonDelinquencyDtoRepository extends JpaRepository<LoanAccountDistributionInfo, Long> {

    @Query(value = "SELECT COUNT(LABI.ACCOUNT_NO)                                         as accountNo, " +
            "       RD.REA_DELIN_NAME                                        as rfdName, " +
            "        SUM(LADI.OUT_STANDING) AS amount " +
            "FROM REASON_DELINQUENCY RD " +
            " LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON RD.CUSTOMER_BASIC_INFO_ENTITY_ID = CBIE.ID " +
            " LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
            " LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = LADI.DEALER_PIN " +
            " LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY T ON PAL.TEAM_LEAD_ID = T.ID "+
            "WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  AND ladi.SUPERVISOR_PIN = ?3 " +
            "GROUP BY RD.REA_DELIN_NAME ", nativeQuery = true)
    List<Tuple> findCurrentMonthReasonDelinquncyBySupervisorPin(Date startDate, Date endDate, String pin);


    @Query(value = "with pinList as  (select emp.pin from PEOPLE_ALLOCATION_LOGIC pl " +
            "        left join EMPLOYEE_INFO_ENTITY e on e.id = pl.MANAGER_ID " +
            "       left join EMPLOYEE_INFO_ENTITY emp  on emp.id = pl.DEALER_ID " +
            " where e.pin = ?3 ) " +
            "select COUNT(LABI.ACCOUNT_NO)   as accountNo, " +
            " RD.REA_DELIN_NAME                          as rfdName, " +
            " SUM(LADI.OUT_STANDING) AS amount from LOAN_ACCOUNT_DISTRIBUTION_INFO ladi " +
            " left join LOAN_ACCOUNT_BASIC_INFO labi on ladi.LOAN_ACCOUNT_BASIC_INFO_ID = labi.ID " +
            " left join CUSTOMER_BASIC_INFO_ENTITY cbie on cbie.id = labi.CUSTOMER_ID " +
            " left join REASON_DELINQUENCY RD on RD.CUSTOMER_BASIC_INFO_ENTITY_ID = cbie.id " +
            " where DEALER_PIN in (select * from pinList) "+
            " and ladi.latest = 1 "+
            " and ladi.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  and RD.REA_DELIN_NAME is not null " +
            " and RD.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            " GROUP BY RD.REA_DELIN_NAME ", nativeQuery = true)
    List<Tuple> findCurrentMonthReasonDelinquencyByManagerPin(Date startDate, Date endDate, String pin);



}
