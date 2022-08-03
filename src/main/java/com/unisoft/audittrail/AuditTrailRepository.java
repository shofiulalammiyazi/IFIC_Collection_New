package com.unisoft.audittrail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long>, PagingAndSortingRepository<AuditTrail, Long> {
    @Override
    @Query("FROM AuditTrail ORDER BY createdDate DESC")
    List<AuditTrail> findAll();

    Page<AuditTrail> findAll(Pageable pageable);
}
