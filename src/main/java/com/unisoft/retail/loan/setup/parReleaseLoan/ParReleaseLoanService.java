package com.unisoft.retail.loan.setup.parReleaseLoan;

import java.util.List;

public interface ParReleaseLoanService{

    ParReleaseLoan getPAR();

    List<ParReleaseLoan> findAll();


    ParReleaseLoan save(ParReleaseLoan parReleaseLoan);

    ParReleaseLoan findParReleaseLoanById(Long id);


}
