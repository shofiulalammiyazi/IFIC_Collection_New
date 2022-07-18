package com.csinfotechbd.legal.report.managerial.statementOfClientWiseCaseDetails;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface StatementOfClientWiseCaseDetailsRepository extends JpaRepository<LitigationCaseInfo, Long> {

   /* @Query(value = "" +
            "SELECT LCI.BRANCH_NAME                                                                                AS BRANCH_NAME, " +
            "       LCI.CUSTOMER_ACC_NUM                                                                           AS ACCOUNT_NUMBER, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                                 AS CASE_TYPE, " +
            "       LCI.CASE_NUMBER                                                                                AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                                      AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, NULL, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD.MM.YYYY')                                                           AS NEXT_DATE, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)                                    AS STATUS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "WHERE LCI.BRANCH_NAME IN(:branches) " +
            "  AND (LCI.CLIENT_ID LIKE '%' || :clientId || '%' " +
            "         OR CUSTOMER_CIF_NO LIKE '%' || :clientId || '%') " +
            "ORDER BY BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("clientId") String clientId, @Param("branches") List<String> branches);*/

   /* @Query(value = "" +
            "WITH tableList AS (SELECT BLA.NEXT_DATE, ROW_NUMBER() OVER (ORDER BY bla.next_date desc) rowno, LCI.BRANCH_NAME      AS BRANCH_NAME, " +
            "       LCI.CUSTOMER_ACC_NUM                                                                           AS ACCOUNT_NUMBER, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                                 AS CASE_TYPE, " +
            "       LCI.CASE_NUMBER                                                                                AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                                      AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, NULL, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)                                    AS STATUS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            " LEFT JOIN litigation_case_info_bla_attendance_history_list LTBL on LTBL.litigation_case_info_id = LCI.ID "+
            " LEFT JOIN LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS BLA ON BLA.ID = LTBL.bla_attendance_history_list_id "+
            "WHERE LCI.BRANCH_NAME IN(:branches) " +
            "  AND (LCI.CLIENT_ID LIKE '%' || :clientId || '%' " +
            "         OR CUSTOMER_CIF_NO LIKE '%' || :clientId || '%') " +
            "ORDER BY BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC) "+
            "select d1.DATE_OF_FILING, d1.ACCOUNT_NUMBER, d1.SUIT_VALUE, d1.STATUS, d1.BRANCH_NAME, d1.CASE_TYPE, d1.CASE_NUMBER, to_char(d1.next_date, 'DD.MM.YYYY') as NEXT_DATE "+
            "from tableList d1 "+
            "left join tableList d2 "+
            "on d1.rowno+1 = d2.rowno "+
            "where d1.rowno = 1 "
            , nativeQuery = true)
    List<Tuple> getReport(@Param("clientId") String clientId, @Param("branches") List<String> branches);
    */

     @Query(value = "" +
            "SELECT LCI.BRANCH_NAME                                                                                AS BRANCH_NAME, " +
            "       LCI.CUSTOMER_ACC_NUM                                                                           AS ACCOUNT_NUMBER, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                                 AS CASE_TYPE, " +
            "       LCI.CASE_NUMBER                                                                                AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                                      AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, NULL, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD.MM.YYYY')                                                           AS NEXT_DATE, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)                                    AS STATUS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "WHERE LCI.BRANCH_CODE IN(:branches) " +
            "  AND (LCI.CLIENT_ID LIKE '%' || :clientId || '%' " +
            "         OR CUSTOMER_CIF_NO LIKE '%' || :clientId || '%') " +
            "ORDER BY BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("clientId") String clientId, @Param("branches") List<String> branches);


    @Query(value = "SELECT DECODE(CUSTOMER_CIF_NO, NULL, CLIENT_ID, CUSTOMER_CIF_NO) AS CIF " +
            "FROM LITIGATION_CASE_INFO " +
            "WHERE CUSTOMER_CIF_NO IS NOT NULL " +
            "   OR CLIENT_ID IS NOT NULL " +
            "ORDER BY CUSTOMER_CIF_NO", nativeQuery = true)
    List<String> getCifNumbers();

}
