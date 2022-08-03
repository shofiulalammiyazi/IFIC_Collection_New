package com.unisoft.collection.automaticDistribution.distributionExceptionLoanModel;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionExceptionModelRepository extends JpaRepository<ProductGroupDpdBucket, Long> {
}
