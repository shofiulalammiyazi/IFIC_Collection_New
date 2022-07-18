package com.csinfotechbd.collection.settings.unit;
/*
Created by Monirul Islam at 6/19/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class UnitEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please enter code")
    @Column(unique = true)
    private String code;

    @NotBlank(message = "Please enter name")
    private String name;

    private String description;

    public UnitEntity() {
    }

    public UnitEntity(Long id) {
        this.id = id;
    }
}
