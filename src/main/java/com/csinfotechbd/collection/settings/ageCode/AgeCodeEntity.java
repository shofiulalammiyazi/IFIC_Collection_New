package com.csinfotechbd.collection.settings.ageCode;
/*
Created by Monirul Islam on 7/9/2019
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "AGE_CODE")
public class AgeCodeEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public AgeCodeEntity() {
    }

    public AgeCodeEntity(Long id) {
        this.id = id;
    }
}
