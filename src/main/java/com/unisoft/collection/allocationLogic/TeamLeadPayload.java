package com.unisoft.collection.allocationLogic;

import java.util.ArrayList;
import java.util.List;

public class TeamLeadPayload {

    private List<String> teamLeadPinList = new ArrayList<>();

    public TeamLeadPayload() {
    }

    public TeamLeadPayload(List<String> teamLeadPinList) {
        this.teamLeadPinList = teamLeadPinList;
    }

    public List<String> getTeamLeadPinList() {
        return teamLeadPinList;
    }

    public void setTeamLeadPinList(List<String> teamLeadPinList) {
        this.teamLeadPinList = teamLeadPinList;
    }
}
