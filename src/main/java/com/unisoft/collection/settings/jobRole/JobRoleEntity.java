package com.unisoft.collection.settings.jobRole;
/*
Created by   Islam at 6/25/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class JobRoleEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;
    private String description;

    public JobRoleEntity (){}
    public JobRoleEntity (Long id){this.id = id;}

}
