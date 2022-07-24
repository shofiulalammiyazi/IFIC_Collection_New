package com.unisoft.audittrail;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AuditTrail extends CommonEntity {

    private Long auditTrailId;

    private String username;

    private String name;

    private String requestIP;

    private String moduleName;

    private String className;

    private String operationType;

    @Lob
    private String presentData;

    @Lob
    private String previousData;
}
