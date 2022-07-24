package com.unisoft.collection.samd.dataEntry.visitReport.DiscussionRegardingBorrower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRegardingBorrowerRepository extends JpaRepository<DiscussionRegardingBorrower, Long> {
    DiscussionRegardingBorrower findDiscussionRegardingBorrowerByCustomerId(String customerId);
}
