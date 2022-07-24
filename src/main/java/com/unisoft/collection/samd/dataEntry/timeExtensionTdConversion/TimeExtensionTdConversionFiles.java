package com.unisoft.collection.samd.dataEntry.timeExtensionTdConversion;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TimeExtensionTdConversionFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;
    private String dmsFileId;
    private String dmsFileType;

    @OneToOne(cascade = CascadeType.REFRESH)
    private TimeExtensionTdConversion timeExtensionTdConversion;

    @Transient
    private Long timeExtensionTdConversionId;

    public TimeExtensionTdConversion getTimeExtensionTdConversion(){
        return timeExtensionTdConversion =(timeExtensionTdConversion!=null) ? timeExtensionTdConversion: new TimeExtensionTdConversion();
    }

    public void settimeExtensionTdConversion(TimeExtensionTdConversion timeExtensionTdConversion) {
        this.timeExtensionTdConversion = new TimeExtensionTdConversion(timeExtensionTdConversionId);
    }


}
