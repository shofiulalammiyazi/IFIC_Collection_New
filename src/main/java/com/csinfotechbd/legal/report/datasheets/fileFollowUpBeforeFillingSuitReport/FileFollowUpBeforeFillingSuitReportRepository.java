package com.csinfotechbd.legal.report.datasheets.fileFollowUpBeforeFillingSuitReport;

import com.csinfotechbd.legal.dataEntry.fileFollowUp.LitigationFileFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileFollowUpBeforeFillingSuitReportRepository extends JpaRepository<LitigationFileFollowUp, Long> {


//    Page<FileFollowUpBeforeFillingSuitReportDto> getReportDtos(Pageable pageable);


}
