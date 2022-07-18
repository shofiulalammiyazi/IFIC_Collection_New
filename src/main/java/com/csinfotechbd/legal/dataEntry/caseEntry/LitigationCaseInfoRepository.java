package com.csinfotechbd.legal.dataEntry.caseEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

public interface LitigationCaseInfoRepository extends JpaRepository<LitigationCaseInfo, Long>, RevisionRepository<LitigationCaseInfo, Long, Integer> {

    @Query(value = "" +
            "SELECT NAME || ' > ' || TO_CHAR(MODIFIED_DATE, 'DD/MON/YYYY HH:MI AM') AS MODIFIED_DATE " +
            "FROM (SELECT L.NAME, MAX(LCIA.MODIFIED_DATE) AS MODIFIED_DATE " +
            "      FROM LITIGATION_CASE_INFO_LAWYER_AUD LCILA " +
            "             JOIN LAWYER L ON L.ID = LCILA.LAWYER_ID " +
            "             JOIN LITIGATION_CASE_INFO_AUD LCIA ON LCILA.REV = LCIA.REV AND LCIA.MODIFIED_DATE IS NOT NULL " +
            "      WHERE LCILA.LITIGATION_CASE_INFO_ID = :id " +
            "        AND LCILA.REVTYPE = 2 " +
            "      GROUP BY L.NAME)", nativeQuery = true)
    List<String> getLawyerChangeHistory(@Param("id") Long id);

