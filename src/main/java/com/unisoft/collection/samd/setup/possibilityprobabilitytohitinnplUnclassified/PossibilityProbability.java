package com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplUnclassified;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

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
