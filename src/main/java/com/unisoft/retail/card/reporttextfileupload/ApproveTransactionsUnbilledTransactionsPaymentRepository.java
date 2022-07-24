package com.unisoft.retail.card.reporttextfileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ApproveTransactionsUnbilledTransactionsPaymentRepository extends JpaRepository<ApproveTransactionsUnbilledTransactionsPayment, Long> {

    @Query(value = "" +
            "SELECT * " +
            "  FROM APPROVE_TRANSACTIONS_UNBILLED_TRANSACTIONS_PAYMENT " +
            " WHERE CONTRACT_NO = ?1 AND TO_CHAR(CREATED_DATE,'DD-MON-YYYY') = ?2", nativeQuery = true)
    ApproveTransactionsUnbilledTransactionsPayment findTrxnApprovalHistoryByContractNo(String contractNo, String createdDate);

@Query(value = "SELECT * FROM APPROVE_TRANSACTIONS_UNBILLED_TRANSACTIONS_PAYMENT\n" +
        "WHERE TO_DATE(TXN_DATE,'DD-MON-YYYY HH:MI:SS AM')  >= add_months(sysdate, -3) AND CONTRACT_NO=?1",nativeQuery = true)
List<ApproveTransactionsUnbilledTransactionsPayment> getLastThreeMonthData(String contractNo);



@Query(value = "SELECT * FROM APPROVE_TRANSACTIONS_UNBILLED_TRANSACTIONS_PAYMENT  WHERE CONTRACT_NO=:contractNo AND TO_DATE(TXN_DATE,'DD-MON-YYYY HH:MI:SS AM') BETWEEN TO_DATE(:startDate,'DD-MM-YYYY') AND TO_DATE(:endDate,'DD-MM-YYYY')", nativeQuery = true)
List<ApproveTransactionsUnbilledTransactionsPayment> getSearchApproveTransactionPaymentData(@Param("contractNo")String contractNo, @Param("startDate")String startDate, @Param("endDate") String endDate);


    @Query(value = "SELECT * FROM APPROVE_TRANSACTIONS_UNBILLED_TRANSACTIONS_PAYMENT WHERE CONTRACT_NO = ? ", nativeQuery = true)
    List<ApproveTransactionsUnbilledTransactionsPayment> getTransactionListByContractId(String contractNo);
}
