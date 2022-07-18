package com.csinfotechbd.collection.agency.agencyStatus;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AgencyStatusEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;
    private String description;
    private boolean loginDisable;
}
