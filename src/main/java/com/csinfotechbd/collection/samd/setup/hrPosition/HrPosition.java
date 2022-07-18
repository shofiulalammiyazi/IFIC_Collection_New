package com.csinfotechbd.collection.samd.setup.hrPosition;

import com.csinfotechbd.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Getter @Setter
@Table(name = "hr_position")
public class HrPosition extends CommonEntity {

    private String name;
    private String shortName;
}
