package com.csinfotechbd.legal.log.mailHistoryLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalMailHistoryRepository extends JpaRepository<LegalMailHistory, Long> {

}
