package com.csinfotechbd.retail.card.dataEntry.contactInfoCard;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ContactInfoCard extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    private String attempt;
    @Expose
    private String contact;
    @Expose
    private String category;
    private String pin;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerBasicInfoEntity customerBasicInfo;
}
