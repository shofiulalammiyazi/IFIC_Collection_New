package com.csinfotechbd.legal.report.managerial.lawyerWiseCaseDetails;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LawyerWiseCaseDetailsRepository extends JpaRepository<LitigationCaseInfo,Long> {

    @Query(value = "" +
            "SELECT LCI.BRANCH_NAME                                                                    AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM)          AS ACCOUNT_NUMBER, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                     AS CASE_TYPE, " +
            "       LCI.CASE_NUMBER                                                                    AS CASE_NUMBER, " +
            "       DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)     AS COURT_NAME, " +
            "       DECODE(COALESCE(LCI.SUIT_VALUE, 0), 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       JOIN LITIGATION_CASE_INFO_LAWYER LCIL ON LCI.ID = LCIL.LITIGATION_CASE_INFO_ID " +
            "       JOIN LAWYER L ON LCIL.LAWYER_ID = L.ID " +
            "       LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "WHERE L.ID = :lawyerId " +
            "ORDER BY BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC" , nativeQuery = true)
    List<Tuple> getReport(@Param("lawyerId") long lawyerId);

    @Query(value = "" +
            "SELECT DISTINCT L.ID, L.NAME, DECODE(L.MOBILE_NO, NULL, L.PHONE_NO, L.MOBILE_NO) AS MOBILE_NO " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       JOIN LITIGATION_CASE_INFO_LAWYER LCIL ON LCI.ID = LCIL.LITIGATION_CASE_INFO_ID " +
            "       JOIN LAWYER L ON LCIL.LAWYER_ID = L.ID" , nativeQuery = true)
    List<Tuple> getAssignedLawyerList();
}
