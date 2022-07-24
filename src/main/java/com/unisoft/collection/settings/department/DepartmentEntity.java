package com.unisoft.collection.settings.department;
/*
Created by   Islam at 6/19/2019
*/

import com.unisoft.base.BaseInfo;
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
