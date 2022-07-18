package com.csinfotechbd.collection.samd.setup.wayForwardReduceFromNPL;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class WayForwardReduceFromNPL extends CommonEntity {

    private String name;


    public WayForwardReduceFromNPL() {
    }


    public WayForwardReduceFromNPL(Long id) {
        this.setId(id);
    }
}
