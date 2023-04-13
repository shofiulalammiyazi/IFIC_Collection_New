package com.unisoft.schedulermonitoringstatus;

import com.unisoft.audittrail.AuditTrail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SchedulerMonitoringStatusRepository extends JpaRepository<SchedulerMonitoringStatus, Long> {




}
