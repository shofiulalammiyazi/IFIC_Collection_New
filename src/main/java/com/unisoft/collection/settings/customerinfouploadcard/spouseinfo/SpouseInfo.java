package com.unisoft.collection.settings.customerinfouploadcard.spouseinfo;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class SpouseInfo extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contractId;
    private String spouseMobileNo;
    private String spouseOccupation;
}
