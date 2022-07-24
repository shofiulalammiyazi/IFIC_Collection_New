package com.unisoft.retail.card.dataEntry.followup;

import com.unisoft.base.BaseInfo;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class CardFollowUp extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Expose
    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss"
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date followUpDate;

    @Expose
    private String followUpTimes;

    @Expose
    @ElementCollection
    private Set<String> followUpReason;

    @Expose
    private String followUpRemarks;

    @Expose
    private String pin;

    @Expose
    private String username;

    @Transient
    private String temdates;

    @Transient
    private String outstanding;

    @Transient
    private String followUpDates;

    @Transient
    private String accNo;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;
}
