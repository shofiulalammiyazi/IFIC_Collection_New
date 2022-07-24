package com.unisoft.collection.dashboard;


import lombok.Data;

@Data
public class DailyActivityModel {

    private String dealerName;
    private String dealerPin;
    private long dailyNotes;
    private long dairyNote;
    private long ptp;
    private long hotNote;
    private long visitLedger;
}
