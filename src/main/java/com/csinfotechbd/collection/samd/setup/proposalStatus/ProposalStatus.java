package com.csinfotechbd.collection.samd.setup.proposalStatus;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
public class ProposalStatus extends CommonEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String name;
    @Value(value = "Active")
    private String status;
}
