package com.unisoft.collection.samd.setup.whethertheloanistetd;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class WhetherTheLoanTeTdRcRs extends CommonEntity {

    private String name;
    private boolean status;

    public WhetherTheLoanTeTdRcRs() {
    }

    public WhetherTheLoanTeTdRcRs(Long id) {
        this.setId(id);
    }
}
