package com.unisoft.collection.settings.PARQueueAccountRuleCard;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PARQueueAccountRuleCardService {
    
    @Autowired
    private PARQueueAccountRuleCardRepo parQueueAccountRuleCardRepo;
    
    public PARQueueAccountRuleCard save(PARQueueAccountRuleCard parQueueAccountRuleCard){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(parQueueAccountRuleCard.getId() != null){
            parQueueAccountRuleCard.setModifiedBy(String.valueOf(user.getId()));
            parQueueAccountRuleCard.setModifiedDate(new Date());
        }else{
            parQueueAccountRuleCard.setCreatedBy(String.valueOf(user.getId()));
            parQueueAccountRuleCard.setCreatedDate(new Date());
        }
        return parQueueAccountRuleCardRepo.save(parQueueAccountRuleCard);
    }
    
    public List<PARQueueAccountRuleCard> getAllActive(){
        return parQueueAccountRuleCardRepo.findByEnabledOrderByCreatedDate(true);
    }
    
}
