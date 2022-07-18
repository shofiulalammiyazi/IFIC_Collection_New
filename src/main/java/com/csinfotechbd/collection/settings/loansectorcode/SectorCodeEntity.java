package com.csinfotechbd.collection.settings.loansectorcode;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class SectorCodeEntity extends BaseInfo {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String accountNo;
    private String sectorCode;
    private String sectorNo;
    private String groupName;
    private String optimaId;
}
