package com.csinfotechbd.collection.agency.agencyStatusManager;

import com.csinfotechbd.collection.agency.agencyStatus.AgencyStatusEntity;
import com.csinfotechbd.collection.settings.agency.AgencyEntity;
import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class AgencyStatusManagerEntity extends CommonEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "agency_ID")
    private AgencyEntity agencyEntity;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "agen_stat_ID")
    private AgencyStatusEntity agencyStatusEntity;


    private Long userId;

    private String remarks;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date endDate;


    @Transient
    private String totalDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AgencyEntity getAgencyEntity() {
        return agencyEntity;
    }

    public void setAgencyEntity(AgencyEntity agencyEntity) {
        this.agencyEntity = agencyEntity;
    }

    public AgencyStatusEntity getAgencyStatusEntity() {
        return agencyStatusEntity;
    }

    public void setAgencyStatusEntity(AgencyStatusEntity agencyStatusEntity) {
        this.agencyStatusEntity = agencyStatusEntity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }
}
