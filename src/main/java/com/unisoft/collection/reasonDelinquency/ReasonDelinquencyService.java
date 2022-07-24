package com.unisoft.collection.reasonDelinquency;

import java.util.List;

public interface ReasonDelinquencyService {
    ReasonDelinquency save(ReasonDelinquency reasonDelinquency);

    List<ReasonDelinquency> findReasonDelinquencyById(Long id);

    List<ReasonDelinquency> findByAccountNo(String accountNo);
     List<ReasonDelinquencyDto> findCurrentMonthReasonDelinquncyBySupervisorPin(String pin);
     List<ReasonDelinquencyDto> findCurrentMonthReasonDelinquncyByManagerPin(String pin);
     List<ReasonDelinquencyWiseDto> findCurrentMonthReasonDelinquncyWiseByDealerPin(String pin, String delinquency);

     List<ReasonDelinquencyWiseDto> getDealerWiseRfdBySupervisor(List<Long> dealerPins);
     List<ReasonDelinquencyWiseDto> getDealerWiseRfdByManager(List<Long> dealerPins);
}
