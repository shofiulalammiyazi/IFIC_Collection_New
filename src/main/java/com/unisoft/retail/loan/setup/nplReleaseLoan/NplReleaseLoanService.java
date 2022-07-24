package com.unisoft.retail.loan.setup.nplReleaseLoan;


import java.util.List;

public interface NplReleaseLoanService {

    NplReleaseLoan getNPL();

    List<NplReleaseLoan> findAll();


    NplReleaseLoan save(NplReleaseLoan nplReleaseLoan);

    NplReleaseLoan findNplReleaseLoanById(Long id);


}
