package com.csinfotechbd.collection.samd.setup.whethertheloanistetd;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
