package com.csinfotechbd.collection.samd.setup.possibilityprobabilitytohitinnplUnclassified;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class PossibilityProbability extends CommonEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String name;
    private boolean status;


    public PossibilityProbability() {
    }

    public PossibilityProbability(Long id) {
        this.setId(id);
    }
}
