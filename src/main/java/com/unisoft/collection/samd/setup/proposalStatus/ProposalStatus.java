package com.unisoft.collection.samd.setup.proposalStatus;


import com.unisoft.common.CommonEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;


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
