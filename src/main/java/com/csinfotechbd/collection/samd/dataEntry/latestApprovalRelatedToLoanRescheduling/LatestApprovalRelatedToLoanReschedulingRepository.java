package com.csinfotechbd.collection.samd.dataEntry.latestApprovalRelatedToLoanRescheduling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LatestApprovalRelatedToLoanReschedulingRepository extends JpaRepository<LatestApprovalRelatedToLoanRescheduling, Long> {
    List<LatestApprovalRelatedToLoanRescheduling> findAllByLoanAccountNo(String accountNo);

}
