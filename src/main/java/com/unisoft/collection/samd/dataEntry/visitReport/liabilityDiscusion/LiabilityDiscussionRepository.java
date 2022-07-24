package com.unisoft.collection.samd.dataEntry.visitReport.liabilityDiscusion;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiabilityDiscussionRepository extends JpaRepository<LiabilityDiscussion, Long> {
    List<LiabilityDiscussion> findLiabilityDiscussionByCustomerIdOrderByCreatedDateDesc(String customerId);

    LiabilityDiscussion findLiabilityDiscussionById(Long id);
}
