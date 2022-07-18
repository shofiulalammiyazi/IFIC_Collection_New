package com.csinfotechbd.collection.datamigration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeRepaymentFidRepository extends JpaRepository<ModeRepaymentFid, Long> {
    ModeRepaymentFid findByAccountNo(String accountNo);
}