    @Query(value = "SELECT LCI.ID                                                                   AS ID, " +
            "            DECODE(LOWER(CFT.NAME), 'the supreme court of bangladesh', LCI.BY_WHOM_FILED, " +
            "                   CFT.NAME)                                                         AS CASE_FILED, " +
            "            CFST.NAME                                                                AS CASE_FILED_SUB_TYPE, " +
            "            DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)           AS CASE_TYPE, " +
            "            DECODE(LOWER(LCI.CASE_TYPE_SUB_TYPE), 'others', LCI.OTHER_CASE_TYPE_SUB_TYPE, " +
            "                   LCI.CASE_TYPE_SUB_TYPE)                                           AS CASE_TYPE_SUB_TYPE, " +
            "            LCI.DIVISION                                                             AS DIVISION, " +
            "            LCI.DISTRICT                                                             AS DISTRICT, " +
            "            LCI.ZONE                                                                 AS ZONE, " +
            "            LCI.COURT_JURISDICTION                                                   AS COURT_JURISDICTION, " +
            "            LCI.LD_NO                                                                AS LD_NO, " +
            "            LCI.BRANCH_NAME                                                          AS BRANCH_NAME, " +
            "            LCI.NAME_OF_ACC                                                          AS NAME_OF_ACC, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.ACCUSED_NAME, " +
            "                  DECODE(LOWER(CT.NAME), 'artharin suit', LCI.ACCUSED_NAME, ''))     AS BORROWER_NAME, " +
            "            LCI.ACCUSED_NAME                                                         AS ACCUSED_NAME, " +
            "            LCI.BUSINESS_SEGMENT                                                     AS BUSINESS_SEGMENT, " +
            "            LCI.CUSTOMER_CIF_NO                                                      AS CUSTOMER_CIF_NO, " +
            "            LCI.CLIENT_ID                                                            AS CLIENT_ID, " +
            "            LCI.CUSTOMER_ACC_NUM                                                     AS CUSTOMER_ACC_NUM, " +
            "            COALESCE(LCI.PLAINTIFF_NAME, LCI.PETITIONER_NAME)                        AS PLAINTIFF_NAME, " +
            "            LCI.PLAINTIFF_DESIGNATION                                                AS PLAINTIFF_DESIGNATION, " +
            "            LCI.PLAINTIFF_PHONE_NO                                                   AS PLAINTIFF_PHONE_NO, " +
            "            DECODE(LCI.DEFENDANT_NAME, NULL, " +
            "                   DECODE(LCI.OTHER_DEFENDANT_NAME, NULL, LCI.OPPOSITE_PARTY_NAME, LCI.OTHER_DEFENDANT_NAME), " +
            "                   LCI.DEFENDANT_NAME)                                               AS DEFENDANT_NAME, " +
            "            LCI.CASE_NUMBER                                                          AS CASE_NUMBER, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'), " +
            "                   TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'))                        AS DATE_OF_FILING, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'), " +
            "                   '-')                                                              AS DATE_OF_FILING_EX, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.SUIT_VALUE, " +
            "                   LCI.SUIT_VALUE)                                                   AS SUIT_VALUE, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.SUIT_VALUE, " +
            "                   0)                                                                AS SUIT_VALUE_EX, " +
            "            SLT.PREVIOUS_DATE                                                        AS PREVIOUS_DATE, " +
            "            SLT.PREVIOUS_COURSE_OF_ACTION                                            AS PREVIOUS_COURSE_OF_ACTION, " +
            "            BLA_HISTORY.NEXT_DATE                                                    AS NEXT_DATE, " +
            "            DECODE(COA.NAME, 'Others', LCI.OTHER_COURSE_OF_ACTION, COA.NAME)         AS NEXT_COURSE_OF_ACTION, " +
            "            DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)                 AS COURT, " +
            "            DECODE(LCI.WRITTEN_OFF, NULL, 'No', 0, 'No', 'Yes')                      AS WRITTEN_OFF, " +
            "            COALESCE(LCI.TOTAL_LEGAL_EXPENSE_AMOUNT, 0)                                           AS TOTAL_LEGAL_EXPENSE_AMOUNT, " +
            "            L.NAME                                                                   AS LAWYER, " +
            "            COALESCE(L.MOBILE_NO, L.PHONE_NO)                                        AS LAWYER_CELL_NUMBER, " +
            "            colsec.collateral_security as collateral_security, " +
            "            BLA_HISTORY.COURSE_OF_ACTION_NAME                                        AS COURSE_OF_ACTION_NAME, " +
            "            COALESCE(LCI.MARKET_VALUE, 0)                                            AS MARKET_VALUE, " +
            "            COALESCE(LCI.FORCED_SALE_VALUE, 0)                                       AS FORCED_SALE_VALUE, " +
            "            LCI.ASSESED_BY                                                           AS ASSESED_BY, " +
            "            DECODE(LCI.UNDER_SOLENAMA, NULL, 'No', 0, 'No', 'Yes')                   AS UNDER_SOLENAMA, " +
            "            COALESCE(LCI.RECOVERY_BEFORE_CASE_AMOUNT, 0)                             AS RECOVERY_BEFORE_CASE_AMOUNT, " +
            "            COALESCE(LCI.RECOVERY_AFTER_CASE_AMOUNT, 0)                              AS RECOVERY_AFTER_CASE_AMOUNT, " +
            "            LCI.TYPE_OF_FRAUD                                                        AS TYPE_OF_FRAUD, " +
            "            LCI.TECHNIQUES_TOFRAUD                                                   AS TECHNIQUES_TOFRAUD, " +
            "            TO_CHAR(LCI.DATE_OF_OCCURRENCE, 'DD-MM-YYYY')                            AS DATE_OF_OCCURRENCE, " +
            "            TO_CHAR(LCI.DATE_OF_DETECTION, 'DD-MM-YYYY')                             AS DATE_OF_DETECTION, " +
            "            COALESCE(LCI.ADJUSTMENT_OF_DEFRAUD_AMOUNT, 0)                            AS ADJUSTMENT_OF_DEFRAUD_AMOUNT, " +
            "            LCI.NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED                              AS NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED, " +
            "            LCI.ACTION_AGAINST_DELINQUENT                                            AS ACTION_AGAINST_DELINQUENT, " +
            "            LCI.INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE                           AS INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE, " +
            "            LCI.SECTION                                                              AS SECTION, " +
            "            LCI.COMMENT_IMPACT_ON_BANK                                               AS COMMENT_IMPACT_ON_BANK, " +
            "            LCI.SUBJECT_MATTER_OF_CASE                                               AS SUBJECT_MATTER_OF_CASE, " +
            "            TO_CHAR(LCI.FIRST_ORDER_DATE, 'DD-MM-YYYY')                              AS FIRST_ORDER_DATE, " +
            "            DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)              AS STATUS " +
            "            FROM LITIGATION_CASE_INFO LCI " +
            "            JOIN CASE_FILED_TYPE CFT on LCI.CASE_FILED_ID = CFT.ID " +
            "            JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID " +
            "            LEFT JOIN CASE_FILED_SUB_TYPE CFST on LCI.CASE_FILED_SUB_TYPE_ID = CFST.ID " +
            "            LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "            LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCIL on LCI.ID = LCIL.LITIGATION_CASE_INFO_ID " +
            "            LEFT JOIN LAWYER L ON LCIL.LAWYER_ID = L.ID " +
            "            LEFT JOIN ( " +
            "              WITH BLA_HISTORY AS ( " +
            "                  SELECT BAH.* " +
            "                  FROM LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS BAH " +
            "                  where BAH.ENABLED = 1 and BAH.DELETED = 0 ORDER BY CREATED_DATE DESC) " +
            "                SELECT BLA_HISTORY.LITIGATION_CASE_INFO_ID AS ID, " +
            "                       LISTAGG(TO_CHAR(BLA_HISTORY.NEXT_DATE, 'DD-MM-YYYY'),',') WITHIN GROUP (ORDER BY BLA_HISTORY.CREATED_DATE) AS NEXT_DATE, " +
            "                       LISTAGG(BLA_HISTORY.COURSE_OF_ACTION_NAME,',') WITHIN GROUP (ORDER BY BLA_HISTORY.CREATED_DATE) AS COURSE_OF_ACTION_NAME " +
            "                FROM BLA_HISTORY GROUP BY BLA_HISTORY.LITIGATION_CASE_INFO_ID " +
            "            ) BLA_HISTORY ON BLA_HISTORY.ID = lci.id " +
            "            LEFT JOIN (SELECT lcicsl.litigation_case_info_id AS id, " +
            "                              LISTAGG(COLLS.name, ', ') WITHIN GROUP (ORDER BY COLLS.name) as COLLATERAL_SECURITY " +
            "                       FROM CASE_ENTRY_COLLATERAL_DETAILS LCICSL " +
            "                              LEFT JOIN collateral_security COLLS on COLLS.id = LCICSL.COLLATERAL_SECURITY_ID " +
            "                       where LCICSL.ENABLED = 1 and LCICSL.DELETED = 0 " +
            "                       GROUP BY lcicsl.litigation_case_info_id) colsec ON colsec.id = lci.id " +
            "            LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "            LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "            LEFT JOIN (SELECT LT.ID, " +
            "                              MAX((SELECT TO_CHAR(NEXT_DATE, 'DD-MM-YYYY') " +
            "                                   FROM LITIGATION_CASE_INFO_AUD " +
            "                                   WHERE id = LT.ID " +
            "                                   ORDER BY REV DESC " +
            "                                   OFFSET 1 ROW " +
            "                                   FETCH FIRST ROW ONLY)) AS PREVIOUS_DATE, " +
            "                              MAX((SELECT DECODE(COA_IN.NAME, 'Others', LCIA_IN.OTHER_COURSE_OF_ACTION, COA_IN.NAME) " +
            "                                   FROM LITIGATION_CASE_INFO_AUD LCIA_IN " +
            "                                          LEFT JOIN COURSE_OF_ACTION COA_IN ON LCIA_IN.COURSE_OF_ACTION_ID = COA_IN.ID " +
            "                                   WHERE LCIA_IN.id = LT.ID " +
            "                                   ORDER BY LCIA_IN.REV DESC " +
            "                                   OFFSET 1 ROW " +
            "                                   FETCH FIRST ROW ONLY)) AS PREVIOUS_COURSE_OF_ACTION " +
            "                       FROM LITIGATION_CASE_INFO_AUD LT " +
            "                       GROUP BY LT.ID)SLT ON SLT.ID = LCI.ID " +
            "            WHERE LCI.ENABLED = 1 AND LCI.BRANCH_CODE = :getBranch", nativeQuery = true)
    List<Tuple> getActiveListWithCommonColumns(@Param("getBranch") String getBranch);

