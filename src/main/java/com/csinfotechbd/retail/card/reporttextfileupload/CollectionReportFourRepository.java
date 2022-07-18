package com.csinfotechbd.retail.card.reporttextfileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionReportFourRepository extends JpaRepository<CollectionReportFour, Long> {
    @Query(value = "SELECT * FROM COLLECTION_REPORT_FOUR WHERE CONTRACT_ID=:contractId   AND TRUNC(CREATED_DATE,'DD') BETWEEN  TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD') ORDER BY ID DESC FETCH FIRST ROW ONLY", nativeQuery = true)
    CollectionReportFour findByContractNoAndCreatedDate(@Param(value = "contractId") String contractId, @Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate);
}
