package com.csinfotechbd.collection.samd.setup.loanLiabilityRecoverability;


import com.csinfotechbd.common.CommonEntity;
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
