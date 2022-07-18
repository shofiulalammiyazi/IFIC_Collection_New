package com.csinfotechbd.retail.card.dataEntry.comments;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CommentApiPayLoad {
    @SerializedName("customerId")
    private String customerId;

    @SerializedName("comment")
    private String comment;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
