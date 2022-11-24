package com.unisoft.collection.settings.SMS.template;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TemplateGenerateService {

    @Autowired
    private TemplateGenerateRepository templateGenerateRepository;

    public List<TemplateGenerate> findAll() {
        return templateGenerateRepository.findAll();
    }

    public void saveGenerate(TemplateGenerate templateGenerate) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();// during login it is capture user info then we get access to pass value

        if (templateGenerate.getId() != null){
            templateGenerate.setModifiedDate(new Date()); // we set the modifieddate when user want to edit this from baseinfo to smsentity
            templateGenerate.setModifiedBy(user.getUsername());  // we set the modifieddate when user want to edit this from baseinfo to smsentity
        }else {
            templateGenerate.setCreatedBy(user.getUsername()); // when we create an sms then use it
            templateGenerate.setCreatedDate(new Date());   //// when we create an sms then use it
        }

        templateGenerateRepository.save(templateGenerate);
    }



    public TemplateGenerate findTemGenById(Long id) {

       return templateGenerateRepository.findTemGenBySmsTypeId(id);

    }
}
