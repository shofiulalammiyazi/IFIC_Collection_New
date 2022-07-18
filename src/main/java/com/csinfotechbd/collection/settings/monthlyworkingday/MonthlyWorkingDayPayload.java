package com.csinfotechbd.collection.settings.monthlyworkingday;

import java.util.ArrayList;
import java.util.List;

public class MonthlyWorkingDayPayload {
    private String id;
    private String name;
    private List<MonthlyWorkingDayDetailsPayload> payloads = new ArrayList<>();

    public MonthlyWorkingDayPayload() {
    }

    public MonthlyWorkingDayPayload(String id, String name, List<MonthlyWorkingDayDetailsPayload> payloads) {
        this.id = id;
        this.name = name;
        this.payloads = payloads;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MonthlyWorkingDayDetailsPayload> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<MonthlyWorkingDayDetailsPayload> payloads) {
        this.payloads = payloads;
    }
}
