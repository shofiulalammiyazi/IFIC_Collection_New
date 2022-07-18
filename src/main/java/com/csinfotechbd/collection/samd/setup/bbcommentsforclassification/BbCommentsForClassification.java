package com.csinfotechbd.collection.samd.setup.bbcommentsforclassification;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
