package com.unisoft.collection.samd.setup.bbApprovalRequirement;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class BBApprovalRequirement extends CommonEntity {

//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String approvalRequirement;
}
