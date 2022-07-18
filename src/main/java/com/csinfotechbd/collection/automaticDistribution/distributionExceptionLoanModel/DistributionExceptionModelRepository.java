package com.csinfotechbd.collection.automaticDistribution.distributionExceptionLoanModel;
/*
Created by Monirul Islam at 9/5/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionExceptionModelRepository extends JpaRepository<ProductGroupDpdBucket, Long> {
}
