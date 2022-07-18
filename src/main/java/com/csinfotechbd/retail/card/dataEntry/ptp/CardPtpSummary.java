package com.csinfotechbd.retail.card.dataEntry.ptp;
/*
  Created by MR on 9/29/2021
*/

import lombok.Data;

@Data
public class CardPtpSummary {
    private int total;
    private int cured;
    private int broken;
    private int kept;
}
