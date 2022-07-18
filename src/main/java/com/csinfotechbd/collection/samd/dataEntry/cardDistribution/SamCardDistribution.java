package com.csinfotechbd.collection.samd.dataEntry.cardDistribution;

import com.csinfotechbd.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity @Getter @Setter
@Table(name = "lms_collection_sam_card_distributions")
@EntityListeners(AuditingEntityListener.class)
public class SamCardDistribution extends CommonEntity {

    private String contractId;
    private String customerId;
    private String dealerId;
    private String dealerName;

}
