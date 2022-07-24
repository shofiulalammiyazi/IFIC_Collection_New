package com.unisoft.collection.samd.setup.interestedFurtherLoan;

import java.util.List;

public interface InterestedFurtherLoanService {


    void save(InterestedFurtherLoan interestedFurtherLoan);
    List<InterestedFurtherLoan> findAllActive();

    InterestedFurtherLoan findInterestedFurtherLoanById(Long id);
}
