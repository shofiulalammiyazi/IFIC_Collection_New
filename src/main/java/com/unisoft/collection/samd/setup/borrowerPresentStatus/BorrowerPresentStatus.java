package com.unisoft.collection.samd.setup.borrowerPresentStatus;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class BorrowerPresentStatus extends CommonEntity {

    private String name;

    public BorrowerPresentStatus(){

    }

    public BorrowerPresentStatus(Long id){
        this.setId(id);
    }
}
