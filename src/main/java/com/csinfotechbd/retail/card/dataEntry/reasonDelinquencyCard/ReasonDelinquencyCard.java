package com.csinfotechbd.retail.card.dataEntry.reasonDelinquencyCard;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.common.CommonEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReasonDelinquencyCard extends CommonEntity {

    @Expose
    private String reasonDeline;
    @Expose
    private String dealerName;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private CustomerBasicInfoEntity customerBasicInfo;

}
