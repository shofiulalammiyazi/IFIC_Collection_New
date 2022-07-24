package com.unisoft.collection.samd.dataEntry.visitReport.statusReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusReportRepository extends JpaRepository<StatusReport, Long> {
    List<StatusReport> findStatusReportByCustomerId(String customerId);

    StatusReport findStatusReportById(Long id);
}
