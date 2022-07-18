package com.csinfotechbd.collection.samd.dataEntry.visitReport.statusReport;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class StatusReport extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date wOffDate;
    private Double wOffAmount;
    private String recoveryWOff;
    private String presentOsdt;
    private Double ptpAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date ptpDate;
    private String ptpSourceOfPayment;
    private String suiteType;
    private Double suiteAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date previousDate;
    private String previousStatus;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date nextDate;
    private String nextStatus;

    private String customerId;
}
