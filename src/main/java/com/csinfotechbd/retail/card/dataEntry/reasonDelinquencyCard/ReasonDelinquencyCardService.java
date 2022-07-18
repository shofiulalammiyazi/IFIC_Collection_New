package com.csinfotechbd.retail.card.dataEntry.reasonDelinquencyCard;

import java.util.List;

public interface ReasonDelinquencyCardService {

    ReasonDelinquencyCard save(ReasonDelinquencyCard reasonDelinquencyCard);

    List<ReasonDelinquencyCard> findReasonDelinquencyCardById(Long id);

}
