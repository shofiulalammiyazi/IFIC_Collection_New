package com.csinfotechbd.retail.card.reporttextfileupload;

import lombok.Data;

@Data
public class FinalSattlementCalculatorDTO {

    Double presentOutstanding;
    Double unbilledAmount;
    Double debitOnHold;
    Double liveTransaction;
    Double unpaidEmiAmount;
    Double accuredInterestAmount;
    Double creditOnHold;
    Double livePayment;
    Double sattlementAmount;

}
