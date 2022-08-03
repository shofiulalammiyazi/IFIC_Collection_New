package com.unisoft.retail.card.dataEntry.reasonDelinquencyCard;

import com.unisoft.common.CommonEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
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
