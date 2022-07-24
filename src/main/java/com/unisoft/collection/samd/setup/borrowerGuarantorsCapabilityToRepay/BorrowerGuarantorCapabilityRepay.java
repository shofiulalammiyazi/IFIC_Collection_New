package com.unisoft.collection.samd.setup.borrowerGuarantorsCapabilityToRepay;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

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
