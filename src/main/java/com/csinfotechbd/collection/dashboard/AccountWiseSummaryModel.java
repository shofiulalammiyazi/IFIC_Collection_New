package com.csinfotechbd.collection.dashboard;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Data
@Entity
public class AccountWiseSummaryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateDate;

    private String dealerPin = "";
    private String cusId;

    private String PG; // Product Group
    private String productAndDpd;
    private Long totalNumber;
    private Long numberPerDpd;
    private float totalPerc;
    private Long touchedNumber;
    private float touchedPerc;
    private Long unTouchedNumber;
    private float unTouchedPerc;
    private Long totalPending;
    private float pendingPerc;
    private Long totalParRel;
    private float parRelPerc;
    private Long totalParRelRem;
    private float parRelRemPerc;
    private Long totalParQ;
    private float parQPerc;
    @Transient
    private int totalParQueueNextDay;

    private Long totalNplRel;
    private float nplRelPerc;
    private Long totalNplRem;
    private float nplRemPerc;
    private Long totalNplQ;
    private float nplQPerc;
    @Transient
    private int totalNplQueueNextDay;

    @Transient
    private int totalPtp;
    @Transient
    private int takenPtp;
    @Transient
    private int ntPtp;
    @Transient
    private int keptPtp;
    @Transient
    private int brokenPtp;

    public AccountWiseSummaryModel() {
    }

    public AccountWiseSummaryModel(Tuple data) {

        dealerPin = Objects.toString(data.get("dealerPin"), "");
        PG = Objects.toString(data.get("product"), "");
        productAndDpd = Objects.toString(data.get("dpdBucket"), "");
        totalNumber = ((Number) Optional.ofNullable(data.get("totalAccount")).orElse(0)).longValue();
        touchedNumber = ((Number) Optional.ofNullable(data.get("totalTouched")).orElse(0)).longValue();
        unTouchedNumber = totalNumber - touchedNumber;
        totalPending = unTouchedNumber + ((Number) Optional.ofNullable(data.get("totalBrokenPtp")).orElse(0)).longValue();
        totalParRel = ((Number) Optional.ofNullable(data.get("totalParRelease")).orElse(0)).longValue();
        totalParRelRem = ((Number) Optional.ofNullable(data.get("totalParRemaining")).orElse(0)).longValue();
        totalParQ = ((Number) Optional.ofNullable(data.get("totalParQueue")).orElse(0)).longValue();
        totalParQueueNextDay = ((Number) Optional.ofNullable(data.get("totalParQueueNextDay")).orElse(0)).intValue();
        totalNplRel = ((Number) Optional.ofNullable(data.get("totalNplRelease")).orElse(0)).longValue();
        totalNplRem = ((Number) Optional.ofNullable(data.get("totalNplRemaining")).orElse(0)).longValue();
        totalNplQ = ((Number) Optional.ofNullable(data.get("totalNplQueue")).orElse(0)).longValue();
        totalNplQueueNextDay = ((Number) Optional.ofNullable(data.get("totalNplQueueNextDay")).orElse(0)).intValue();
        totalPtp = ((Number) Optional.ofNullable(data.get("TOTAL_PTP")).orElse(0)).intValue();
        takenPtp = ((Number) Optional.ofNullable(data.get("TAKEN_PTP")).orElse(0)).intValue();
        ntPtp = ((Number) Optional.ofNullable(data.get("NT_PTP")).orElse(0)).intValue();
        keptPtp = ((Number) Optional.ofNullable(data.get("KEPT_PTP")).orElse(0)).intValue();
        brokenPtp = ((Number) Optional.ofNullable(data.get("BROKEN_PTP")).orElse(0)).intValue();
    }


}
