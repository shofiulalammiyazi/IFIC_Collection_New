package com.unisoft.collection.chequeManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface ChequeManagementRepository extends JpaRepository<ChequeManagement, Long> {

    @Query(value = "SELECT LLCM.ID           AS id, " +
            "       LLCM.CHEQUE_NO    AS chequeNo, " +
            "       LLCM.ACCOUNT_NO   AS accountNo, " +
            "       LLCM.ACCOUNT_NAME AS accountName, " +
            "       LLCM.BANK_NAME    AS bankName, " +
            "       LLCM.BRANCH_NAME  AS branchName, " +
            "       LLCM.AMOUNT       AS amount, " +
            "       LLCM.RECEIVE_DATE AS receiveDate, " +
            "       LLCM.ROUTING_NO AS routingNo, " +
            "       LLCM.CHEQUE_DATE AS chequeDate, " +
            "       LLCM.RECEIVED_BY AS receivedBy, " +
            "       LLCM.PRESENT_STATUS AS presentStatus, " +
            "       CMF.FILE_NAME     AS fileName, " +
            "       CMF.DMS_FILE_ID   AS dmsId, " +
            "       CMF.DMS_FILE_TYPE AS fileType " +
            "FROM LMS_LOAN_CHEQUE_MANAGEMENT LLCM " +
            "       LEFT JOIN CHEQUE_MANAGEMENT_FILE CMF ON CMF.CHEQUE_MANAGEMENT_ID = LLCM.ID " +
            "WHERE LLCM.CUSTOMER_ID = ? " +
            "ORDER BY LLCM.CREATED_DATE DESC", nativeQuery = true)
    List<Tuple> findAllByCustomerId(String customerId);
}
