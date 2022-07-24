package com.unisoft.collection.automaticDistribution.samDistributionRule;
/*
Created by   Islam at 9/15/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SamDistributionRuleRepository extends JpaRepository<SamDistributionRuleInfo, Long> {

    SamDistributionRuleInfo findFirstByOrderByIdDesc();

    List<SamDistributionRuleInfo> findByEnabled(boolean enabled);
}
