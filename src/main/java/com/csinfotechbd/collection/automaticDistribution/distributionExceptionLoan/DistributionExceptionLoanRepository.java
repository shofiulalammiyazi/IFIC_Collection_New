package com.csinfotechbd.collection.automaticDistribution.distributionExceptionLoan;
/*
Created by Monirul Islam at 9/4/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributionExceptionLoanRepository extends JpaRepository<DistributionExceptionLoan, Long> {
    DistributionExceptionLoan findFirstByOrderByIdDesc();
}
