package com.csinfotechbd.collection.samd.dataEntry.visitReport.DiscussionRegardingBorrower;

public interface DiscussionRegardingBorrowerService {
    DiscussionRegardingBorrower save(DiscussionRegardingBorrower discussionRegardingBorrower);

    DiscussionRegardingBorrower getDiscussionRegardingBorrowerByCustomerId(String customerId);
}
