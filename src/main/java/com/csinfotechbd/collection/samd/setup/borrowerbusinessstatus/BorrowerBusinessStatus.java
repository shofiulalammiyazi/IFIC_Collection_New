package com.csinfotechbd.collection.samd.setup.borrowerbusinessstatus;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class BorrowerBusinessStatus extends CommonEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String name;
    private boolean status;

    public BorrowerBusinessStatus() {
    }

    public BorrowerBusinessStatus(Long id) {
        this.setId(id);
    }
}
