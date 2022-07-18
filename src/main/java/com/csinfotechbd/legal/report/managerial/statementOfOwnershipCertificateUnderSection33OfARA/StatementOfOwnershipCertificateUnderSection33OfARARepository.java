package com.csinfotechbd.legal.report.managerial.statementOfOwnershipCertificateUnderSection33OfARA;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface StatementOfOwnershipCertificateUnderSection33OfARARepository extends JpaRepository<LitigationCaseInfo, Long> {
/*
    @Query(value = "" +
            "SELECT LCI.BRANCH_NAME                                                                    AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM)          AS ACCOUNT_NUMBER, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_COLLATERAL_SECURITY, CS.NAME)           AS TYPE_OF_PROPERTY, " +
            "       LCI.COLL_SECURITY_DISTRICT                                                         AS PROPERTY_DISTRICT, " +
            "       LCI.COLL_SECURITY_MOUZA                                                            AS PROPERTY_MOUZA, " +
            "       LCI.COLL_SECURITY_SRO                                                              AS PROPERTY_SRO, " +
            "       LCI.COLL_SECURITY_PS                                                               AS PROPERTY_PS, " +
            "       LCI.FORCED_SALE_VALUE                                                              AS FORCED_SALE_VALUE, " +
            "       LCI.CASE_NUMBER                                                                    AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                          AS DATE_OF_FILING, " +
            "       DECODE(COALESCE(LCI.SUIT_VALUE, 0), 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       TO_CHAR(LCI.DEED_DATE, 'DD.MM.YYYY')                                               AS CERTIFICATE_OBTAINING_DATE, " +
            "       TO_CHAR(LCI.REGISTRATION_DATE, 'DD.MM.YYYY')                                       AS REGISTRATION_DATE, " +
            "       TO_CHAR(LCI.MUTATION_DATE, 'DD.MM.YYYY')                                           AS MUTATION_DATE, " +
            "       LCI.REMARKS                                                                        AS REMARKS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID AND CT.NAME = 'Artharin Execution' " +
            "       JOIN COURSE_OF_ACTION COA ON LCI.COURSE_OF_ACTION_ID = COA.ID AND COA.NAME = :courseOfAction " +
            "       LEFT JOIN COLLATERAL_SECURITY CS on LCI.COLLATERAL_SECURITY_ID = CS.ID " +
            "ORDER BY BRANCH_NAME, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("courseOfAction") String courseOfAction);*/



    @Query(value = "" +
            " with collateralSequrityTable as (select LITIGATION_CASE_INFO_ID, COLL_SECURITY_DISTRICT as COLL_SECURITY_DISTRICT,  COLL_SECURITY_MOUZA as COLL_SECURITY_MOUZA, "+
            " COLLATERAL_SECURITY_NAME AS COLLATERAL_SECURITY_NAME,  COLL_SECURITY_SRO as COLL_SECURITY_SRO, COLL_SECURITY_PS as COLL_SECURITY_PS, OTHER_COLLATERAL_SECURITY AS OTHER_COLLATERAL_SECURITY "+
            " from CASE_ENTRY_COLLATERAL_DETAILS group by LITIGATION_CASE_INFO_ID, COLL_SECURITY_DISTRICT, COLL_SECURITY_MOUZA, COLL_SECURITY_SRO,COLL_SECURITY_PS,COLLATERAL_SECURITY_NAME,OTHER_COLLATERAL_SECURITY), "+
            " countCourseOfAction AS (select litigation_case_info_id, mutation_date as mutation_date, deed_date as deed_date, "+
            " registration_date as registration_date, course_of_action_name AS course_of_action_name from LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS bla "+
            " group by litigation_case_info_id, course_of_action_name, mutation_date, deed_date,registration_date), "+
            "certificateTable as (SELECT LCI.BRANCH_NAME                                                                    AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM)          AS ACCOUNT_NUMBER, " +
            "       CS.COLLATERAL_SECURITY_NAME                                                         AS TYPE_OF_PROPERTY, " +
            "       CS.COLL_SECURITY_DISTRICT                                                         AS PROPERTY_DISTRICT, " +
            "       CS.COLL_SECURITY_MOUZA                                                            AS PROPERTY_MOUZA, " +
            "       CS.COLL_SECURITY_SRO                                                              AS PROPERTY_SRO, " +
            "       CS.COLL_SECURITY_PS                                                               AS PROPERTY_PS, " +
            "       CS.OTHER_COLLATERAL_SECURITY                                                      AS OTHER_COLLATERAL_SECURITY, "+
            "       LCI.TOTAL_FORCED_SALE_VALUE/100000                                                            AS FORCED_SALE_VALUE, " +
            "       LCI.CASE_NUMBER                                                                    AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                          AS DATE_OF_FILING, " +
            "       DECODE(COALESCE(LCI.SUIT_VALUE, 0), 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       TO_CHAR(coa.deed_date, 'DD.MM.YYYY')                                               AS CERTIFICATE_OBTAINING_DATE, " +
            "       TO_CHAR(coa.registration_date, 'DD.MM.YYYY')                                       AS REGISTRATION_DATE, " +
            "       TO_CHAR(coa.mutation_date, 'DD.MM.YYYY')                                           AS MUTATION_DATE, " +
            "       LCI.REMARKS                                                                        AS REMARKS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID AND CT.NAME = 'Artharin Execution' " +
            "       LEFT JOIN countCourseOfAction coa on coa.litigation_case_info_id = LCI.ID " +
            "       LEFT JOIN collateralSequrityTable CS on CS.LITIGATION_CASE_INFO_ID = LCI.ID " +
            " WHERE coa.course_of_action_name = ?1 "+
            " ORDER BY BRANCH_NAME, LCI.DATE_OF_FILING ASC) "+
            " select * from certificateTable", nativeQuery = true)
    List<Tuple> getReport(@Param("courseOfAction") String courseOfAction);

}
