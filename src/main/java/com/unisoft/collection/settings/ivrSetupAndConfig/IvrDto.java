package com.unisoft.collection.settings.ivrSetupAndConfig;

import lombok.Data;

@Data
public class IvrDto {

    private String ivrAccId;
    private String ivrAgentId;
    private String dial;
    private String time;
    private String hash;
    private String skillId;

    public IvrDto(String ivrAccId, String ivrAgentId, String dial, String time, String hash, String skillId) {
        this.ivrAccId = ivrAccId;
        this.ivrAgentId = ivrAgentId;
        this.dial = dial;
        this.time = time;
        this.hash = hash;
        this.skillId = skillId;
    }

    public IvrDto() {
    }
}
