package com.unisoft.collection.settings.SMS;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SMSService {

    @Autowired
    private SMSRepository smsRepository;

    public List<SMSEntity> getAll()
    {
        return smsRepository.findAll();
    }

    public void saveTemplate(SMSEntity smsEntity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();// during login it is capture user info then we get access to pass value

        if (smsEntity.getId() != null){
            smsEntity.setModifiedDate(new Date()); // we set the modifieddate when user want to edit this from baseinfo to smsentity
            smsEntity.setModifiedBy(user.getUsername());  // we set the modifieddate when user want to edit this from baseinfo to smsentity
        }else {
            smsEntity.setCreatedBy(user.getUsername()); // when we create an sms then use it
            smsEntity.setCreatedDate(new Date());  //// when we create an sms then use it
        }

        smsRepository.save(smsEntity);
    }

    public List<SMSEntity> findAll() {

        return smsRepository.findAll();

    }

    public SMSEntity findSmsById(Long id) {

        return smsRepository.findSmsById(id);
    }
}
