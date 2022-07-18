package com.csinfotechbd.collection.samd.dataEntry.visitReport.DiscussionRegardingBorrower;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class DiscussionRegardingBorrower extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String detailsDiscussion;
    private String monitoringPerson;
    private String nextCourseOfAction;


    private String customerId;
}
