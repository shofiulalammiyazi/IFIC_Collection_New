package com.unisoft.collection.settings.nplQueueAccRuleCard;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "NPL_QUEUE_ACC_RULE_CARD")
public class NPLQueueAccRuleCardEntity extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double minAgeCode = 0D; // To calculate NPL queue next day
    
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ProductTypeCardEntity> productTypeList;
    
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<AgeCodeEntity> ageCodeList;
}
