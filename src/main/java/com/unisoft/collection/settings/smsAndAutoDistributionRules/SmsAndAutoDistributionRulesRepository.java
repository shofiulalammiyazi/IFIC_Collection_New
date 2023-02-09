package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsAndAutoDistributionRulesRepository extends JpaRepository<SmsAndAutoDistributionRulesEntity, Long> {

    SmsAndAutoDistributionRulesEntity findByType(String type);
}
