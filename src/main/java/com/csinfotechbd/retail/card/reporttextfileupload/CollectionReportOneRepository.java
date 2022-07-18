package com.csinfotechbd.retail.card.reporttextfileupload;

import com.csinfotechbd.retail.card.dataEntry.distribution.accountInfo.CardAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

public interface CollectionReportOneRepository extends JpaRepository<CollectionReportOne, Long> {

    @Query(value = "" +
            "SELECT * " +
            "  FROM COLLECTION_REPORT_ONE " +
            " WHERE CONTRACT_NO = ?1 AND TO_CHAR(CREATED_DATE,'DD-MON-YYYY') = ?2", nativeQuery = true)
    CollectionReportOne getByContractNoAndCreatedDate(String contactNo, String createdDate);

    @Query(value = "select ATUTP.* from APPROVE_TRANSACTIONS_UNBILLED_TRANSACTIONS_PAYMENT ATUTP " +
            "  LEFT JOIN COLLECTION_REPORT_ONE CRO ON ATUTP.CONTRACT_NO = CRO.CONTRACT_NO " +
            " WHERE TO_DATE(CRO.STATEMENT_DATE,'DD/MM/YYYY')<TO_DATE(ATUTP.POST_DATE,'DD/MM/YYYY') and  ATUTP.CONTRACT_NO = ?1", nativeQuery = true)
    List<Tuple> findAllUnbilledByContractNo(String contractNo);

    @Query(value = "select ATUTP.* from APPROVE_TRANSACTIONS_UNBILLED_TRANSACTIONS_PAYMENT ATUTP " +
            "  LEFT JOIN COLLECTION_REPORT_ONE CRO ON ATUTP.CONTRACT_NO = CRO.CONTRACT_NO " +
            " WHERE TO_DATE(CRO.STATEMENT_DATE,'DD/MM/YYYY')>TO_DATE(ATUTP.POST_DATE,'DD/MM/YYYY') and  ATUTP.CONTRACT_NO = ?1", nativeQuery = true)
    List<Tuple> findAllTrxnApprovalHistoryByContractNo(String contractNo);


    @Query(value = "" +
            " Select RV.* from RECOVERY_VIEW RV" +
            " WHERE RV.ACCOUNT = ?1 AND RV.CARD_NO = ?2", nativeQuery = true)
    Tuple findRecoveryViewDTOByContractNo(String contactNo, String test);

    @Query(value = "" +
            " Select cabi.card_no, cai.* from CARD_ACCOUNT_INFO CAI " +
            " left join card_account_basic_info cabi on cabi.id = cai.card_account_basic_info_id " +
            " WHERE CAI.CONTRACT_NO = ?1", nativeQuery = true)
    Tuple findCardAccountInfoByContractNo(String contactNo);
}
