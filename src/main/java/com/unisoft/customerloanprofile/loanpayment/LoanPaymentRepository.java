package com.unisoft.customerloanprofile.loanpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {
    List<LoanPayment> findByPaymentDate(Date startDate);

    @Query(value = "SELECT nvl(SUM(PAYMENT),0) total from LOAN_PAYMENT WHERE PAYMENT_DATE BETWEEN ?1 AND ?2 AND ACCOUNT_NO = ?3", nativeQuery = true)
    double findByPaymentDateBetweenAndAccountNo(Date startDate, Date endDate, String accountNo);

    @Query(value = "SELECT nvl(SUM(PAYMENT),0) total from LOAN_PAYMENT WHERE PAYMENT_DATE>= ?1 AND ACCOUNT_NO= ?2", nativeQuery = true)
    double findByPaymentDateBetweenAndAccountNo(Date startDate, String accountNo);
    
    //@Query(value = "")

    LoanPayment findFirstByAccountNoOrderByIdDesc(String accountNo);

    List<LoanPayment> findByAccountNoAndPaymentDateIsAfter(String accountNo, Date startDate);

    @Query(value = "SELECT SUM(LPD.PAYMENT) AS lastMonthPayment, " +
            "(SELECT SUM(LPD2.PAYMENT) FROM LOAN_PAYMENT LPD2 WHERE LPD2.ACCOUNT_NO = LPD.ACCOUNT_NO) AS cumulativePayment " +
            "FROM LOAN_PAYMENT LPD " +
            "WHERE LPD.ACCOUNT_NO = ?1 AND LPD.PAYMENT_DATE BETWEEN ?2 AND ?3 " +
            "GROUP BY LPD.ACCOUNT_NO", nativeQuery = true)
    Tuple getLoanPaymentSummaryByAccount(String accountNo, Date startDate, Date endDate);

    @Query(value = "SELECT LABI.ACCOUNT_NO, LP.PAYMENT_DATE " +
            "FROM LOAN_ACCOUNT_BASIC_INFO LABI " +
            "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "         ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM') AND " +
            "            LADI.LATEST = '1' " +
            "       LEFT JOIN (SELECT ACCOUNT_NO, MAX(PAYMENT_DATE) AS PAYMENT_DATE FROM LOAN_PAYMENT GROUP BY ACCOUNT_NO) LP " +
            "         ON LP.ACCOUNT_NO = LABI.ACCOUNT_NO", nativeQuery = true)
    List<Tuple> getLatestPaymentDetailsOfCurrentlyDistributedAccounts();

}
