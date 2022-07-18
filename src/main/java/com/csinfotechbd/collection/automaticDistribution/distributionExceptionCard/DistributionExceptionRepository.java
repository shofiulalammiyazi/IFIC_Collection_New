package com.csinfotechbd.collection.automaticDistribution.distributionExceptionCard;
/*
Created by Monirul Islam at 8/22/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionExceptionRepository extends JpaRepository<DistributionException, Long> {
    DistributionException findFirstByOrderByIdDesc();
}
