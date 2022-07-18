package com.csinfotechbd.collection.settings.lateReason;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LateReasonRepository extends JpaRepository<LateReasonEntity,Long> {
    LateReasonEntity findByReasonTitle(String regular);
}
