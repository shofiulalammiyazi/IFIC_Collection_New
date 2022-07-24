package com.unisoft.collection.settings.statuscdmanagement;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class StatusCd extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String statuscdName;
    private  String description;
}
