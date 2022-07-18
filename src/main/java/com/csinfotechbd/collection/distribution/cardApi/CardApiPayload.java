package com.csinfotechbd.collection.distribution.cardApi;
/*
Created by Monirul Islam at 7/29/2019
*/

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class CardApiPayload {

    @SerializedName("id")
    private String id;

    @SerializedName("list")
    private List<String> list;

    private String dualorsingle;
}
