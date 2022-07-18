package com.csinfotechbd.collection.samd.setup.approvalLevel;

import com.csinfotechbd.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "lms_collection_sam_approval_level")
public class ApprovalLevel extends CommonEntity {

    private String name;

}
