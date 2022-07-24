package com.unisoft.retail.card.reporttextfileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;

public interface CollectionReportTwoRepository extends JpaRepository<CollectionReportTwo, Long> {
    @Query(value = "" +
            "SELECT * " +
            "  FROM COLLECTION_REPORT_TWO " +
            " WHERE CONTRACT_NO = ?1 AND TO_CHAR(CREATED_DATE,'DD-MON-YYYY') = ?2", nativeQuery = true)
    CollectionReportTwo getByContractNoAndCreatedDate(String contactNo, String createdDate);

    @Query(value = "SELECT CBIE.NAME_ON_CARD, CRT.* " +
                   "  FROM COLLECTION_REPORT_TWO CRT " +
                   "  LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE " +
                   "    ON CRT.CONTRACT_NO = CBIE.CONTRACT_ID " +
                   " WHERE CRT.CONTRACT_NO = ?1", nativeQuery = true)
    List<Tuple> findAllUnbilledByContractNo(String contractNo);

    @Query(value = "" +
            "SELECT CBIE.CARD_NO, CAI.DUE_DATE, (CAI.BDT_LIMIT + COALESCE(CAI.INTERNATIONAL_LIMIT,0)) AS CREDIT_LIMIT " +
            "  FROM CARD_ACCOUNT_BASIC_INFO CABI " +
            "  LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CABI.CUSTOMER_ID = CBIE.ID " +
            "  LEFT JOIN CARD_ACCOUNT_INFO CAI ON CABI.ID = CAI.CARD_ACCOUNT_BASIC_INFO_ID " +
            " WHERE CABI.CONTRACT_ID = ?1 and CABI.CLIENT_ID= ?2", nativeQuery = true)
    Tuple findByContractNoAndClientId(String contactNo, String clientId);

    @Query(value = "" +
            "SELECT CBIE.* " +
            "  FROM CARD_ACCOUNT_BASIC_INFO CABI " +
            "  LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CABI.CUSTOMER_ID = CBIE.ID " +
            " WHERE CABI.CONTRACT_ID = ?1 and CABI.CLIENT_ID = ?2", nativeQuery = true)
    Tuple findCardNoByContractNoClientId(String contractNo, String clientId);

    List<CollectionReportTwo> findAllByContractNo(String contractNo);


    @Query(value = "SELECT CRT.FDR_VALUE " +
            "  FROM COLLECTION_REPORT_TWO CRT " +
            " WHERE CRT.CONTRACT_NO = ?1", nativeQuery = true)
    List<Tuple> findAllFdrByContractNo(String contractNo);

}
