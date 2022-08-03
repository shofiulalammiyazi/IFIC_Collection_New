package com.unisoft.collection.automaticDistribution.distributionExceptionLoan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributionExceptionLoanRepository extends JpaRepository<DistributionExceptionLoan, Long> {
    DistributionExceptionLoan findFirstByOrderByIdDesc();
}
