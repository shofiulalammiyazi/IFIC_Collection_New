package com.unisoft.retail.card.dataEntry.comments;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comment_table")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String msg;
    private String time;
    private Long userId;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerBasicInfoEntity customerBasicInfo;

    public Comment(String msg, String time, Long userId, CustomerBasicInfoEntity customerBasicInfo) {
        this.msg = msg;
        this.time = time;
        this.userId = userId;
        this.customerBasicInfo = customerBasicInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public CustomerBasicInfoEntity getCustomerBasicInfo() {
        return customerBasicInfo;
    }

    public void setCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfo) {
        this.customerBasicInfo = customerBasicInfo;
    }
}
