package com.csinfotechbd.legal.report.datasheets.summery;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT BRN.BRANCH_NAME       as branchName, " +
            "       CT.NAME               as nosOfARS, " +
            "       LCI.SUIT_VALUE_AR     as arthaRinRelatedSuitValue, " +
            "       LCI.SUIT_VALUE_EX     as exeCaseRelatedSuitValue, " +
            "       LCI.ARTHARIN_SUIT_NO  as totalNumberArtharin, " +
            "       LCI.SUIT_VALUE        as totalSuitValue, " +
            "       LCI.NI_ACT_CASE_NO    as nosOfNIAct, " +
            "       LCI.CHEQUE_AMOUNT     as chequeAmount, " +
            "       LCI.WRIT_PETITION_NUM as numberWritPetition " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN LOS_TB_S_BRANCH BRN ON BRN.BRANCH_NAME = LCI.BRANCH_NAME " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.CUSTOMER_ID = LCI.CUSTOMER_CIF_NO " +
            "       LEFT JOIN COURTS CRT ON CRT.ID = LCI.COURT_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCILC ON LCILC.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "       LEFT JOIN LAWYER LY ON LY.ID = LCILC.LAWYER_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "       LEFT JOIN CASE_FILED_TYPE CFT ON CFT.ID = LCI.CASE_FILED_ID " +
            "       LEFT JOIN COLLATERAL_SECURITY COS ON COS.ID = LCI.COLLATERAL_SECURITY_ID " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "WHERE CS.NAME = 'Pending' ", nativeQuery = true)
    List<Tuple> getAllPendingCase();
}