    /*@Query(value = "" +
            "SELECT LCI.ID                                                                   AS ID, " +
            "       DECODE(LOWER(CFT.NAME), 'the supreme court of bangladesh', LCI.BY_WHOM_FILED, " +
            "              CFT.NAME)                                                         AS CASE_FILED, " +
            "       CFST.NAME                                                                AS CASE_FILED_SUB_TYPE, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)           AS CASE_TYPE, " +
            "       DECODE(LOWER(LCI.CASE_TYPE_SUB_TYPE), 'others', LCI.OTHER_CASE_TYPE_SUB_TYPE, " +
            "              LCI.CASE_TYPE_SUB_TYPE)                                           AS CASE_TYPE_SUB_TYPE, " +
            "       LCI.DIVISION                                                             AS DIVISION, " +
            "       LCI.DISTRICT                                                             AS DISTRICT, " +
            "       LCI.ZONE                                                                 AS ZONE, " +
            "       LCI.COURT_JURISDICTION                                                   AS COURT_JURISDICTION, " +
            "       LCI.LD_NO                                                                AS LD_NO, " +
            "       LCI.BRANCH_NAME                                                          AS BRANCH_NAME, " +
            "       LCI.NAME_OF_ACC                                                          AS NAME_OF_ACC, " +
            "       LCI.BORROWER_NAME                                                        AS BORROWER_NAME, " +
            "       LCI.ACCUSED_NAME                                                         AS ACCUSED_NAME, " +
            "       LCI.BUSINESS_SEGMENT                                                     AS BUSINESS_SEGMENT, " +
            "       LCI.CUSTOMER_CIF_NO                                                      AS CUSTOMER_CIF_NO, " +
            "       LCI.CLIENT_ID                                                            AS CLIENT_ID, " +
            "       LCI.CUSTOMER_ACC_NUM                                                     AS CUSTOMER_ACC_NUM, " +
            "       COALESCE(LCI.PLAINTIFF_NAME, LCI.PETITIONER_NAME)                        AS PLAINTIFF_NAME, " +
            "       LCI.PLAINTIFF_DESIGNATION                                                AS PLAINTIFF_DESIGNATION, " +
            "       LCI.PLAINTIFF_PHONE_NO                                                   AS PLAINTIFF_PHONE_NO, " +
            "       DECODE(LCI.DEFENDANT_NAME, NULL, " +
            "              DECODE(LCI.OTHER_DEFENDANT_NAME, NULL, LCI.OPPOSITE_PARTY_NAME, LCI.OTHER_DEFENDANT_NAME), " +
            "              LCI.DEFENDANT_NAME)                                               AS DEFENDANT_NAME, " +
            "       LCI.CASE_NUMBER                                                          AS CASE_NUMBER, " +
            "       DECODE(LOWER(CT.NAME), 'artharin execution', TO_CHAR(LCI.DATE_OF_FILINGAR, 'DD-MM-YYYY'), " +
            "              TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'))                        AS DATE_OF_FILING, " +
            "       DECODE(LOWER(CT.NAME), 'artharin execution', TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'), " +
            "              '-')                                                              AS DATE_OF_FILING_EX, " +
            "       DECODE(LOWER(CT.NAME), 'artharin execution', LCI.SUIT_VALUE_AR, " +
            "              LCI.SUIT_VALUE)                                                   AS SUIT_VALUE, " +
            "       DECODE(LOWER(CT.NAME), 'artharin execution', LCI.SUIT_VALUE, " +
            "              0)                                                                AS SUIT_VALUE_EX, " +
            "       SLT.PREVIOUS_DATE                                                        AS PREVIOUS_DATE, " +
            "       SLT.PREVIOUS_COURSE_OF_ACTION                                            AS PREVIOUS_COURSE_OF_ACTION, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD-MM-YYYY')                                     AS NEXT_DATE, " +
            "       DECODE(COA.NAME, 'Others', LCI.OTHER_COURSE_OF_ACTION, COA.NAME)         AS NEXT_COURSE_OF_ACTION, " +
            "       DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)                 AS COURT, " +
            "       DECODE(LCI.WRITTEN_OFF, NULL, 'No', 0, 'No', 'Yes')                      AS WRITTEN_OFF, " +
            "       COALESCE(LCI.LEGAL_EXPENSE, 0)                                           AS LEGAL_EXPENSE, " +
            "       L.NAME                                                                   AS LAWYER, " +
            "       COALESCE(L.MOBILE_NO, L.PHONE_NO)                                        AS LAWYER_CELL_NUMBER, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_COLLATERAL_SECURITY, CS.NAME) AS COLLATERAL_SECURITY, " +
            "       COALESCE(LCI.MARKET_VALUE, 0)                                            AS MARKET_VALUE, " +
            "       COALESCE(LCI.FORCED_SALE_VALUE, 0)                                       AS FORCED_SALE_VALUE, " +
            "       LCI.ASSESED_BY                                                           AS ASSESED_BY, " +
            "       DECODE(LCI.UNDER_SOLENAMA, NULL, 'No', 0, 'No', 'Yes')                   AS UNDER_SOLENAMA, " +
            "       COALESCE(LCI.RECOVERY_BEFORE_CASE_AMOUNT, 0)                             AS RECOVERY_BEFORE_CASE_AMOUNT, " +
            "       COALESCE(LCI.RECOVERY_AFTER_CASE_AMOUNT, 0)                              AS RECOVERY_AFTER_CASE_AMOUNT, " +
            "       LCI.TYPE_OF_FRAUD                                                        AS TYPE_OF_FRAUD, " +
            "       LCI.TECHNIQUES_TOFRAUD                                                   AS TECHNIQUES_TOFRAUD, " +
            "       TO_CHAR(LCI.DATE_OF_OCCURRENCE, 'DD-MM-YYYY')                            AS DATE_OF_OCCURRENCE, " +
            "       TO_CHAR(LCI.DATE_OF_DETECTION, 'DD-MM-YYYY')                             AS DATE_OF_DETECTION, " +
            "       COALESCE(LCI.ADJUSTMENT_OF_DEFRAUD_AMOUNT, 0)                            AS ADJUSTMENT_OF_DEFRAUD_AMOUNT, " +
            "       LCI.NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED                              AS NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED, " +
            "       LCI.ACTION_AGAINST_DELINQUENT                                            AS ACTION_AGAINST_DELINQUENT, " +
            "       LCI.INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE                           AS INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE, " +
            "       LCI.SECTION                                                              AS SECTION, " +
            "       LCI.COMMENT_IMPACT_ON_BANK                                               AS COMMENT_IMPACT_ON_BANK, " +
            "       LCI.SUBJECT_MATTER_OF_CASE                                               AS SUBJECT_MATTER_OF_CASE, " +
            "       TO_CHAR(LCI.FIRST_ORDER_DATE, 'DD-MM-YYYY')                              AS FIRST_ORDER_DATE, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)              AS STATUS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_FILED_TYPE CFT on LCI.CASE_FILED_ID = CFT.ID " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID " +
            "       LEFT JOIN CASE_FILED_SUB_TYPE CFST on LCI.CASE_FILED_SUB_TYPE_ID = CFST.ID " +
            "       LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCIL on LCI.ID = LCIL.LITIGATION_CASE_INFO_ID " +
            "       LEFT JOIN LAWYER L ON LCIL.LAWYER_ID = L.ID " +
            "       LEFT JOIN COLLATERAL_SECURITY CS ON LCI.COLLATERAL_SECURITY_ID = CS.ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "       LEFT JOIN (SELECT LT.ID, " +
            "                         MAX((SELECT TO_CHAR(NEXT_DATE, 'DD-MM-YYYY') " +
            "                              FROM LITIGATION_CASE_INFO_AUD " +
            "                              WHERE id = LT.ID " +
            "                              ORDER BY REV DESC " +
            "                              OFFSET 1 ROW " +
            "                              FETCH FIRST ROW ONLY)) AS PREVIOUS_DATE, " +
            "                         MAX((SELECT DECODE(COA_IN.NAME, 'Others', LCIA_IN.OTHER_COURSE_OF_ACTION, COA_IN.NAME) " +
            "                              FROM LITIGATION_CASE_INFO_AUD LCIA_IN " +
            "                                     LEFT JOIN COURSE_OF_ACTION COA_IN ON LCIA_IN.COURSE_OF_ACTION_ID = COA_IN.ID " +
            "                              WHERE LCIA_IN.id = LT.ID " +
            "                              ORDER BY LCIA_IN.REV DESC " +
            "                              OFFSET 1 ROW " +
            "                              FETCH FIRST ROW ONLY)) AS PREVIOUS_COURSE_OF_ACTION " +
            "                  FROM LITIGATION_CASE_INFO_AUD LT " +
            "                  GROUP BY LT.ID)SLT ON SLT.ID = LCI.ID " +
            "WHERE LCI.ENABLED = 1", nativeQuery = true)*/
    /*@Query(value = "" +
            " SELECT LCI.ID                                                                   AS ID, " +
            "            DECODE(LOWER(CFT.NAME), 'the supreme court of bangladesh', LCI.BY_WHOM_FILED, " +
            "                   CFT.NAME)                                                         AS CASE_FILED, " +
            "            CFST.NAME                                                                AS CASE_FILED_SUB_TYPE, " +
            "            DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)           AS CASE_TYPE, " +
            "            DECODE(LOWER(LCI.CASE_TYPE_SUB_TYPE), 'others', LCI.OTHER_CASE_TYPE_SUB_TYPE, " +
            "                   LCI.CASE_TYPE_SUB_TYPE)                                           AS CASE_TYPE_SUB_TYPE, " +
            "            LCI.DIVISION                                                             AS DIVISION, " +
            "            LCI.DISTRICT                                                             AS DISTRICT, " +
            "            LCI.ZONE                                                                 AS ZONE, " +
            "            LCI.COURT_JURISDICTION                                                   AS COURT_JURISDICTION, " +
            "            LCI.LD_NO                                                                AS LD_NO, " +
            "            LCI.BRANCH_NAME                                                          AS BRANCH_NAME, " +
            "            LCI.NAME_OF_ACC                                                          AS NAME_OF_ACC, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.ACCUSED_NAME, " +
            "                  DECODE(LOWER(CT.NAME), 'artharin suit', LCI.ACCUSED_NAME, ''))     AS BORROWER_NAME, " +
            "            LCI.ACCUSED_NAME                                                         AS ACCUSED_NAME, " +
            "            LCI.BUSINESS_SEGMENT                                                     AS BUSINESS_SEGMENT, " +
            "            LCI.CUSTOMER_CIF_NO                                                      AS CUSTOMER_CIF_NO, " +
            "            LCI.CLIENT_ID                                                            AS CLIENT_ID, " +
            "            LCI.CUSTOMER_ACC_NUM                                                     AS CUSTOMER_ACC_NUM, " +
            "            COALESCE(LCI.PLAINTIFF_NAME, LCI.PETITIONER_NAME)                        AS PLAINTIFF_NAME, " +
            "            LCI.PLAINTIFF_DESIGNATION                                                AS PLAINTIFF_DESIGNATION, " +
            "            LCI.PLAINTIFF_PHONE_NO                                                   AS PLAINTIFF_PHONE_NO, " +
            "            DECODE(LCI.DEFENDANT_NAME, NULL, " +
            "                   DECODE(LCI.OTHER_DEFENDANT_NAME, NULL, LCI.OPPOSITE_PARTY_NAME, LCI.OTHER_DEFENDANT_NAME), " +
            "                   LCI.DEFENDANT_NAME)                                               AS DEFENDANT_NAME, " +
            "            LCI.CASE_NUMBER                                                          AS CASE_NUMBER, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'), " +
            "                   TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'))                        AS DATE_OF_FILING, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'), " +
            "                   '-')                                                              AS DATE_OF_FILING_EX, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.SUIT_VALUE, " +
            "                   LCI.SUIT_VALUE)                                                   AS SUIT_VALUE, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.SUIT_VALUE, " +
            "                   0)                                                                AS SUIT_VALUE_EX, " +
            "            SLT.PREVIOUS_DATE                                                        AS PREVIOUS_DATE, " +
            "            SLT.PREVIOUS_COURSE_OF_ACTION                                            AS PREVIOUS_COURSE_OF_ACTION, " +
            "            TO_CHAR(LCI.NEXT_DATE, 'DD-MM-YYYY')                                     AS NEXT_DATE, " +
            "            DECODE(COA.NAME, 'Others', LCI.OTHER_COURSE_OF_ACTION, COA.NAME)         AS NEXT_COURSE_OF_ACTION, " +
            "            DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)                 AS COURT, " +
            "            DECODE(LCI.WRITTEN_OFF, NULL, 'No', 0, 'No', 'Yes')                      AS WRITTEN_OFF, " +
            "            COALESCE(LCI.TOTAL_LEGAL_EXPENSE_AMOUNT, 0)                                           AS TOTAL_LEGAL_EXPENSE_AMOUNT, " +
            "            L.NAME                                                                   AS LAWYER, " +
            "            COALESCE(L.MOBILE_NO, L.PHONE_NO)                                        AS LAWYER_CELL_NUMBER, " +
            "            colsec.collateral_security as collateral_security," +
            "            COALESCE(LCI.MARKET_VALUE, 0)                                            AS MARKET_VALUE, " +
            "            COALESCE(LCI.FORCED_SALE_VALUE, 0)                                       AS FORCED_SALE_VALUE, " +
            "            LCI.ASSESED_BY                                                           AS ASSESED_BY, " +
            "            DECODE(LCI.UNDER_SOLENAMA, NULL, 'No', 0, 'No', 'Yes')                   AS UNDER_SOLENAMA, " +
            "            COALESCE(LCI.RECOVERY_BEFORE_CASE_AMOUNT, 0)                             AS RECOVERY_BEFORE_CASE_AMOUNT, " +
            "            COALESCE(LCI.RECOVERY_AFTER_CASE_AMOUNT, 0)                              AS RECOVERY_AFTER_CASE_AMOUNT, " +
            "            LCI.TYPE_OF_FRAUD                                                        AS TYPE_OF_FRAUD, " +
            "            LCI.TECHNIQUES_TOFRAUD                                                   AS TECHNIQUES_TOFRAUD, " +
            "            TO_CHAR(LCI.DATE_OF_OCCURRENCE, 'DD-MM-YYYY')                            AS DATE_OF_OCCURRENCE, " +
            "            TO_CHAR(LCI.DATE_OF_DETECTION, 'DD-MM-YYYY')                             AS DATE_OF_DETECTION, " +
            "            COALESCE(LCI.ADJUSTMENT_OF_DEFRAUD_AMOUNT, 0)                            AS ADJUSTMENT_OF_DEFRAUD_AMOUNT, " +
            "            LCI.NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED                              AS NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED, " +
            "            LCI.ACTION_AGAINST_DELINQUENT                                            AS ACTION_AGAINST_DELINQUENT, " +
            "            LCI.INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE                           AS INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE, " +
            "            LCI.SECTION                                                              AS SECTION, " +
            "            LCI.COMMENT_IMPACT_ON_BANK                                               AS COMMENT_IMPACT_ON_BANK, " +
            "            LCI.SUBJECT_MATTER_OF_CASE                                               AS SUBJECT_MATTER_OF_CASE, " +
            "            TO_CHAR(LCI.FIRST_ORDER_DATE, 'DD-MM-YYYY')                              AS FIRST_ORDER_DATE, " +
            "            DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)              AS STATUS " +
            "            FROM LITIGATION_CASE_INFO LCI " +
            "            JOIN CASE_FILED_TYPE CFT on LCI.CASE_FILED_ID = CFT.ID " +
            "            JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID " +
            "            LEFT JOIN CASE_FILED_SUB_TYPE CFST on LCI.CASE_FILED_SUB_TYPE_ID = CFST.ID " +
            "            LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "            LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCIL on LCI.ID = LCIL.LITIGATION_CASE_INFO_ID " +
            "            LEFT JOIN LAWYER L ON LCIL.LAWYER_ID = L.ID " +
            "            LEFT JOIN (SELECT lcicsl.litigation_case_info_id AS id," +
            "            LISTAGG(COLLS.name, ', ') WITHIN GROUP (ORDER BY COLLS.name) as COLLATERAL_SECURITY " +
            "            FROM CASE_ENTRY_COLLATERAL_DETAILS LCICSL " +
            "            LEFT JOIN collateral_security COLLS on COLLS.id = LCICSL.COLLATERAL_SECURITY_ID " +
            "            where LCICSL.ENABLED = 1 and LCICSL.DELETED = 0 " +
            "            GROUP BY lcicsl.litigation_case_info_id) colsec ON colsec.id = lci.id " +
            "            LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "            LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "            LEFT JOIN (SELECT LT.ID, " +
            "                              MAX((SELECT TO_CHAR(NEXT_DATE, 'DD-MM-YYYY') " +
            "                                   FROM LITIGATION_CASE_INFO_AUD " +
            "                                   WHERE id = LT.ID " +
            "                                   ORDER BY REV DESC " +
            "                                   OFFSET 1 ROW " +
            "                                   FETCH FIRST ROW ONLY)) AS PREVIOUS_DATE, " +
            "                              MAX((SELECT DECODE(COA_IN.NAME, 'Others', LCIA_IN.OTHER_COURSE_OF_ACTION, COA_IN.NAME) " +
            "                                   FROM LITIGATION_CASE_INFO_AUD LCIA_IN " +
            "                                          LEFT JOIN COURSE_OF_ACTION COA_IN ON LCIA_IN.COURSE_OF_ACTION_ID = COA_IN.ID " +
            "                                   WHERE LCIA_IN.id = LT.ID " +
            "                                   ORDER BY LCIA_IN.REV DESC " +
            "                                   OFFSET 1 ROW " +
            "                                   FETCH FIRST ROW ONLY)) AS PREVIOUS_COURSE_OF_ACTION " +
            "                       FROM LITIGATION_CASE_INFO_AUD LT " +
            "                       GROUP BY LT.ID)SLT ON SLT.ID = LCI.ID " +
            "            WHERE LCI.ENABLED = 1", nativeQuery = true)*/
    @Query(value = "SELECT LCI.ID                                                                   AS ID, " +
            "            DECODE(LOWER(CFT.NAME), 'the supreme court of bangladesh', LCI.BY_WHOM_FILED, " +
            "                   CFT.NAME)                                                         AS CASE_FILED, " +
            "            CFST.NAME                                                                AS CASE_FILED_SUB_TYPE, " +
            "            DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)           AS CASE_TYPE, " +
            "            DECODE(LOWER(LCI.CASE_TYPE_SUB_TYPE), 'others', LCI.OTHER_CASE_TYPE_SUB_TYPE, " +
            "                   LCI.CASE_TYPE_SUB_TYPE)                                           AS CASE_TYPE_SUB_TYPE, " +
            "            LCI.DIVISION                                                             AS DIVISION, " +
            "            LCI.DISTRICT                                                             AS DISTRICT, " +
            "            LCI.ZONE                                                                 AS ZONE, " +
            "            LCI.COURT_JURISDICTION                                                   AS COURT_JURISDICTION, " +
            "            LCI.LD_NO                                                                AS LD_NO, " +
            "            LCI.BRANCH_NAME                                                          AS BRANCH_NAME, " +
            "            LCI.NAME_OF_ACC                                                          AS NAME_OF_ACC, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.ACCUSED_NAME, " +
            "                  DECODE(LOWER(CT.NAME), 'artharin suit', LCI.ACCUSED_NAME, ''))     AS BORROWER_NAME, " +
            "            LCI.ACCUSED_NAME                                                         AS ACCUSED_NAME, " +
            "            LCI.BUSINESS_SEGMENT                                                     AS BUSINESS_SEGMENT, " +
            "            LCI.CUSTOMER_CIF_NO                                                      AS CUSTOMER_CIF_NO, " +
            "            LCI.CLIENT_ID                                                            AS CLIENT_ID, " +
            "            LCI.CUSTOMER_ACC_NUM                                                     AS CUSTOMER_ACC_NUM, " +
            "            COALESCE(LCI.PLAINTIFF_NAME, LCI.PETITIONER_NAME)                        AS PLAINTIFF_NAME, " +
            "            LCI.PLAINTIFF_DESIGNATION                                                AS PLAINTIFF_DESIGNATION, " +
            "            LCI.PLAINTIFF_PHONE_NO                                                   AS PLAINTIFF_PHONE_NO, " +
            "            DECODE(LCI.DEFENDANT_NAME, NULL, " +
            "                   DECODE(LCI.OTHER_DEFENDANT_NAME, NULL, LCI.OPPOSITE_PARTY_NAME, LCI.OTHER_DEFENDANT_NAME), " +
            "                   LCI.DEFENDANT_NAME)                                               AS DEFENDANT_NAME, " +
            "            LCI.CASE_NUMBER                                                          AS CASE_NUMBER, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'), " +
            "                   TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'))                        AS DATE_OF_FILING, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', TO_CHAR(LCI.DATE_OF_FILING, 'DD-MM-YYYY'), " +
            "                   '-')                                                              AS DATE_OF_FILING_EX, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.SUIT_VALUE, " +
            "                   LCI.SUIT_VALUE)                                                   AS SUIT_VALUE, " +
            "            DECODE(LOWER(CT.NAME), 'artharin execution', LCI.SUIT_VALUE, " +
            "                   0)                                                                AS SUIT_VALUE_EX, " +
            "            SLT.PREVIOUS_DATE                                                        AS PREVIOUS_DATE, " +
            "            SLT.PREVIOUS_COURSE_OF_ACTION                                            AS PREVIOUS_COURSE_OF_ACTION, " +
            "            BLA_HISTORY.NEXT_DATE                                                    AS NEXT_DATE, " +
            "            DECODE(COA.NAME, 'Others', LCI.OTHER_COURSE_OF_ACTION, COA.NAME)         AS NEXT_COURSE_OF_ACTION, " +
            "            DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)                 AS COURT, " +
            "            DECODE(LCI.WRITTEN_OFF, NULL, 'No', 0, 'No', 'Yes')                      AS WRITTEN_OFF, " +
            "            COALESCE(LCI.TOTAL_LEGAL_EXPENSE_AMOUNT, 0)                                           AS TOTAL_LEGAL_EXPENSE_AMOUNT, " +
            "            L.NAME                                                                   AS LAWYER, " +
            "            COALESCE(L.MOBILE_NO, L.PHONE_NO)                                        AS LAWYER_CELL_NUMBER, " +
            "            colsec.collateral_security as collateral_security, " +
            "            BLA_HISTORY.COURSE_OF_ACTION_NAME                                        AS COURSE_OF_ACTION_NAME, " +
            "            COALESCE(LCI.MARKET_VALUE, 0)                                            AS MARKET_VALUE, " +
            "            COALESCE(LCI.FORCED_SALE_VALUE, 0)                                       AS FORCED_SALE_VALUE, " +
            "            LCI.ASSESED_BY                                                           AS ASSESED_BY, " +
            "            DECODE(LCI.UNDER_SOLENAMA, NULL, 'No', 0, 'No', 'Yes')                   AS UNDER_SOLENAMA, " +
            "            COALESCE(LCI.RECOVERY_BEFORE_CASE_AMOUNT, 0)                             AS RECOVERY_BEFORE_CASE_AMOUNT, " +
            "            COALESCE(LCI.RECOVERY_AFTER_CASE_AMOUNT, 0)                              AS RECOVERY_AFTER_CASE_AMOUNT, " +
            "            LCI.TYPE_OF_FRAUD                                                        AS TYPE_OF_FRAUD, " +
            "            LCI.TECHNIQUES_TOFRAUD                                                   AS TECHNIQUES_TOFRAUD, " +
            "            TO_CHAR(LCI.DATE_OF_OCCURRENCE, 'DD-MM-YYYY')                            AS DATE_OF_OCCURRENCE, " +
            "            TO_CHAR(LCI.DATE_OF_DETECTION, 'DD-MM-YYYY')                             AS DATE_OF_DETECTION, " +
            "            COALESCE(LCI.ADJUSTMENT_OF_DEFRAUD_AMOUNT, 0)                            AS ADJUSTMENT_OF_DEFRAUD_AMOUNT, " +
            "            LCI.NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED                              AS NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED, " +
            "            LCI.ACTION_AGAINST_DELINQUENT                                            AS ACTION_AGAINST_DELINQUENT, " +
            "            LCI.INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE                           AS INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE, " +
            "            LCI.SECTION                                                              AS SECTION, " +
            "            LCI.COMMENT_IMPACT_ON_BANK                                               AS COMMENT_IMPACT_ON_BANK, " +
            "            LCI.SUBJECT_MATTER_OF_CASE                                               AS SUBJECT_MATTER_OF_CASE, " +
            "            TO_CHAR(LCI.FIRST_ORDER_DATE, 'DD-MM-YYYY')                              AS FIRST_ORDER_DATE, " +
            "            DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)              AS STATUS " +
            "            FROM LITIGATION_CASE_INFO LCI " +
            "            JOIN CASE_FILED_TYPE CFT on LCI.CASE_FILED_ID = CFT.ID " +
            "            JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID " +
            "            LEFT JOIN CASE_FILED_SUB_TYPE CFST on LCI.CASE_FILED_SUB_TYPE_ID = CFST.ID " +
            "            LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "            LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCIL on LCI.ID = LCIL.LITIGATION_CASE_INFO_ID " +
            "            LEFT JOIN LAWYER L ON LCIL.LAWYER_ID = L.ID " +
            "            LEFT JOIN ( " +
            "              WITH BLA_HISTORY AS ( " +
            "                  SELECT BAH.* " +
            "                  FROM LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS BAH " +
            "                  where BAH.ENABLED = 1 and BAH.DELETED = 0 ORDER BY CREATED_DATE DESC) " +
            "                SELECT BLA_HISTORY.LITIGATION_CASE_INFO_ID AS ID, " +
            "                       LISTAGG(TO_CHAR(BLA_HISTORY.NEXT_DATE, 'DD-MM-YYYY'),',') WITHIN GROUP (ORDER BY BLA_HISTORY.CREATED_DATE) AS NEXT_DATE, " +
            "                       LISTAGG(BLA_HISTORY.COURSE_OF_ACTION_NAME,',') WITHIN GROUP (ORDER BY BLA_HISTORY.CREATED_DATE) AS COURSE_OF_ACTION_NAME " +
            "                FROM BLA_HISTORY GROUP BY BLA_HISTORY.LITIGATION_CASE_INFO_ID " +
            "            ) BLA_HISTORY ON BLA_HISTORY.ID = lci.id " +
            "            LEFT JOIN (SELECT lcicsl.litigation_case_info_id AS id, " +
            "                              LISTAGG(COLLS.name, ', ') WITHIN GROUP (ORDER BY COLLS.name) as COLLATERAL_SECURITY " +
            "                       FROM CASE_ENTRY_COLLATERAL_DETAILS LCICSL " +
            "                              LEFT JOIN collateral_security COLLS on COLLS.id = LCICSL.COLLATERAL_SECURITY_ID " +
            "                       where LCICSL.ENABLED = 1 and LCICSL.DELETED = 0 " +
            "                       GROUP BY lcicsl.litigation_case_info_id) colsec ON colsec.id = lci.id " +
            "            LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "            LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "            LEFT JOIN (SELECT LT.ID, " +
            "                              MAX((SELECT TO_CHAR(NEXT_DATE, 'DD-MM-YYYY') " +
            "                                   FROM LITIGATION_CASE_INFO_AUD " +
            "                                   WHERE id = LT.ID " +
            "                                   ORDER BY REV DESC " +
            "                                   OFFSET 1 ROW " +
            "                                   FETCH FIRST ROW ONLY)) AS PREVIOUS_DATE, " +
            "                              MAX((SELECT DECODE(COA_IN.NAME, 'Others', LCIA_IN.OTHER_COURSE_OF_ACTION, COA_IN.NAME) " +
            "                                   FROM LITIGATION_CASE_INFO_AUD LCIA_IN " +
            "                                          LEFT JOIN COURSE_OF_ACTION COA_IN ON LCIA_IN.COURSE_OF_ACTION_ID = COA_IN.ID " +
            "                                   WHERE LCIA_IN.id = LT.ID " +
            "                                   ORDER BY LCIA_IN.REV DESC " +
            "                                   OFFSET 1 ROW " +
            "                                   FETCH FIRST ROW ONLY)) AS PREVIOUS_COURSE_OF_ACTION " +
            "                       FROM LITIGATION_CASE_INFO_AUD LT " +
            "                       GROUP BY LT.ID)SLT ON SLT.ID = LCI.ID " +
            "            WHERE LCI.ENABLED = 1", nativeQuery = true)
    List<Tuple> getActiveHeadOfficeList();

