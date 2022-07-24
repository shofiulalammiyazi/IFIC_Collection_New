package com.unisoft.collection.samd.setup.agencySamd;

import com.unisoft.common.CommonEntity;
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
