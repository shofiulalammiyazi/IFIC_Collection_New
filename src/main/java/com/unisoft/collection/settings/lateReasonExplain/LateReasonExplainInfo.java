package com.unisoft.collection.settings.lateReasonExplain;
/*
Created by   Islam at 7/22/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.lateReason.LateReasonEntity;
import com.unisoft.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class LateReasonExplainInfo extends BaseInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "late_reason_id")
    private LateReasonEntity lateReasonEntity;
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;
    private String logoutReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LateReasonEntity getLateReasonEntity() {
        return lateReasonEntity;
    }

    public void setLateReasonEntity(LateReasonEntity lateReasonEntity) {
        this.lateReasonEntity = lateReasonEntity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogoutReason() {
        return logoutReason;
    }

    public void setLogoutReason(String logoutReason) {
        this.logoutReason = logoutReason;
    }
}
