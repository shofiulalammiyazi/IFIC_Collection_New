package com.unisoft.collection.samd.setup.proposedRescheduleTimes;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class ProposedRescheduleTimes extends CommonEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String rescheduleTimes;
}
