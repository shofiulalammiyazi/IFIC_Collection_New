package com.unisoft.collection.samd.dataEntry.visitReport.reportOne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportOneRepository extends JpaRepository<ReportOne, Long> {
    ReportOne findReportOneByCustomerId(String customerId);
}
