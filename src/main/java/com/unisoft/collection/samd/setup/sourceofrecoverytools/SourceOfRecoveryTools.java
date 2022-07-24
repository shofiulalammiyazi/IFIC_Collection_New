package com.unisoft.collection.samd.setup.sourceofrecoverytools;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class SourceOfRecoveryTools extends CommonEntity {

    private String name;
    private boolean status;

    public SourceOfRecoveryTools() {
    }

    public SourceOfRecoveryTools(Long id) {
        this.setId(id);
    }
}
