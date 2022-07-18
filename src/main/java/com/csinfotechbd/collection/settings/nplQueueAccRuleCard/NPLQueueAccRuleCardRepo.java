package com.csinfotechbd.collection.settings.nplQueueAccRuleCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NPLQueueAccRuleCardRepo extends JpaRepository<NPLQueueAccRuleCardEntity,Long> {
    
    List<NPLQueueAccRuleCardEntity> findByEnabled(boolean enabled);
    NPLQueueAccRuleCardEntity findById(long id);
}
