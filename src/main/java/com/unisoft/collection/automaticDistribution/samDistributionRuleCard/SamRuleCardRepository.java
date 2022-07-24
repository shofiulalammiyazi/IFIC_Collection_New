package com.unisoft.collection.automaticDistribution.samDistributionRuleCard;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SamRuleCardRepository extends JpaRepository<SamRuleCardInfo, Long> {
    SamRuleCardInfo findFirstByOrderByIdDesc();
}
