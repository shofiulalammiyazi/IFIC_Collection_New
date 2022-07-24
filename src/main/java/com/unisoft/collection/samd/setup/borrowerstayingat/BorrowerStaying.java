package com.unisoft.collection.samd.setup.borrowerstayingat;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

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
