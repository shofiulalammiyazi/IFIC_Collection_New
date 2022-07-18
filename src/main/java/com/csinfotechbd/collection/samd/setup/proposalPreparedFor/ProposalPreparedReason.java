package com.csinfotechbd.collection.samd.setup.proposalPreparedFor;

import com.csinfotechbd.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "lms_collection_sam_proposal_prepared_reason")
public class ProposalPreparedReason extends CommonEntity {

    private String name;

}
