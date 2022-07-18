package com.csinfotechbd.collection.settings.sector;
/*
Created by Monirul Islam at 7/2/2019

Update--> Removed "Description"
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SectorEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "sector_Group_id")
    private SectorGroupEntity sectorGroup;

    private String name;
    private String code;

}
