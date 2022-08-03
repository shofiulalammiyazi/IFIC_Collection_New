package com.unisoft.retail.loan.dataEntry.manualAccountWriteOff;

import java.util.List;

public interface ManualAccountWriteOffService {


    List<ManualAccountWriteOff> getCurrentMonthManualAccountWriteOffFromApi();

    List<ManualAccountWriteOff> findCurrentMonthWriteOffAccount();
}
