package com.csinfotechbd.collection.settings.nplAccountRuleCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NPLAccountRuleCardRepo extends JpaRepository<NPLAccountRuleCardEntity,Long> {
    
    List<NPLAccountRuleCardEntity> findByEnabled(boolean enabled);
    NPLAccountRuleCardEntity findById(long id);
}
