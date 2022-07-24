package com.unisoft.collection.samd.setup.logicInTerms;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class LogicInTerms extends CommonEntity {

    private String name;

    public LogicInTerms() {
    }

    public LogicInTerms(Long id) {
        this.setId(id);
    }
}
