package com.unisoft.retail.card.reporttextfileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionReportThreeRepository extends JpaRepository<CollectionReportThree, Long> {

    @Query(value = "" +
            "SELECT * " +
            "  FROM COLLECTION_REPORT_THREE " +
            " WHERE CONTRACT_NO = ?1 AND TO_CHAR(CREATED_DATE,'DD-MON-YYYY') = ?2", nativeQuery = true)
    List<CollectionReportThree> getByContractNoAndCreatedDate(String contactNo, String createdDate);

    List<CollectionReportThree> findByContractNo(String contractNo);

}
