package com.csinfotechbd.collection.samd.setup.borrowerstayingat;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class BorrowerStaying extends CommonEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String name;
    private boolean status;


    public BorrowerStaying() {
    }

    public BorrowerStaying(Long id) {
        this.setId(id);
    }
}
