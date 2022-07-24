package com.unisoft.collection.samd.dataEntry.visitReport.DiscussionRegardingBorrower;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscussionRegardingBorrowerServiceImpl implements DiscussionRegardingBorrowerService{


    @Autowired
    private DiscussionRegardingBorrowerRepository regardingBorrowerRepository;

    @Override
    public DiscussionRegardingBorrower save(DiscussionRegardingBorrower discussionRegardingBorrower) {
        DiscussionRegardingBorrower discussionRegardingBorrower1 = regardingBorrowerRepository.save(discussionRegardingBorrower);
        return discussionRegardingBorrower1;
    }

    @Override
    public DiscussionRegardingBorrower getDiscussionRegardingBorrowerByCustomerId(String customerId) {
        DiscussionRegardingBorrower discussionRegardingBorrower = regardingBorrowerRepository.findDiscussionRegardingBorrowerByCustomerId(customerId);
        return discussionRegardingBorrower;
    }
}
