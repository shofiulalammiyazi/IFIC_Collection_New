package com.unisoft.collection.settings.waiverOrReversalFacility;
/*
Created by   Islam on 7/14/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "WAIVER_OR_REVERSAL_FACILITY")
public class WaiverOrReversalFacilityEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accNo;
    private double amount;
    private String type;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date effectiveDate;

    private String note;
}
