package com.csinfotechbd.collection.samd.setup.borrowerguarantoravailability;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class BorrowerAndGuarantorAvailability extends CommonEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String name;
    private boolean status;

    public BorrowerAndGuarantorAvailability() {
    }

    public BorrowerAndGuarantorAvailability(Long id) {
        this.setId(id);
    }
}
