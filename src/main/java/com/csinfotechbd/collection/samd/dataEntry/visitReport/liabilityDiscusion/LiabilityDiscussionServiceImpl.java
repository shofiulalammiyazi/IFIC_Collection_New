package com.csinfotechbd.collection.samd.dataEntry.visitReport.liabilityDiscusion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiabilityDiscussionServiceImpl implements LiabilityDiscussionService{

    @Autowired
    private LiabilityDiscussionRepository repository;

    @Override
    public LiabilityDiscussion save(LiabilityDiscussion liabilityDiscussion) {
        LiabilityDiscussion liabilityDiscussion1 = repository.save(liabilityDiscussion);
        return liabilityDiscussion1;
    }

    @Override
    public List<LiabilityDiscussion> findAllLiabilityDiscussion(String customerId) {
        return repository.findLiabilityDiscussionByCustomerIdOrderByCreatedDateDesc(customerId);
    }

    @Override
    public LiabilityDiscussion findLiabilityDiscussionById(Long id) {
        LiabilityDiscussion liabilityDiscussion = repository.findLiabilityDiscussionById(id);
        return liabilityDiscussion;
    }
}
