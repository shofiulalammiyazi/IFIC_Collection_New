package com.csinfotechbd.legal.dataEntry.blaAttendanceHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BLAAttendanceHistoryRepository extends JpaRepository<BLAAttendanceHistory, Long> {

    List<BLAAttendanceHistory> findAllByLitigationCaseInfoIdOrderByCreatedDateDesc(Long id);
    BLAAttendanceHistory findFirstByLitigationCaseInfoIdOrderByCreatedDateDesc(Long id);
}
