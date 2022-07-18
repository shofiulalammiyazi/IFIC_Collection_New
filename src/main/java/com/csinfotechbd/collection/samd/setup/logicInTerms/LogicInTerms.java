package com.csinfotechbd.collection.samd.setup.logicInTerms;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

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
