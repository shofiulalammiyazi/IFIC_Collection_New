package com.unisoft.collection.settings.SMS.template;

import com.unisoft.collection.settings.SMS.smsType.SMSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateGenerateRepository extends JpaRepository<TemplateGenerate, Long> {

    @Query(value = "SELECT TG.* FROM TEMPLATE_GENERATE TG WHERE TG.SMS_TYPE_ID = ?1", nativeQuery = true)
    TemplateGenerate findTemGenBySmsTypeId(Long id);

    @Query(value = "SELECT TG.* FROM TEMPLATE_GENERATE TG WHERE TG.ID = ?1", nativeQuery = true)
    TemplateGenerate findTempById(Long id);

    TemplateGenerate findBySmsType(SMSEntity smsEntity);
}

