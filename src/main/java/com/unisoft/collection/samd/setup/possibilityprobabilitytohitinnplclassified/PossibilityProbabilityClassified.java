package com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplclassified;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class PossibilityProbabilityClassified extends CommonEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String name;
    private boolean status;


    public PossibilityProbabilityClassified() {
    }


    public PossibilityProbabilityClassified(Long id) {
        this.setId(id);
    }
}
