package com.csinfotechbd.retail.loan.dataEntry.manualAccountWriteOff;

import com.csinfotechbd.loanApi.model.LoanAccInfo;

import java.util.List;

public interface ManualAccountWriteOffService {


    List<ManualAccountWriteOff> getCurrentMonthManualAccountWriteOffFromApi();

    List<ManualAccountWriteOff> findCurrentMonthWriteOffAccount();
}
