package com.unisoft.collection.samd.setup.capableInfluenceCustomerBankDue;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class CapableInfluenceCustomerBankDue extends CommonEntity {

    private String name;


    public CapableInfluenceCustomerBankDue() {
    }


    public CapableInfluenceCustomerBankDue(Long id) {
        this.setId(id);
    }
}
