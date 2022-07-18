package com.csinfotechbd.retail.card.dataEntry.hotnotes;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Data
@Entity
public class CardHotNotes extends BaseInfo {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    private String vipStatus;
    @Expose
    private String hotNote;
    @Expose
    private String username;
    @Expose
    private boolean statusFlag;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerBasicInfoEntity customerBasicInfo;
}
