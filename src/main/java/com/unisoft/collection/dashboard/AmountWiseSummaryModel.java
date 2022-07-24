package com.unisoft.collection.dashboard;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Data
@Entity
public class AmountWiseSummaryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateDate;

    private String dealerPin = "";
    private String cusId;

    private String PG = "";
    private String productAndDpd;
    private double totalNumber;
    private double numberPerDpd;
    private double totalPerc;
    private double touchedNumber;
    private double touchedPerc;
    private double unTouchedNumber;
    private double unTouchedPerc;
    private double totalPending;
    private double pendingPerc;
    private double totalParRel;
    private double parRelPerc;
    private double totalParRelRem;
    private double parRelRemPerc;
    private double totalParQ;
    private double parQPerc;
    @Transient
    private double totalParQueueNextDay;

    private double totalNplRel;
    private double nplRelPerc;
    private double totalNplRem;
    private double nplRemPerc;
    private double totalNplQ;
    private double nplQPerc;
    private double keptFromTekenPtp;
    private double brokenPtp;
    private double ptpNotTaken;
    @Transient
    private double totalNplQueueNextDay;

    @Transient
    private int totalNoOfAcc;

    @Transient
    private String accounts;

    public AmountWiseSummaryModel() {
    }

    public AmountWiseSummaryModel(Tuple data) {
        {

            dealerPin = Objects.toString(data.get("dealerPin"), "");
            PG = Objects.toString(data.get("product"), "");
            productAndDpd = Objects.toString(data.get("dpdBucket"), "");
            totalNumber = ((Number) Optional.ofNullable(data.get("totalAmount")).orElse(0)).longValue();
            touchedNumber = ((Number) Optional.ofNullable(data.get("totalToucheded")).orElse(0)).longValue();
//            unTouchedNumber = ((Number) Optional.ofNullable(data.get("totalUntouched")).orElse(0)).longValue();
            unTouchedNumber = totalNumber - touchedNumber;
            totalPending = ((Number) Optional.ofNullable(data.get("PendingAmount")).orElse(0)).longValue();
            keptFromTekenPtp = ((Number) Optional.ofNullable(data.get("keptFromTakenPtp")).orElse(0)).longValue();
            brokenPtp = ((Number) Optional.ofNullable(data.get("ptpBroken")).orElse(0)).longValue();
            totalParRel = ((Number) Optional.ofNullable(data.get("totalParRelease")).orElse(0)).longValue();
            totalParRelRem = ((Number) Optional.ofNullable(data.get("totalParRemaining")).orElse(0)).longValue();
            totalParQ = ((Number) Optional.ofNullable(data.get("totalParQueue")).orElse(0)).longValue();
            totalParQueueNextDay = ((Number) Optional.ofNullable(data.get("totalParQueueNextDay")).orElse(0)).intValue();
            totalNplRel = ((Number) Optional.ofNullable(data.get("totalNplRelease")).orElse(0)).longValue();
            totalNplRem = ((Number) Optional.ofNullable(data.get("totalNplRemaining")).orElse(0)).longValue();
            totalNplQ = ((Number) Optional.ofNullable(data.get("totalNplQueue")).orElse(0)).longValue();
            totalNplQueueNextDay = ((Number) Optional.ofNullable(data.get("totalNplQueueNextDay")).orElse(0)).intValue();
            totalNoOfAcc = ((Number) Optional.ofNullable(data.get("TOTALACC")).orElse(0)).intValue();
            accounts = Objects.toString(data.get("accounts"),"");
        }
    }

}
