package com.unisoft.collection.samd.setup.sourceOfRecovery;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class SourceOfRecovery extends CommonEntity {

    private String name;


    public SourceOfRecovery() {
    }


    public SourceOfRecovery(Long id) {
        this.setId(id);
    }
}
