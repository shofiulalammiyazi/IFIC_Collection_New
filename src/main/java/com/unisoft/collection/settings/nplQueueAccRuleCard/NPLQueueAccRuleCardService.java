package com.unisoft.collection.settings.nplQueueAccRuleCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NPLQueueAccRuleCardService {
    
    @Autowired
    private NPLQueueAccRuleCardRepo nplQueueAccRuleCardRepo;
    
    public List<NPLQueueAccRuleCardEntity> getAllActive(){
      return nplQueueAccRuleCardRepo.findByEnabled(true);
    }
    
    public NPLQueueAccRuleCardEntity findById(long id){
        return nplQueueAccRuleCardRepo.findById(id);
    }
    
    public NPLQueueAccRuleCardEntity save(NPLQueueAccRuleCardEntity nplQueueAccRuleCardEntity){
        return nplQueueAccRuleCardRepo.save(nplQueueAccRuleCardEntity);
    }
}
