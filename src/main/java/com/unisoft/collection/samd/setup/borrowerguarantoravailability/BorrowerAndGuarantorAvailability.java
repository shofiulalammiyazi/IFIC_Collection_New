package com.unisoft.collection.samd.setup.borrowerguarantoravailability;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

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
