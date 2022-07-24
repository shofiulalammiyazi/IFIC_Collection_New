package com.unisoft.collection.samd.dataEntry.visitReport.liabilityDiscusion;

import java.util.List;

public interface LiabilityDiscussionService {
    LiabilityDiscussion save(LiabilityDiscussion liabilityDiscussion);

    List<LiabilityDiscussion> findAllLiabilityDiscussion(String customerId);

    LiabilityDiscussion findLiabilityDiscussionById(Long id);
}
