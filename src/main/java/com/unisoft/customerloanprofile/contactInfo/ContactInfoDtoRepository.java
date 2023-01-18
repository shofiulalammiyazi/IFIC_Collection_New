package com.unisoft.customerloanprofile.contactInfo;

import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface ContactInfoDtoRepository extends JpaRepository<LoanAccountDistributionInfo, Long> {


    @Query(value = "SELECT substr(L.ACCOUNT_NO,0,13)                                         as accountNo, " +
            "       l.CUSTOMER_ID                                        as customerId, " +
            "       COUNT(con.ATTEMPT) AS attempted " +
            "FROM LOAN_ACCOUNT_DISTRIBUTION_INFO ladi " +
            "       LEFT JOIN LOAN_ACCOUNT_BASIC_INFO L on ladi.LOAN_ACCOUNT_BASIC_INFO_ID = L.ID " +
            "       left join CONTACT_INFO con on con.CUSTOMER_ID = l.CUSTOMER_ID " +
            "WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            " and con.CREATED_DATE BETWEEN ?1 and ?2 " +
            "  and CON.ATTEMPT IS NOT NULL " +
            "  AND ladi.DEALER_PIN = ?3 " +
            "GROUP BY L.ACCOUNT_NO, l.CUSTOMER_ID ", nativeQuery = true)
    List<Tuple> findCurrentMonthContactInfoByDealerPin(Date startDate, Date endDate, String pin);


    @Query(value = "SELECT L.CONTRACT_ID                                         as accountNo, " +
            "       l.CUSTOMER_ID                                        as customerId, " +
            "       COUNT(con.ATTEMPT) AS attempted " +
            "FROM CARD_ACCOUNT_DISTRIBUTION_INFO ladi " +
            "       LEFT JOIN CARD_ACCOUNT_BASIC_INFO L on ladi.CARD_ACCOUNT_BASIC_INFO_ID = L.ID " +
            "       left join CONTACT_INFO_CARD con on con.CUSTOMER_ID = l.CUSTOMER_ID " +
            "WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            " and con.CREATED_DATE BETWEEN ?1 and ?2 " +
            "  and CON.ATTEMPT IS NOT NULL " +
            "  AND ladi.DEALER_PIN = ?3 " +
            "GROUP BY L.CONTRACT_ID, l.CUSTOMER_ID ", nativeQuery = true)
    List<Tuple> findCurrentMonthContactInfoByCardDealerPin(Date startDate, Date endDate, String pin);

    @Query(value = "SELECT substr(LABI.ACCOUNT_NO ,0,13)                      as accountNo, " +
            "       LABI.CUSTOMER_ID                                        as customerId, " +
            "       COUNT(CI.ATTEMPT) AS attempted " +
            "FROM CONTACT_INFO CI " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CI.CUSTOMER_ID = CBIE.ID " +
            "       LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
            " LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = LADI.DEALER_PIN " +
            " LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY T ON PAL.TEAM_LEAD_ID = T.ID "+
            "WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  and CI.ATTEMPT IS NOT NULL " +
            "  AND ladi.DEALER_PIN = ?3 " +
            "GROUP BY LABI.ACCOUNT_NO, LABI.CUSTOMER_ID ", nativeQuery = true)
    List<Tuple> findCurrentMonthContactInfoByTeamleadPin(Date startDate, Date endDate, String pin);

    @Query(value = "SELECT substr(LABI.ACCOUNT_NO ,0,13)                     as accountNo, " +
            "       LABI.CUSTOMER_ID                                        as customerId, " +
            "       COUNT(CI.ATTEMPT) AS attempted " +
            "FROM CONTACT_INFO CI " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CI.CUSTOMER_ID = CBIE.ID " +
            "       LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
            " LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = LADI.DEALER_PIN " +
            " LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY T ON PAL.TEAM_LEAD_ID = T.ID "+
            "WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  and CI.ATTEMPT IS NOT NULL " +
            "  AND ladi.SUPERVISOR_PIN = ?3 " +
            "GROUP BY LABI.ACCOUNT_NO, LABI.CUSTOMER_ID ", nativeQuery = true)
    List<Tuple> findCurrentMonthContactInfoBySupervisorPin(Date startDate, Date endDate, String pin);

    @Query(value = "with pinList as  (select emp.pin from PEOPLE_ALLOCATION_LOGIC pl " +
            "       left join EMPLOYEE_INFO_ENTITY e " +
            "        on e.id = pl.MANAGER_ID " +
            "left join EMPLOYEE_INFO_ENTITY emp " +
            "       on emp.id = pl.DEALER_ID " +
            "       where e.pin = ?3) " +
            "   select substr(labi.ACCOUNT_NO ,0,13) as accountNo, count(*) AS attempted from LOAN_ACCOUNT_DISTRIBUTION_INFO ladi " +
            " left join LOAN_ACCOUNT_BASIC_INFO labi " +
            "  on ladi.LOAN_ACCOUNT_BASIC_INFO_ID = labi.ID " +
            " left join CUSTOMER_BASIC_INFO_ENTITY cbie " +
            "  on cbie.id = labi.CUSTOMER_ID " +
            "left join CONTACT_INFO ci " +
            " on ci.CUSTOMER_ID = cbie.id "+
            "  where DEALER_PIN in (select * from pinList) " +
            "  and ladi.latest = 1 " +
            " and ladi.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            " and ci.CUSTOMER_ID is not null " +
            " and ci.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            " group by ci.CUSTOMER_ID, labi.ACCOUNT_NO ", nativeQuery = true)
    List<Tuple> findCurrentMonthContactInfoByManagerPin(Date startDate, Date endDate, String pin);


    @Query(value = "SELECT CABI.CONTRACT_ID                                         as accountNo, " +
            "       CABI.CUSTOMER_ID                                        as customerId, " +
            "       COUNT(CIC.ATTEMPT) AS attempted " +
            "FROM CONTACT_INFO_CARD CIC " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CIC.CUSTOMER_ID = CBIE.ID " +
            "       LEFT JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CBIE.ID = CABI.CUSTOMER_ID " +
            " LEFT JOIN CARD_ACCOUNT_DISTRIBUTION_INFO CADI ON CABI.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = CADI.DEALER_PIN " +
            " LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            " LEFT JOIN EMPLOYEE_INFO_ENTITY T ON PAL.TEAM_LEAD_ID = T.ID " +
            "WHERE CADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  and CIC.ATTEMPT IS NOT NULL " +
            "  AND CADI.DEALER_PIN = ?3 " +
            "GROUP BY CABI.CONTRACT_ID, CABI.CUSTOMER_ID", nativeQuery = true)
    List<Tuple> findCardCurrentMonthContactInfoByTeamleadPin(Date startDate, Date endDate, String pin);

}
