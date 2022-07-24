package com.unisoft.collection.samd.setup.interestedFurtherLoan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestedFurtherLoanServiceImpl implements InterestedFurtherLoanService{


    @Autowired
    private InterestedFurtherLoanRepository interestedFurtherLoanRepository;


    @Override
    public void save(InterestedFurtherLoan interestedFurtherLoan) {
        interestedFurtherLoanRepository.save(interestedFurtherLoan);
    }

    @Override
    public List<InterestedFurtherLoan> findAllActive() {
        boolean enabled = true;
        return interestedFurtherLoanRepository.findInterestedFurtherLoansByEnabled(enabled);
    }

    @Override
    public InterestedFurtherLoan findInterestedFurtherLoanById(Long id) {
        return interestedFurtherLoanRepository.findInterestedFurtherLoanById(id);
    }
}
