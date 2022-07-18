package com.csinfotechbd.collection.settings.department;
/*
Created by Monirul Islam at 6/19/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DepartmentEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String deptCode;
    private String name;
    private String description;


}
