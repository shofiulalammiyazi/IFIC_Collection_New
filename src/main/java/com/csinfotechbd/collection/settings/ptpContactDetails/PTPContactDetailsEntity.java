package com.csinfotechbd.collection.settings.ptpContactDetails;
/*
Created by Monirul Islam at 7/2/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class PTPContactDetailsEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contactName;
    private String remarks;
}
