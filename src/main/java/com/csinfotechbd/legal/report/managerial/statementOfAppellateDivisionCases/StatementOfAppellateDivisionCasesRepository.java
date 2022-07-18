package com.csinfotechbd.legal.report.managerial.statementOfAppellateDivisionCases;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface StatementOfAppellateDivisionCasesRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "" +
            "SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME) AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, " +
            "              LCI.CUSTOMER_ACC_NUM)                                   AS ACCOUNT_NUMBER, " +
            "       LCI.PETITIONER_NAME                                            AS PETITIONER_NAME, " +
            "       LCI.SUBJECT_MATTER_OF_CASE                                     AS SUBJECT_MATTER_OF_CASE, " +
            "       TO_CHAR(LCI.FIRST_ORDER_DATE, 'DD.MM.YYYY')                    AS FIRST_ORDER_DATE, " +
            "       LCI.CASE_NUMBER                                                AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                      AS DATE_OF_FILING, " +
            "       DECODE(COALESCE(LCI.SUIT_VALUE, 0), 0, " +
            "              DECODE(COALESCE(LCI.CHEQUE_AMOUNT, 0), 0, COALESCE(LCI.OUTSTANDING, 0), " +
            "                     LCI.CHEQUE_AMOUNT), LCI.SUIT_VALUE) / 100000     AS SUIT_VALUE, " +
            "       L.NAME                                                         AS LAWYER_NAME, " +
            "       L.MOBILE_NO                                                    AS LAWYER_MOBILE_NO, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)    AS CASE_STATUS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       JOIN CASE_FILED_SUB_TYPE CFST on CT.CASE_FILED_SUB_TYPE_ID = CFST.ID AND " +
            "                                        REGEXP_REPLACE(LOWER(CFST.NAME), '\\W+', '') = " +
            "                                        REGEXP_REPLACE(LOWER('Appellate Division'), '\\W+', '') " +
            "       LEFT JOIN CASE_STATUS CS ON LCI.STATUS_ID = CS.ID " +
            "       LEFT JOIN (SELECT LCIL.LITIGATION_CASE_INFO_ID                                                    AS ID, " +
            "                         LISTAGG(L.NAME || ' (' || L.MOBILE_NO || ')', ', ') WITHIN GROUP (ORDER BY L.ID) AS NAME, " +
            "                         LISTAGG(L.MOBILE_NO, ', ') WITHIN GROUP (ORDER BY L.ID)                         AS MOBILE_NO " +
            "                  FROM LITIGATION_CASE_INFO_LAWYER LCIL " +
            "                         JOIN LAWYER L ON LCIL.LAWYER_ID = L.ID " +
            "                  GROUP BY LCIL.LITIGATION_CASE_INFO_ID) L ON LCI.ID = L.ID " +
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND LOWER(LCI.BY_WHOM_FILED) = LOWER(:byWhomeFiled) " +
            "  AND ((LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD')) OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:startDate, '___') OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:endDate, '___') )" +
            "ORDER BY LCI.BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("byWhomeFiled") String byWhomeFiled, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "" +
            "SELECT CT.ID, CT.NAME " +
            "FROM CASE_TYPE CT " +
            "       JOIN CASE_FILED_SUB_TYPE CFST on CT.CASE_FILED_SUB_TYPE_ID = CFST.ID " +
            "WHERE REGEXP_REPLACE(LOWER(CFST.NAME), '\\W+', '') = REGEXP_REPLACE(LOWER(:caseFiledSubType), '\\W+', '')", nativeQuery = true)
    List<Tuple> getCaseTypesBySubType(@Param("caseFiledSubType") String caseFiledSubType);
}
