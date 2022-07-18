package com.csinfotechbd.legal.report.managerial.branchWiseLegalExpenses;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface BranchWiseLegalExpensesRepository extends JpaRepository<LitigationCaseInfo, Long> {

    /*@Query(value = "" +
            "SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)            AS CASE_TYPE, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM) AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                           AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                 AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000     AS SUIT_VALUE, " +
            "       LCI.LEGAL_EXPENSE / 100000                                                AS LEGAL_EXPENSE " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND LCI.BRANCH_CODE = :branchCode " +
            "ORDER BY CASE_TYPE, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("branchCode") String branchCode);*/

    @Query(value = "" +
            "SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)            AS CASE_TYPE, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM) AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                           AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                 AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000     AS SUIT_VALUE, " +
            "       LCI.TOTAL_LEGAL_EXPENSE_AMOUNT / 100000                                                AS LEGAL_EXPENSE " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND LCI.BRANCH_CODE = :branchCode " +
            "ORDER BY CASE_TYPE, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("branchCode") String branchCode);
}
