package com.unisoft.collection.settings.PARQueueAccountRuleCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PARQueueAccountRuleCardRepo extends JpaRepository<PARQueueAccountRuleCard,Long> {
    
    List<PARQueueAccountRuleCard> findByEnabledOrderByCreatedDate(boolean enabled);
}
