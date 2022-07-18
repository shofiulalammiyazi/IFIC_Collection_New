package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 1/5/2020
*/

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
