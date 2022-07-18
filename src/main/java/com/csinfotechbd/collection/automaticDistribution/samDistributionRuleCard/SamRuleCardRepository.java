package com.csinfotechbd.collection.automaticDistribution.samDistributionRuleCard;
/*
Created by Monirul Islam at 8/8/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SamRuleCardRepository extends JpaRepository<SamRuleCardInfo, Long> {
    SamRuleCardInfo findFirstByOrderByIdDesc();
}
