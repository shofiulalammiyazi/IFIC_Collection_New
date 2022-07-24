package com.unisoft.dataStore.loanListing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LoanListingRepository extends JpaRepository<LoanListing, Long> {

    @Query(value = "SELECT LL.CL_STATUS AS clStatus, COUNT(LL.CL_STATUS) AS accCount, SUM(LL.OUTSTANDING) AS outstanding, LL.BUCKET AS bucket " +
            "FROM LOAN_LISTING LL " +
            "WHERE TO_CHAR(LL.LISTING_DOWN_DATE, 'YYYY-mm') = ? " +
            "GROUP BY LL.CL_STATUS, LL.BUCKET ", nativeQuery = true)
    List<Tuple> findDataForMonthBasisClReport(String month);
}
