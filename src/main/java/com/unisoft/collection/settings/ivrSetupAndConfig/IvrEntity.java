package com.unisoft.collection.settings.ivrSetupAndConfig;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;


@Entity
@Data
public class IvrEntity extends CommonEntity {

    private String dealerPin;
    private String ivrAccId;
    private String ivrAgentId;
    private String skillId;
}
