package com.csinfotechbd.customerloanprofile.legalAction;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LegalActionRepository extends JpaRepository<LitigationCaseInfo, Long> {

   /* @Query(value = "SELECT LCI.CASE_NUMBER    as caseNumber, " +
            "       LCI.DATE_OF_FILING as dateOfFiling, " +
            "       LCI.SUIT_VALUE     as suitValue, " +
            "       CT.NAME            as caseType, " +
            "       LCI.NEXT_DATE      as nextDate, " +
            "       LCI.TOTAL_LEGAL_EXPENSE_AMOUNT  as legalExpense, " +
            "       COA.NAME           as courseOfAction " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "WHERE LCI.CUSTOMER_ACC_NUM = ? ", nativeQuery = true)
    List<Tuple> findLegalActionByAccountNo(String accountNo);*/

    @Query(value = "SELECT LCI.CASE_NUMBER    as caseNumber, " +
            "       LCI.DATE_OF_FILING as dateOfFiling, " +
            "       LCI.SUIT_VALUE     as suitValue, " +
            "       CT.NAME            as caseType, " +
            "       LCI.NEXT_DATE      as nextDate, " +
            "       LCI.TOTAL_LEGAL_EXPENSE_AMOUNT  as legalExpense, " +
            "       LCI.COURSE_OF_ACTION_NAMES           as courseOfAction " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "WHERE LCI.CUSTOMER_ACC_NUM = ? ", nativeQuery = true)
    List<Tuple> findLegalActionByAccountNo(String accountNo);
}
