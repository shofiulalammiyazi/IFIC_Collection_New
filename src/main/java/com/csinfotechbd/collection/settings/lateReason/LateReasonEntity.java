package com.csinfotechbd.collection.settings.lateReason;
/*
Created by Monirul Islam on 7/6/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class LateReasonEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reasonTitle;

    public LateReasonEntity() {
    }

    public LateReasonEntity(String reasonTitle) {
        this.reasonTitle = reasonTitle;
    }
}
