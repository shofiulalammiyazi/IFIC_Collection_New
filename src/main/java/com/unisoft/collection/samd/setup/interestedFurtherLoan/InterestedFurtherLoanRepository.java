package com.unisoft.collection.samd.setup.interestedFurtherLoan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestedFurtherLoanRepository extends JpaRepository<InterestedFurtherLoan, Long> {
    List<InterestedFurtherLoan> findInterestedFurtherLoansByEnabled(boolean enabled);

    InterestedFurtherLoan findInterestedFurtherLoanById(Long id);

//    List<InterestedFurtherLoan> findInterestedFurtherLoansByEnabled(boolean);
}
