package com.csinfotechbd.collection.samd.setup.sourceofrecoverytools;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
