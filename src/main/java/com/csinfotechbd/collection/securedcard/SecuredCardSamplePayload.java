package com.csinfotechbd.collection.securedcard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SecuredCardSamplePayload {
    @SerializedName("list")
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
