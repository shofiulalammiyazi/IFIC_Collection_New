package com.unisoft.collection.samd.dataEntry.monitoringFollowUp;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Entity
@Data
public class MonitoringFollowUpFile extends CommonEntity {


    private String fileName;
    private String dmsFileId;
    private String dmsFileType;


    @OneToOne(cascade = CascadeType.REFRESH)
    private MonitoringAndFollowUp monitoringFollowUp;
}
