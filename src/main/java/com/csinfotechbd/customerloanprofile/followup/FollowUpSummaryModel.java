package com.csinfotechbd.customerloanprofile.followup;

import com.csinfotechbd.utillity.StringUtils;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.Tuple;
import java.sql.Clob;
import java.util.Objects;

@Data
public class FollowUpSummaryModel {

    private String reason;
    private long totalAccount;
    private double outstandingAmount;
    private String followUpData;
    private FollowUpDataModel[] followUpDetails;

    public FollowUpSummaryModel() {
    }

    public FollowUpSummaryModel(Tuple tuple) {
        reason = Objects.toString(tuple.get("REASON"), "");
        totalAccount = ((Number) tuple.get("TOTAL_ACCOUNT")).longValue();
        outstandingAmount = ((Number) tuple.get("OUTSTANDING_AMOUNT")).doubleValue();

        try {
            Clob followUpDetails = (Clob) tuple.get("FOLLOW_UP_DATA");
            String dataListString = StringUtils.clobToString(followUpDetails);
            setFollowUpDetailModelsFromJson(dataListString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FollowUpSummaryModel(String reason, long totalAccount, double outstandingAmount) {
        this.reason = reason;
        this.totalAccount = totalAccount;
        this.outstandingAmount = outstandingAmount;
    }

    public FollowUpSummaryModel(@NonNull String reason, @NonNull String followUpData, @NonNull Long totalAccount, @NonNull Double outstandingAmount) {
        this.reason = reason;
        this.totalAccount = totalAccount;
        this.outstandingAmount = outstandingAmount;
        setFollowUpDetailModelsFromJson(followUpData);
    }

    public void setFollowUpData(String followUpData) {
        setFollowUpDetailModelsFromJson(followUpData);
    }

    public void setFollowUpDetailModelsFromJson(String followUpData) {
        //followUpData = followUpData.replaceAll("", "");
        followUpDetails = new Gson().fromJson(followUpData, FollowUpDataModel[].class);
    }

}
