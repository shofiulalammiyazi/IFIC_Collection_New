package com.csinfotechbd.legal.setup.alternativeWayReport;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class AlternativeWayReportEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numberOne;
    private String claimAmountOne;
    private String numberTwo;
    private String claimAmountTwo;
    private String actualRealization;

}
