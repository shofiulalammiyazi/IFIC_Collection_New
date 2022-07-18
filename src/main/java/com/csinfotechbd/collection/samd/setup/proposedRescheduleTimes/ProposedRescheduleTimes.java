package com.csinfotechbd.collection.samd.setup.proposedRescheduleTimes;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ProposedRescheduleTimes extends CommonEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String rescheduleTimes;
}
