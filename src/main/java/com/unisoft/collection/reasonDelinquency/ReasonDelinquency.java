package com.unisoft.collection.reasonDelinquency;

import com.unisoft.common.CommonEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReasonDelinquency extends CommonEntity {

    @Expose
    private String reaDelinName;
    @Expose
    private String dealerName;

    private String accountNo;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private CustomerBasicInfoEntity customerBasicInfoEntity;
}
