package com.unisoft.collection.samd.setup.loanLiabilityRecoverability;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class LoanLiabilityRecoverability extends CommonEntity {

    private String name;


    public LoanLiabilityRecoverability() {
    }


    public LoanLiabilityRecoverability(Long id) {
        this.setId(id);
    }
}
