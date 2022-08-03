package com.unisoft.collection.automaticDistribution.distributionExceptionCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionExceptionRepository extends JpaRepository<DistributionException, Long> {
    DistributionException findFirstByOrderByIdDesc();
}