    /*@Query(value = "SELECT PLAINTIFF_NAME, TO_CHAR(DECODE(MODIFIED_DATE, NULL, CREATED_DATE, MODIFIED_DATE), 'DD/MON/YYYY') AS MODIFICATION_DATE FROM LITIGATION_CASE_INFO_AUD where id=:id", nativeQuery = true)
    List<Tuple> getPlaintiffChangeHistory(@Param("id") Long id);*/

    @Query(value = "SELECT PLAINTIFF_NAME, TO_CHAR(PLAINTIFF_CHANGES_DATE, 'DD/MON/YYYY') AS MODIFICATION_DATE FROM LITIGATION_CASE_INFO_AUD where id=:id " +
            "group by PLAINTIFF_NAME, PLAINTIFF_CHANGES_DATE order by PLAINTIFF_CHANGES_DATE desc", nativeQuery = true)
    List<Tuple> getPlaintiffChangeHistory(@Param("id") Long id);

    List<LitigationCaseInfo> findByCustomerAccNum(String accNum);

    List<LitigationCaseInfo> findByCourseOfActionId(Long courseOfActionId);

    /*@Query("Select l from LitigationCaseInfo l where l.nextDate >= :fromDate and l.nextDate <= :toDate")
    List<LitigationCaseInfo> findBetweenNextDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);*/

