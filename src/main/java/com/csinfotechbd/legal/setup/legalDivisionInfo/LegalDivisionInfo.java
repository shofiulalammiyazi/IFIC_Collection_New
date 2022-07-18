package com.csinfotechbd.legal.setup.legalDivisionInfo;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class LegalDivisionInfo extends CommonEntity {

    private String hodName;
    private String hodPhone;
    private String hodEmail;


}
