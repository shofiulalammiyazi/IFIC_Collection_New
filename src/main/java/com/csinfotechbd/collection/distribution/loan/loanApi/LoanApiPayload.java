package com.csinfotechbd.collection.distribution.loan.loanApi;
/*
Created by Monirul Islam at 7/28/2019
*/

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class LoanApiPayload {

    @SerializedName("id")
    private String id; //Dealer Id

    @SerializedName("list")
    private List<String> list;

    private String dualorsingle;

    private String sid; // Supervisor Id

    @SerializedName("remarks")
    private String remarks;
}
