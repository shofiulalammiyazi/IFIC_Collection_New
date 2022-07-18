package com.csinfotechbd.collection.settings.designation;
/*
Created by Monirul Islam at 6/19/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class DesignationEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String desCode;
    private String name;
    private String description;
}
