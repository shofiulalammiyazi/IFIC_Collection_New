package com.csinfotechbd.collection.samd.setup.bBAuditType;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class BBAuditType extends CommonEntity {

    private String name;


    public BBAuditType() {
    }

    public BBAuditType(Long id) {
        this.setId(id);
    }
}
