package com.unisoft.collection.samd.setup.borrowerbusinessstatus;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

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
