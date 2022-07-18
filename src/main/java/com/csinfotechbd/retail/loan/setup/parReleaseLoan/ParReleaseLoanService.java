package com.csinfotechbd.retail.loan.setup.parReleaseLoan;

import com.csinfotechbd.common.CommonService;

import java.util.List;

public interface ParReleaseLoanService{

    ParReleaseLoan getPAR();

    List<ParReleaseLoan> findAll();


    ParReleaseLoan save(ParReleaseLoan parReleaseLoan);

    ParReleaseLoan findParReleaseLoanById(Long id);


}
