package com.unisoft.collection.settings.PTPContactLocation;
/*
Created by   Islam at 6/30/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class PTPContactLocationEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contactLocation;


}
