package com.unisoft.collection.settings.agency;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
public class AgencyFile extends CommonEntity {

    private String dmsFileId;
    private String dmsFileType;
    private String fileName;

    @OneToOne(cascade = CascadeType.REFRESH)
    private AgencyEntity agencyEntity;
}
