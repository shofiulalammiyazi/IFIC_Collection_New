package com.csinfotechbd.collection.settings.ageAndClassificationRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeAndClassificationRuleEntityRepository extends JpaRepository<AgeAndClassifiactionRuleEntity, Long> {
}
