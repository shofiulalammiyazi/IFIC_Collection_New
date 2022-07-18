package com.csinfotechbd.collection.samd.setup.borrowerGuarantorsCapabilityToRepay;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class BorrowerGuarantorCapabilityRepay extends CommonEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String name;
    private boolean status;


    public BorrowerGuarantorCapabilityRepay() {
    }

    public BorrowerGuarantorCapabilityRepay(Long id) {
        this.setId(id);
    }
}
