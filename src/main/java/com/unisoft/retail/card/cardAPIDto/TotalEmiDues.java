package com.unisoft.retail.card.cardAPIDto;

import lombok.Data;

@Data
public class TotalEmiDues {
    String contractNo;
    String totalEMIAmount;
    String totalPaidEMIAmount;
    String totalUnpaidEMIAmount;
}
