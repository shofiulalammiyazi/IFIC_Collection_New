package com.unisoft.collection.settings.ivrSetupAndConfig;

import lombok.Data;

@Data
public class IvrDto {

    private String account_id;
    private String agent_id;
    private String dial;
    private String time;
    private String hash;
    private String skill_id;

    public IvrDto() {
    }

    public IvrDto(String account_id, String agent_id, String dial, String time, String hash, String skill_id) {
        this.account_id = account_id;
        this.agent_id = agent_id;
        this.dial = dial;
        this.time = time;
        this.hash = hash;
        this.skill_id = skill_id;
    }
}
