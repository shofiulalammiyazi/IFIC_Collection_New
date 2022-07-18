package com.csinfotechbd.legal.log.mailHistoryLog;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
public class LegalMailHistory extends CommonEntity {

    private String mailType;
    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    @Lob
    private String mailMessage;

}
