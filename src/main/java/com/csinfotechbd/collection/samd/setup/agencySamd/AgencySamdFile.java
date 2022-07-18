package com.csinfotechbd.collection.samd.setup.agencySamd;

import com.csinfotechbd.collection.settings.agency.AgencyEntity;
import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
@Data
@Entity
public class AgencySamdFile extends CommonEntity {

    private String dmsFileId;
    private String dmsFileType;
    private String fileName;

    @OneToOne(cascade = CascadeType.REFRESH)
    private AgencySamdEntity agencySamdEntity;
}
