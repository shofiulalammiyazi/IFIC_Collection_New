package com.unisoft.collection.settings.considerAttempt;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsiderAttemptService {

    @Autowired
    private ConsiderAttemptRepository considerAttemptRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<ConsiderAttempt> getConsiderAttemptList(){
      return considerAttemptRepository.findAll();

    }

    public List<ConsiderAttempt> getConsiderAttemptListLoan(){
     return considerAttemptRepository.findByTypeAndEnabled("LOAN",true);
    }
    public List<ConsiderAttempt> getConsiderAttemptListCard(){
     return considerAttemptRepository.findByTypeAndEnabled("CARD",true);
    }

    public String save(ConsiderAttempt considerAttempt) {
        boolean isNewEntity = false;
        ConsiderAttempt previousEntity = new ConsiderAttempt();

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (considerAttempt.getId() == null) {
            considerAttempt.setCreatedBy(user.getUsername());
            considerAttempt.setCreatedDate(new Date());
        } else {
            ConsiderAttempt oldData = considerAttemptRepository.getOne(considerAttempt.getId());
            BeanUtils.copyProperties(oldData, previousEntity);

            considerAttempt.setModifiedBy(user.getUsername());
            considerAttempt.setModifiedDate(new Date());
        }
        considerAttemptRepository.save(considerAttempt);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Consideration As Attempt", considerAttempt);
        else
            auditTrailService.saveUpdatedData("Consideration As Attempt", previousEntity, considerAttempt);
        return "1";
    }

    public ConsiderAttempt getById(Long id)
    {
        return considerAttemptRepository.findById(id).orElse(new ConsiderAttempt());
    }

    public List<ConsiderAttempt> getActiveList()
    {
        return  considerAttemptRepository.findByEnabled(true);
    }


}
