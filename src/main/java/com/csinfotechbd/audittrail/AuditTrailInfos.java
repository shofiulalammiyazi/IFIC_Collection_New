package com.csinfotechbd.audittrail;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
public class AuditTrailInfos extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditTrailId;
    private Long userId;
    private String userName;
    private String moduleName;
    @Lob
    private String presentData;
    @Lob
    private String previousData;
    private String systemIp;
    private String operationType;
}
