package com.unisoft.collection.samd.setup.interestedFurtherLoan;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class InterestedFurtherLoan extends CommonEntity {

    private String name;


    public InterestedFurtherLoan() {
    }


    public InterestedFurtherLoan(Long id) {
        this.setId(id);
    }
}
