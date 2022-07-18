package com.csinfotechbd.retail.card.cardAPIDto;

import lombok.Data;

@Data
public class TotalEmiDues {
    String contractNo;
    String totalEMIAmount;
    String totalPaidEMIAmount;
    String totalUnpaidEMIAmount;
}