    @Query(value = "Select  * from litigation_case_info lci left join litigation_case_info_bla_attendance_history_list lca on lci.id = lca.litigation_case_info_id left join bla_attendance_history bla on bla.id = lca.bla_attendance_history_list_id where trunc(bla.next_date) >= :fromDate and trunc(bla.next_date) <= :toDate order by bla.created_date desc", nativeQuery = true)
    List<LitigationCaseInfo> findBetweenNextDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("Select l from LitigationCaseInfo l where  l.nextDate is null")
    List<LitigationCaseInfo> findByNextOrPreviousDateNull();

    //@Query("Select l from LitigationCaseInfo left join CaseStatus c on l. l where l.nextDate <= :nextDate and l.STATUS_ID = 1803")
    @Query(value = "select * from LITIGATION_CASE_INFO l left join CASE_STATUS c on l.STATUS_ID = c.ID where l.NEXT_DATE <= :nextDate and l.STATUS_ID != 1895", nativeQuery = true)
    List<LitigationCaseInfo> findByExpiredNextDate(@Param("nextDate") Date nextDate);

    @Query("Select COALESCE(MAX(ldNo), :ldNo) from LitigationCaseInfo")
    long findMaxLdNo(@Param("ldNo") Long ldNo);


    LitigationCaseInfo findByLdNoAndCaseTypeId(String ldNo1, Long id);
}
