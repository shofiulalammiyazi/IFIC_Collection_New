package com.unisoft.collection.samd.setup.bbcommentsforclassification;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class BbCommentsForClassification extends CommonEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String name;
    private boolean status;

    public BbCommentsForClassification() {
    }


    public BbCommentsForClassification(Long id) {
        this.setId(id);
    }
}
